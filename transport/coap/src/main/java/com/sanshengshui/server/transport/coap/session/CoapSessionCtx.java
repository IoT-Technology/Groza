package com.sanshengshui.server.transport.coap.session;

import com.sanshengshui.server.common.data.id.SessionId;
import com.sanshengshui.server.common.msg.session.SessionActorToAdaptorMsg;
import com.sanshengshui.server.common.msg.session.SessionCtrlMsg;
import com.sanshengshui.server.common.msg.session.SessionType;
import com.sanshengshui.server.common.msg.session.ctrl.SessionCloseMsg;
import com.sanshengshui.server.common.msg.session.ex.SessionException;
import com.sanshengshui.server.common.transport.SessionMsgProcessor;
import com.sanshengshui.server.common.transport.adaptor.AdaptorException;
import com.sanshengshui.server.common.transport.auth.DeviceAuthService;
import com.sanshengshui.server.common.transport.session.DeviceAwareSessionContext;
import com.sanshengshui.server.transport.coap.adaptors.CoapTransportAdaptor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.Request;
import org.eclipse.californium.core.coap.Response;
import org.eclipse.californium.core.server.resources.CoapExchange;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author james mu
 * @date 19-1-21 下午3:57
 */
@Slf4j
public class CoapSessionCtx  extends DeviceAwareSessionContext {

    private final SessionId sessionId;
    private final CoapExchange exchange;
    private final CoapTransportAdaptor adaptor;
    private final String token;
    private final long timeout;
    private SessionType sessionType;
    private final AtomicInteger seqNumber = new AtomicInteger(2);

    public CoapSessionCtx(CoapExchange exchange, CoapTransportAdaptor adaptor, SessionMsgProcessor processor, DeviceAuthService authService, long timeout) {
        super(processor, authService);
        Request request = exchange.advanced().getRequest();
        this.token = request.getTokenString();
        this.sessionId = new CoapSessionId(request.getSource().getHostAddress(), request.getSourcePort(), this.token);
        this.exchange = exchange;
        this.adaptor = adaptor;
        this.timeout = timeout;
    }


    @Override
    public void onMsg(SessionActorToAdaptorMsg msg) throws SessionException {
        try {
            adaptor.convertToAdaptorMsg(this, msg).ifPresent(this::pushToNetwork);
        } catch (AdaptorException e) {
            logAndWrap(e);
        }
    }

    private void pushToNetwork(Response response) {
        exchange.respond(response);
    }

    private void logAndWrap(AdaptorException e) throws SessionException {
        log.warn("Failed to convert msg: {}", e.getMessage(), e);
        throw new SessionException(e);
    }

    @Override
    public void onMsg(SessionCtrlMsg msg) throws SessionException {
        log.debug("[{}] onCtrl: {}", sessionId, msg);
        if (msg instanceof SessionCloseMsg) {
            onSessionClose((SessionCloseMsg) msg);
        }
    }

    private void onSessionClose(SessionCloseMsg msg) {
        if (msg.isTimeout()) {
            exchange.respond(CoAP.ResponseCode.SERVICE_UNAVAILABLE);
        } else if (msg.isCredentialsRevoked()) {
            exchange.respond(CoAP.ResponseCode.UNAUTHORIZED);
        } else {
            exchange.respond(CoAP.ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public SessionId getSessionId() {
        return sessionId;
    }

    @Override
    public String toString() {
        return "CoapSessionCtx [sessionId=" + sessionId + "]";
    }

    @Override
    public boolean isClosed() {
        return exchange.advanced().isComplete() || exchange.advanced().isTimedOut();
    }

    public void close() {
        log.info("[{}] Closing processing context. Timeout: {}", sessionId, exchange.advanced().isTimedOut());
        processor.process(exchange.advanced().isTimedOut() ? SessionCloseMsg.onTimeout(sessionId) : SessionCloseMsg.onError(sessionId));
    }

    @Override
    public long getTimeout() {
        return timeout;
    }

    public void setSessionType(SessionType sessionType) {
        this.sessionType = sessionType;
    }

    @Override
    public SessionType getSessionType() {
        return sessionType;
    }

    public int nextSeqNumber() {
        return seqNumber.getAndIncrement();
    }
}
