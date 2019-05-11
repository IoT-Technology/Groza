package com.sanshengshui.server.transport.mqtt;

import com.fasterxml.jackson.databind.JsonNode;
import com.sanshengshui.server.common.data.Device;
import com.sanshengshui.server.common.data.security.DeviceTokenCredentials;
import com.sanshengshui.server.common.data.security.DeviceX509Credentials;
import com.sanshengshui.server.common.msg.core.SessionOpenMsg;
import com.sanshengshui.server.common.msg.session.AdaptorToSessionActorMsg;
import com.sanshengshui.server.common.msg.session.BasicAdaptorToSessionActorMsg;
import com.sanshengshui.server.common.msg.session.BasicTransportToDeviceSessionActorMsg;
import com.sanshengshui.server.common.msg.session.ctrl.SessionCloseMsg;
import com.sanshengshui.server.common.transport.SessionMsgProcessor;
import com.sanshengshui.server.common.transport.adaptor.AdaptorException;
import com.sanshengshui.server.common.transport.auth.DeviceAuthService;
import com.sanshengshui.server.common.transport.quota.QuotaService;
import com.sanshengshui.server.dao.EncryptionUtil;
import com.sanshengshui.server.dao.device.DeviceService;
import com.sanshengshui.server.dao.relation.RelationService;
import com.sanshengshui.server.transport.mqtt.adaptors.MqttTransportAdaptor;
import com.sanshengshui.server.transport.mqtt.session.DeviceSessionCtx;
import com.sanshengshui.server.transport.mqtt.session.GatewaySessionCtx;
import com.sanshengshui.server.transport.mqtt.util.SslUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.mqtt.*;
import io.netty.handler.ssl.SslHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.util.StringUtils;

import javax.net.ssl.SSLPeerUnverifiedException;
import javax.security.cert.X509Certificate;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static com.sanshengshui.rule.engine.producers.producers.PulsarClient;
import static com.sanshengshui.server.common.msg.session.SessionMsgType.*;
import static com.sanshengshui.server.transport.mqtt.MqttTopics.*;
import static io.netty.handler.codec.mqtt.MqttConnectReturnCode.*;
import static io.netty.handler.codec.mqtt.MqttConnectReturnCode.CONNECTION_REFUSED_NOT_AUTHORIZED;
import static io.netty.handler.codec.mqtt.MqttMessageType.*;
import static io.netty.handler.codec.mqtt.MqttQoS.*;

/**
 * @author james
 * @date 2018年10月23日
 * handle mqtt message
 */
@Slf4j
public class MqttTransportHandler extends ChannelInboundHandlerAdapter implements GenericFutureListener<Future<? super Void>> {

    public static final MqttQoS MAX_SUPPORTED_QOS_LVL = AT_LEAST_ONCE;

    private final DeviceSessionCtx deviceSessionCtx;
    private final String sessionId;
    private final MqttTransportAdaptor adaptor;
    private final SessionMsgProcessor processor;
    private final DeviceService deviceService;
    private final DeviceAuthService authService;
    private final RelationService relationService;
    private final QuotaService quotaService;
    private final SslHandler sslHandler;
    private final ConcurrentMap<MqttTopicMatcher, Integer> mqttQoSMap;

    private volatile boolean connected;
    private volatile InetSocketAddress address;
    private volatile GatewaySessionCtx gatewaySessionCtx;

    public MqttTransportHandler(SessionMsgProcessor processor, DeviceService deviceService, DeviceAuthService authService, RelationService relationService,
                                MqttTransportAdaptor adaptor, SslHandler sslHandler, QuotaService quotaService) {
        this.processor = processor;
        this.deviceService = deviceService;
        this.relationService = relationService;
        this.authService = authService;
        this.adaptor = adaptor;
        this.mqttQoSMap = new ConcurrentHashMap<>();
        this.deviceSessionCtx = new DeviceSessionCtx(processor, authService, adaptor, mqttQoSMap);
        this.sessionId = deviceSessionCtx.getSessionId().toUidStr();
        this.sslHandler = sslHandler;
        this.quotaService = quotaService;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        log.trace("[{}] Processing msg: {}", sessionId, msg);
        if (msg instanceof MqttMessage) {
            processMqttMsg(ctx, (MqttMessage) msg);
        } else {
            ctx.close();
        }
    }

