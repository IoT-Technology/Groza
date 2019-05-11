package com.sanshengshui.server.transport.mqtt.session;

import com.sanshengshui.server.common.data.Device;
import com.sanshengshui.server.common.transport.SessionMsgProcessor;
import com.sanshengshui.server.common.transport.auth.DeviceAuthService;
import com.sanshengshui.server.common.transport.session.DeviceAwareSessionContext;
import com.sanshengshui.server.transport.mqtt.MqttTopicMatcher;
import io.netty.handler.codec.mqtt.MqttQoS;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * @author james mu
 * @date 19-1-24 下午3:12
 */
public abstract class MqttDeviceAwareSessionContext extends DeviceAwareSessionContext {

    private final ConcurrentMap<MqttTopicMatcher, Integer> mqttQoSMap;

    public MqttDeviceAwareSessionContext(SessionMsgProcessor processor, DeviceAuthService authService, ConcurrentMap<MqttTopicMatcher, Integer> mqttQoSMap) {
        super(processor, authService);
        this.mqttQoSMap = mqttQoSMap;
    }

    public MqttDeviceAwareSessionContext(SessionMsgProcessor processor, DeviceAuthService authService, Device device, ConcurrentMap<MqttTopicMatcher, Integer> mqttQoSMap) {
        super(processor, authService, device);
        this.mqttQoSMap = mqttQoSMap;
    }

    public ConcurrentMap<MqttTopicMatcher, Integer> getMqttQoSMap() {
        return mqttQoSMap;
    }

    public MqttQoS getQoSForTopic(String topic) {
        List<Integer> qosList = mqttQoSMap.entrySet()
                .stream()
                .filter(entry -> entry.getKey().matches(topic))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
        if (!qosList.isEmpty()) {
            return MqttQoS.valueOf(qosList.get(0));
        } else {
            return MqttQoS.AT_LEAST_ONCE;
        }
    }
}
