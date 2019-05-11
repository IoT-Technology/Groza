package com.sanshengshui.server.transport.coap;

import com.sanshengshui.server.common.data.id.SessionId;
import com.sanshengshui.server.common.data.security.DeviceCredentialsFilter;
import com.sanshengshui.server.common.data.security.DeviceTokenCredentials;
import com.sanshengshui.server.common.msg.session.*;
import com.sanshengshui.server.common.transport.SessionMsgProcessor;
import com.sanshengshui.server.common.transport.adaptor.AdaptorException;
import com.sanshengshui.server.common.transport.auth.DeviceAuthService;
import com.sanshengshui.server.common.transport.quota.QuotaService;
import com.sanshengshui.server.transport.coap.adaptors.CoapTransportAdaptor;
import com.sanshengshui.server.transport.coap.session.CoapExchangeObserverProxy;
import com.sanshengshui.server.transport.coap.session.CoapSessionCtx;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.Request;
import org.eclipse.californium.core.network.Exchange;
import org.eclipse.californium.core.network.ExchangeObserver;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.eclipse.californium.core.server.resources.Resource;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

@Slf4j
public class CoapTransportResource extends CoapResource {

    // coap://localhost:port/api/v1/DEVICE_TOKEN/[attributes|telemetry|rpc[/requestId]]
    private static final int ACCESS_TOKEN_POSITION = 3;
    private static final int FEATURE_TYPE_POSITION = 4;
    private static final int REQUEST_ID_POSITION = 5;


    private final CoapTransportAdaptor adaptor;
    private final SessionMsgProcessor processor;
    private final DeviceAuthService authService;
    private final QuotaService quotaService;
    private final Field observerField;
    private final long timeout;

    public CoapTransportResource(SessionMsgProcessor processor, DeviceAuthService authService, CoapTransportAdaptor adaptor, String name,
                                 long timeout, QuotaService quotaService) {
        super(name);
        this.processor = processor;
        this.authService = authService;
        this.quotaService = quotaService;
        this.adaptor = adaptor;
        this.timeout = timeout;
        this.setObservable(false);
        observerField = ReflectionUtils.findField(Exchange.class, "observer");
        observerField.setAccessible(true);
    }

    @Override
    public void handleGET(CoapExchange exchange) {
        if (quotaService.isQuotaExceeded(exchange.getSourceAddress().getHostAddress())){
            log.warn("COAP Quota exceeded for [{}:{}] . Disconnect", exchange.getSourceAddress().getHostAddress(), exchange.getSourcePort());
            exchange.respond(CoAP.ResponseCode.BAD_REQUEST);
            return;
        }
        Optional<FeatureType> featureType = getFeatureType(exchange.advanced().getRequest());
        if (!featureType.isPresent()) {
            log.trace("Missing feature type parameter");
            exchange.respond(CoAP.ResponseCode.BAD_REQUEST);
        } else if (featureType.get() == FeatureType.TELEMETRY) {
            log.trace("Can't fetch/subscribe to timeseries updates");
            exchange.respond(CoAP.ResponseCode.BAD_REQUEST);
        } else if (exchange.getRequestOptions().hasObserve()) {
            processExchangeGetRequest(exchange, featureType.get());
        } else if (featureType.get() == FeatureType.ATTRIBUTES) {
            processRequest(exchange, SessionMsgType.GET_ATTRIBUTES_REQUEST);
        } else {
            log.trace("Invalid feature type parameter");
            exchange.respond(CoAP.ResponseCode.BAD_REQUEST);
        }
    }

    private void processExchangeGetRequest(CoapExchange exchange, FeatureType featureType) {
        boolean unsubscribe = exchange.getRequestOptions().getObserve() == 1;
        SessionMsgType sessionMsgType;
        if (featureType == FeatureType.RPC) {
            sessionMsgType = unsubscribe ? SessionMsgType.UNSUBSCRIBE_RPC_COMMANDS_REQUEST : SessionMsgType.SUBSCRIBE_RPC_COMMANDS_REQUEST;
        } else {
            sessionMsgType = unsubscribe ? SessionMsgType.UNSUBSCRIBE_ATTRIBUTES_REQUEST : SessionMsgType.SUBSCRIBE_ATTRIBUTES_REQUEST;
        }
        Optional<SessionId> sessionId = processRequest(exchange, sessionMsgType);
        if (sessionId.isPresent()) {
            if (exchange.getRequestOptions().getObserve() == 1) {
                exchange.respond(CoAP.ResponseCode.VALID);
            }
        }
    }

