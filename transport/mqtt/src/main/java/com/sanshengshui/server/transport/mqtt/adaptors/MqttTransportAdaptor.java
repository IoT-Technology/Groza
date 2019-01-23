package com.sanshengshui.server.transport.mqtt.adaptors;

import com.sanshengshui.server.common.transport.TransportAdaptor;
import com.sanshengshui.server.transport.mqtt.session.DeviceSessionCtx;
import io.netty.handler.codec.mqtt.MqttMessage;

/**
 * @author james mu
 * @date 19-1-23 上午9:32
 */
public interface MqttTransportAdaptor extends TransportAdaptor<DeviceSessionCtx, MqttMessage,MqttMessage> {
}