    private void processMqttMsg(ChannelHandlerContext ctx, MqttMessage msg) {
        address = (InetSocketAddress) ctx.channel().remoteAddress();
        if (msg.fixedHeader() == null) {
            log.info("[{}:{}] Invalid message received", address.getHostName(), address.getPort());
            processDisconnect(ctx);
            return;
        }

        if (quotaService.isQuotaExceeded(address.getHostName())) {
            log.warn("MQTT Quota exceeded for [{}:{}] . Disconnect", address.getHostName(), address.getPort());
            processDisconnect(ctx);
            return;
        }

        deviceSessionCtx.setChannel(ctx);
        /**
         * 根据不同MQTT固定头部信息类型，进行不同动作
         */
        switch (msg.fixedHeader().messageType()) {
            //连接: C->S
            case CONNECT:
                //客户端请求与服务器的连接的报文
                processConnect(ctx, (MqttConnectMessage) msg);
                break;
            //推送消息: C<->S

            case PUBLISH:
                //PUBLISH控制包从客户端发送到服务器或从服务器发送到客户端以传输消息
                processPublish(ctx, (MqttPublishMessage) msg);
                break;
            //订阅请求: C->S
            /**
             * SUBSCRIBE数据包从客户端发送到服务器以创建一个或多个订阅。每个订阅
             * 都注册客户对一个或多个主题的兴趣。服务器将PUBLISH数据包发送到客户端，
             * 以便将发布的应用程序消息转发到与这些订阅匹配的主题。SUBSCRIBE数据包还
             * 指定(对于每个订阅)服务器可以将应用程序消息发送到客户端的最大QoS。
             */
            case SUBSCRIBE:
                processSubscribe(ctx, (MqttSubscribeMessage) msg);
                break;
            //取消订阅请求: C->S
            /**
             * 取消订阅--取消订阅主题
             * 客户端向服务器发送UNSUBSCRIBE数据包，以取消订阅
             */
            case UNSUBSCRIBE:
                processUnsubscribe(ctx, (MqttUnsubscribeMessage) msg);
                break;
            //PING请求: C->S
            /**
             * PINGREQ -- PING请求
             * PINGREQ数据包从客户端发送到服务器。它可以用于:
             * 1. 在没有任何其他控制数据包从客户端发送到服务器的情况下，向服务器指示客户端处于活动状态。
             * 2. 请求服务器响应以确认它处于活动状态。
             * 3. 联系网络以指示网络连接处于活动状态。
             *
             * -- 此数据包用于Keep Alive处理
             */
            case PINGREQ:
                //判断是否连接
                if (checkConnected(ctx)) {
                    //服务器发送PINGRESP数据包以响应PINGREQ数据包。
                    ctx.writeAndFlush(new MqttMessage(new MqttFixedHeader(PINGRESP, false, AT_MOST_ONCE, false, 0)));
                }
                break;
            //断开连接: C->S
            /**
             * DISCONNECT数据包是从客户端发送到服务器的最终控制数据包。它表示客户端正在完全断开连接。
             */
            case DISCONNECT:
                //判断是否连接，如果网络连接，则断开连接
                if (checkConnected(ctx)) {
                    processDisconnect(ctx);
                }
                break;
            default:
                break;
        }

    }
    //处理PUBLISH消息
    private void processPublish(ChannelHandlerContext ctx, MqttPublishMessage mqttMsg) {
        //检查是否连接
        if (!checkConnected(ctx)) {
            return;
        }
        //获取PUBLISH消息有效负载中的主题名称，包标志符
        String topicName = mqttMsg.variableHeader().topicName();
        int msgId = mqttMsg.variableHeader().messageId();
        //打印相关信息
        log.trace("[{}] Processing publish msg [{}][{}]!", sessionId, topicName, msgId);
        //如果主题是以网关主题为开头，则处理推送消息给网关
        if (topicName.startsWith(BASE_GATEWAY_API_TOPIC)) {
            if (gatewaySessionCtx != null) {
                gatewaySessionCtx.setChannel(ctx);
                handleMqttPublishMsg(topicName, msgId, mqttMsg);
            }
        } else {
            //推送消息给设备
            processDevicePublish(ctx, mqttMsg, topicName, msgId);
        }
    }

