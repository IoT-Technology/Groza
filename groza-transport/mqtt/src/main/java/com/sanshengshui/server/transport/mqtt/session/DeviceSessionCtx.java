package com.sanshengshui.server.transport.mqtt.session;

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
import com.sanshengshui.server.transport.mqtt.MqttTopicMatcher;
import com.sanshengshui.server.transport.mqtt.adaptors.MqttTransportAdaptor;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.mqtt.MqttFixedHeader;
import io.netty.handler.codec.mqtt.MqttMessage;
import io.netty.handler.codec.mqtt.MqttMessageType;
import io.netty.handler.codec.mqtt.MqttQoS;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author james mu
 * @date 19-1-23 上午9:34
 */
@Slf4j
public class DeviceSessionCtx extends MqttDeviceAwareSessionContext {

    private final MqttTransportAdaptor adaptor;
    private final MqttSessionId sessionId;
    private ChannelHandlerContext channel;
    private volatile boolean allowAttributeResponses;
    private AtomicInteger msgIdSeq = new AtomicInteger(0);

    public DeviceSessionCtx(SessionMsgProcessor processor, DeviceAuthService authService, MqttTransportAdaptor adaptor, ConcurrentMap<MqttTopicMatcher, Integer> mqttQoSMap){
        super(processor, authService, mqttQoSMap);
        this.adaptor = adaptor;
        this.sessionId = new MqttSessionId();
    }

    @Override
    public SessionType getSessionType() {
        return SessionType.ASYNC;
    }

    @Override
    public void onMsg(SessionActorToAdaptorMsg msg) throws SessionException {
        try {
            adaptor.convertToAdaptorMsg(this, msg).ifPresent(this::pushToNetwork);
        } catch (AdaptorException e) {
            //TODO: close channel with disconnect;
            logAndWrap(e);
        }
    }

    private void logAndWrap(AdaptorException e) throws SessionException {
        log.warn("Failed to convert msg: {}", e.getMessage(), e);
        throw new SessionException(e);
    }


    private void pushToNetwork(MqttMessage msg){
        channel.writeAndFlush(msg);
    }

    @Override
    public void onMsg(SessionCtrlMsg msg) throws SessionException {
        if (msg instanceof SessionCloseMsg){
            pushToNetwork(new MqttMessage(new MqttFixedHeader(MqttMessageType.DISCONNECT, false, MqttQoS.AT_MOST_ONCE, false, 0)));
            channel.close();
        }
    }


    @Override
    public boolean isClosed() {
        return false;
    }

    @Override
    public long getTimeout() {
        return 0;
    }

    @Override
    public SessionId getSessionId() {
        return sessionId;
    }

    public void setChannel(ChannelHandlerContext channel) {
        this.channel = channel;
    }

    public void setAllowAttributeResponses() {
        allowAttributeResponses = true;
    }

    public void setDisallowAttributeResponses() {
        allowAttributeResponses = false;
    }

    public int nextMsgId() {
        return msgIdSeq.incrementAndGet();
    }
}