    @Override
    public void handlePOST(CoapExchange exchange) {
        Optional<FeatureType> featureType = getFeatureType(exchange.advanced().getRequest());
        if (!featureType.isPresent()) {
            log.trace("Missing feature type parameter");
            exchange.respond(CoAP.ResponseCode.BAD_REQUEST);
        } else {
            switch (featureType.get()) {
                case ATTRIBUTES:
                    processRequest(exchange, SessionMsgType.POST_ATTRIBUTES_REQUEST);
                    break;
                case TELEMETRY:
                    processRequest(exchange, SessionMsgType.POST_TELEMETRY_REQUEST);
                    break;
                case RPC:
                    Optional<Integer> requestId = getRequestId(exchange.advanced().getRequest());
                    if (requestId.isPresent()) {
                        processRequest(exchange, SessionMsgType.TO_DEVICE_RPC_RESPONSE);
                    } else {
                        processRequest(exchange, SessionMsgType.TO_SERVER_RPC_REQUEST);
                    }
                    break;
            }
        }
    }

    private Optional<SessionId> processRequest(CoapExchange exchange, SessionMsgType type) {
        log.trace("Processing {}", exchange.advanced().getRequest());
        exchange.accept();
        Exchange advanced = exchange.advanced();
        Request request = advanced.getRequest();

        Optional<DeviceCredentialsFilter> credentials = decodeCredentials(request);
        if (!credentials.isPresent()) {
            exchange.respond(CoAP.ResponseCode.BAD_REQUEST);
            return Optional.empty();
        }


        CoapSessionCtx ctx = new CoapSessionCtx(exchange, adaptor, processor, authService, timeout);

        AdaptorToSessionActorMsg msg;
        try {
            switch (type) {
                case GET_ATTRIBUTES_REQUEST:
                case POST_ATTRIBUTES_REQUEST:
                case POST_TELEMETRY_REQUEST:
                case TO_DEVICE_RPC_RESPONSE:
                case TO_SERVER_RPC_REQUEST:
                    ctx.setSessionType(SessionType.SYNC);
                    msg = adaptor.convertToActorMsg(ctx, type, request);
                    break;
                case SUBSCRIBE_ATTRIBUTES_REQUEST:
                case SUBSCRIBE_RPC_COMMANDS_REQUEST:
                    ExchangeObserver systemObserver = (ExchangeObserver) observerField.get(advanced);
                    advanced.setObserver(new CoapExchangeObserverProxy(systemObserver, ctx));
                    ctx.setSessionType(SessionType.ASYNC);
                    msg = adaptor.convertToActorMsg(ctx, type, request);
                    break;
                case UNSUBSCRIBE_ATTRIBUTES_REQUEST:
                case UNSUBSCRIBE_RPC_COMMANDS_REQUEST:
                    ctx.setSessionType(SessionType.ASYNC);
                    msg = adaptor.convertToActorMsg(ctx, type, request);
                    break;
                default:
                    log.trace("[{}] Unsupported msg type: {}", ctx.getSessionId(), type);
                    throw new IllegalArgumentException("Unsupported msg type: " + type);
            }
            log.trace("Processing msg: {}", msg);
            processor.process(new BasicTransportToDeviceSessionActorMsg(ctx.getDevice(), msg));
        } catch (AdaptorException e) {
            log.debug("Failed to decode payload {}", e);
            exchange.respond(CoAP.ResponseCode.BAD_REQUEST, e.getMessage());
            return Optional.empty();
        } catch (IllegalArgumentException  | IllegalAccessException e) {
            log.debug("Failed to process payload {}", e);
            exchange.respond(CoAP.ResponseCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        return Optional.of(ctx.getSessionId());
    }

    private Optional<DeviceCredentialsFilter> decodeCredentials(Request request) {
        List<String> uriPath = request.getOptions().getUriPath();
        DeviceCredentialsFilter credentials = null;
        if (uriPath.size() >= ACCESS_TOKEN_POSITION) {
            credentials = new DeviceTokenCredentials(uriPath.get(ACCESS_TOKEN_POSITION - 1));
        }
        return Optional.ofNullable(credentials);
    }

    private Optional<FeatureType> getFeatureType(Request request) {
        List<String> uriPath = request.getOptions().getUriPath();
        try {
            if (uriPath.size() >= FEATURE_TYPE_POSITION) {
                return Optional.of(FeatureType.valueOf(uriPath.get(FEATURE_TYPE_POSITION - 1).toUpperCase()));
            }
        } catch (RuntimeException e) {
            log.warn("Failed to decode feature type: {}", uriPath);
        }
        return Optional.empty();
    }

    public static Optional<Integer> getRequestId(Request request) {
        List<String> uriPath = request.getOptions().getUriPath();
        try {
            if (uriPath.size() >= REQUEST_ID_POSITION) {
                return Optional.of(Integer.valueOf(uriPath.get(REQUEST_ID_POSITION - 1)));
            }
        } catch (RuntimeException e) {
            log.warn("Failed to decode feature type: {}", uriPath);
        }
        return Optional.empty();
    }

    @Override
    public Resource getChild(String name) {
        return this;
    }
}