    private void handleMqttPublishMsg(String topicName, int msgId, MqttPublishMessage mqttMsg) {
        try {
            if (topicName.equals(GATEWAY_TELEMETRY_TOPIC)) {
                gatewaySessionCtx.onDeviceTelemetry(mqttMsg);
            } else if (topicName.equals(GATEWAY_ATTRIBUTES_TOPIC)) {
                gatewaySessionCtx.onDeviceAttributes(mqttMsg);
            } else if (topicName.equals(GATEWAY_ATTRIBUTES_REQUEST_TOPIC)) {
                gatewaySessionCtx.onDeviceAttributesRequest(mqttMsg);
            } else if (topicName.equals(GATEWAY_RPC_TOPIC)) {
                gatewaySessionCtx.onDeviceRpcResponse(mqttMsg);
            } else if (topicName.equals(GATEWAY_CONNECT_TOPIC)) {
                gatewaySessionCtx.onDeviceConnect(mqttMsg);
            } else if (topicName.equals(GATEWAY_DISCONNECT_TOPIC)) {
                gatewaySessionCtx.onDeviceDisconnect(mqttMsg);
            }
        } catch (RuntimeException | AdaptorException e) {
            log.warn("[{}] Failed to process publish msg [{}][{}]", sessionId, topicName, msgId, e);
        }
    }

    private void processDevicePublish(ChannelHandlerContext ctx, MqttPublishMessage mqttMsg, String topicName, int msgId) {
        AdaptorToSessionActorMsg msg = null;
        try {
            PulsarClient();
        } catch (PulsarClientException e) {
            e.printStackTrace();
        }
        try {
            if (topicName.equals(DEVICE_TELEMETRY_TOPIC)) {
                msg = adaptor.convertToActorMsg(deviceSessionCtx, POST_TELEMETRY_REQUEST, mqttMsg);
            } else if (topicName.equals(DEVICE_ATTRIBUTES_TOPIC)) {
                msg = adaptor.convertToActorMsg(deviceSessionCtx, POST_ATTRIBUTES_REQUEST, mqttMsg);
            } else if (topicName.startsWith(DEVICE_ATTRIBUTES_REQUEST_TOPIC_PREFIX)) {
                msg = adaptor.convertToActorMsg(deviceSessionCtx, GET_ATTRIBUTES_REQUEST, mqttMsg);
                if (msgId >= 0) {
                    ctx.writeAndFlush(createMqttPubAckMsg(msgId));
                }
            } else if (topicName.startsWith(DEVICE_RPC_RESPONSE_TOPIC)) {
                msg = adaptor.convertToActorMsg(deviceSessionCtx, TO_DEVICE_RPC_RESPONSE, mqttMsg);
                if (msgId >= 0) {
                    ctx.writeAndFlush(createMqttPubAckMsg(msgId));
                }
            } else if (topicName.startsWith(DEVICE_RPC_REQUESTS_TOPIC)) {
                msg = adaptor.convertToActorMsg(deviceSessionCtx, TO_SERVER_RPC_REQUEST, mqttMsg);
                if (msgId >= 0) {
                    ctx.writeAndFlush(createMqttPubAckMsg(msgId));
                }
            }
        } catch (AdaptorException e) {
            log.warn("[{}] Failed to process publish msg [{}][{}]", sessionId, topicName, msgId, e);
        }
        if (msg != null) {
            processor.process(new BasicTransportToDeviceSessionActorMsg(deviceSessionCtx.getDevice(), msg));
        } else {
            log.info("[{}] Closing current session due to invalid publish msg [{}][{}]", sessionId, topicName, msgId);
            ctx.close();
        }
    }

    //处理来自客户端的订阅请求
    private void processSubscribe(ChannelHandlerContext ctx, MqttSubscribeMessage mqttMsg) {
        //判断是否连接
        if (!checkConnected(ctx)) {
            return;
        }
        //打印变量头中的数据包标识符
        log.trace("[{}] Processing subscription [{}]!", sessionId, mqttMsg.variableHeader().messageId());
        List<Integer> grantedQoSList = new ArrayList<>();
        for (MqttTopicSubscription subscription : mqttMsg.payload().topicSubscriptions()) {
            String topicName = subscription.topicName();
            //TODO: handle this qos level.
            MqttQoS reqQoS = subscription.qualityOfService();
            try {
                if (topicName.equals(DEVICE_ATTRIBUTES_TOPIC)) {
                    AdaptorToSessionActorMsg msg = adaptor.convertToActorMsg(deviceSessionCtx, SUBSCRIBE_ATTRIBUTES_REQUEST, mqttMsg);
//                    processor.process(new BasicTransportToDeviceSessionActorMsg(deviceSessionCtx.getDevice(), msg));
                    grantedQoSList.add(getMinSupportedQos(reqQoS));
                } else if (topicName.equals(DEVICE_RPC_REQUESTS_SUB_TOPIC)) {
                    AdaptorToSessionActorMsg msg = adaptor.convertToActorMsg(deviceSessionCtx, SUBSCRIBE_RPC_COMMANDS_REQUEST, mqttMsg);
//                    processor.process(new BasicTransportToDeviceSessionActorMsg(deviceSessionCtx.getDevice(), msg));
                    grantedQoSList.add(getMinSupportedQos(reqQoS));
                } else if (topicName.equals(DEVICE_RPC_RESPONSE_SUB_TOPIC)) {
                    grantedQoSList.add(getMinSupportedQos(reqQoS));
                } else if (topicName.equals(DEVICE_ATTRIBUTES_RESPONSES_TOPIC)) {
                    deviceSessionCtx.setAllowAttributeResponses();
                    grantedQoSList.add(getMinSupportedQos(reqQoS));
                } else if (topicName.equals(GATEWAY_ATTRIBUTES_TOPIC)) {
                    grantedQoSList.add(getMinSupportedQos(reqQoS));
                } else {
                    log.warn("[{}] Failed to subscribe to [{}][{}]", sessionId, topicName, reqQoS);
                    grantedQoSList.add(FAILURE.value());
                }
            } catch (AdaptorException e) {
                log.warn("[{}] Failed to subscribe to [{}][{}]", sessionId, topicName, reqQoS);
                grantedQoSList.add(FAILURE.value());
            }
        }
        ctx.writeAndFlush(createSubAckMessage(mqttMsg.variableHeader().messageId(), grantedQoSList));
    }

    //处理来自客户端的取消订阅请求
    private void processUnsubscribe(ChannelHandlerContext ctx, MqttUnsubscribeMessage mqttMsg) {
        //判断是否连接
        if (!checkConnected(ctx)) {
            return;
        }
        //打印消息变量头中的数据包标识符
        log.trace("[{}] Processing subscription [{}]!", sessionId, mqttMsg.variableHeader().messageId());
        for (String topicName : mqttMsg.payload().topics()) {
            try {
                if (topicName.equals(DEVICE_ATTRIBUTES_TOPIC)) {
                    AdaptorToSessionActorMsg msg = adaptor.convertToActorMsg(deviceSessionCtx, UNSUBSCRIBE_ATTRIBUTES_REQUEST, mqttMsg);
//                    processor.process(new BasicTransportToDeviceSessionActorMsg(deviceSessionCtx.getDevice(), msg));
                } else if (topicName.equals(DEVICE_RPC_REQUESTS_SUB_TOPIC)) {
                    AdaptorToSessionActorMsg msg = adaptor.convertToActorMsg(deviceSessionCtx, UNSUBSCRIBE_RPC_COMMANDS_REQUEST, mqttMsg);
//                    processor.process(new BasicTransportToDeviceSessionActorMsg(deviceSessionCtx.getDevice(), msg));
                } else if (topicName.equals(DEVICE_ATTRIBUTES_RESPONSES_TOPIC)) {
                    deviceSessionCtx.setDisallowAttributeResponses();
                }
            } catch (AdaptorException e) {
                log.warn("[{}] Failed to process unsubscription [{}] to [{}]", sessionId, mqttMsg.variableHeader().messageId(), topicName);
            }
        }
        ctx.writeAndFlush(createUnSubAckMessage(mqttMsg.variableHeader().messageId()));
    }

    private MqttMessage createUnSubAckMessage(int msgId) {
        MqttFixedHeader mqttFixedHeader =
                new MqttFixedHeader(UNSUBACK, false, AT_LEAST_ONCE, false, 0);
        MqttMessageIdVariableHeader mqttMessageIdVariableHeader = MqttMessageIdVariableHeader.from(msgId);
        return new MqttMessage(mqttFixedHeader, mqttMessageIdVariableHeader);
    }

    //处理连接操作
    private void processConnect(ChannelHandlerContext ctx, MqttConnectMessage msg) {
        //打印来自客户端的连接信息，包括会话ID,有效负载的客户端标识符
        log.info("[{}] Processing connect msg for client: {}!", sessionId, msg.payload().clientIdentifier());
        X509Certificate cert;
        if (sslHandler != null && (cert = getX509Certificate()) != null) {
            processX509CertConnect(ctx, cert);
        } else {
            processAuthTokenConnect(ctx, msg);
        }
    }

    private void processAuthTokenConnect(ChannelHandlerContext ctx, MqttConnectMessage msg) {
        String userName = msg.payload().userName();
        if (StringUtils.isEmpty(userName)) {
            ctx.writeAndFlush(createMqttConnAckMsg(CONNECTION_REFUSED_BAD_USER_NAME_OR_PASSWORD));
            ctx.close();
        } else if (!deviceSessionCtx.login(new DeviceTokenCredentials(msg.payload().userName()))) {
            ctx.writeAndFlush(createMqttConnAckMsg(CONNECTION_REFUSED_NOT_AUTHORIZED));
            ctx.close();
        } else {
            ctx.writeAndFlush(createMqttConnAckMsg(CONNECTION_ACCEPTED));
            connected = true;
//            processor.process(new BasicTransportToDeviceSessionActorMsg(deviceSessionCtx.getDevice(),
//                    new BasicAdaptorToSessionActorMsg(deviceSessionCtx, new SessionOpenMsg())));
//            checkGatewaySession();
        }
    }

    private void processX509CertConnect(ChannelHandlerContext ctx, X509Certificate cert) {
        try {
            String strCert = SslUtil.getX509CertificateString(cert);
            String sha3Hash = EncryptionUtil.getSha3Hash(strCert);
            if (deviceSessionCtx.login(new DeviceX509Credentials(sha3Hash))) {
                ctx.writeAndFlush(createMqttConnAckMsg(CONNECTION_ACCEPTED));
                connected = true;
                processor.process(new BasicTransportToDeviceSessionActorMsg(deviceSessionCtx.getDevice(),
                        new BasicAdaptorToSessionActorMsg(deviceSessionCtx, new SessionOpenMsg())));
                checkGatewaySession();
            } else {
                ctx.writeAndFlush(createMqttConnAckMsg(CONNECTION_REFUSED_NOT_AUTHORIZED));
                ctx.close();
            }
        } catch (Exception e) {
            ctx.writeAndFlush(createMqttConnAckMsg(CONNECTION_REFUSED_NOT_AUTHORIZED));
            ctx.close();
        }
    }

    private X509Certificate getX509Certificate() {
        try {
            X509Certificate[] certChain = sslHandler.engine().getSession().getPeerCertificateChain();
            if (certChain.length > 0) {
                return certChain[0];
            }
        } catch (SSLPeerUnverifiedException e) {
            log.warn(e.getMessage());
            return null;
        }
        return null;
    }

    private void processDisconnect(ChannelHandlerContext ctx) {
        ctx.close();
        if (connected) {
//            processor.process(SessionCloseMsg.onDisconnect(deviceSessionCtx.getSessionId()));
            if (gatewaySessionCtx != null) {
                gatewaySessionCtx.onGatewayDisconnect();
            }
        }
    }

    private MqttConnAckMessage createMqttConnAckMsg(MqttConnectReturnCode returnCode) {
        MqttFixedHeader mqttFixedHeader =
                new MqttFixedHeader(CONNACK, false, AT_MOST_ONCE, false, 0);
        MqttConnAckVariableHeader mqttConnAckVariableHeader =
                new MqttConnAckVariableHeader(returnCode, true);
        return new MqttConnAckMessage(mqttFixedHeader, mqttConnAckVariableHeader);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("[{}] Unexpected Exception", sessionId, cause);
        ctx.close();
    }

    private static MqttSubAckMessage createSubAckMessage(Integer msgId, List<Integer> grantedQoSList) {
        MqttFixedHeader mqttFixedHeader =
                new MqttFixedHeader(SUBACK, false, AT_LEAST_ONCE, false, 0);
        MqttMessageIdVariableHeader mqttMessageIdVariableHeader = MqttMessageIdVariableHeader.from(msgId);
        MqttSubAckPayload mqttSubAckPayload = new MqttSubAckPayload(grantedQoSList);
        return new MqttSubAckMessage(mqttFixedHeader, mqttMessageIdVariableHeader, mqttSubAckPayload);
    }

    private static int getMinSupportedQos(MqttQoS reqQoS) {
        return Math.min(reqQoS.value(), MAX_SUPPORTED_QOS_LVL.value());
    }

    public static MqttPubAckMessage createMqttPubAckMsg(int requestId) {
        MqttFixedHeader mqttFixedHeader =
                new MqttFixedHeader(PUBACK, false, AT_LEAST_ONCE, false, 0);
        MqttMessageIdVariableHeader mqttMsgIdVariableHeader =
                MqttMessageIdVariableHeader.from(requestId);
        return new MqttPubAckMessage(mqttFixedHeader, mqttMsgIdVariableHeader);
    }

    //检查是否连接
    private boolean checkConnected(ChannelHandlerContext ctx) {
        if (connected) {
            return true;
        } else {
            log.info("[{}] Closing current session due to invalid msg order [{}][{}]", sessionId);
            ctx.close();
            return false;
        }
    }

    private void checkGatewaySession() {
        Device device = deviceSessionCtx.getDevice();
        JsonNode infoNode = device.getAdditionalInfo();
        if (infoNode != null) {
            JsonNode gatewayNode = infoNode.get("gateway");
            if (gatewayNode != null && gatewayNode.asBoolean()) {
                gatewaySessionCtx = new GatewaySessionCtx(processor, deviceService, authService, relationService, deviceSessionCtx);
            }
        }
    }

    @Override
    public void operationComplete(Future<? super Void> future) throws Exception {
//        processor.process(SessionCloseMsg.onError(deviceSessionCtx.getSessionId()));
    }
}
