package com.sanshengshui.server.transport.mqtt.session;

import com.sanshengshui.server.common.data.id.SessionId;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author james mu
 * @date 19-1-2 下午1:37
 */
public class MqttSessionId implements SessionId {
    public static final AtomicLong idSeq = new AtomicLong();

    private final long id;

    public MqttSessionId(){
        this.id = idSeq.incrementAndGet();
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MqttSessionId that = (MqttSessionId) o;

        return id == that.id;
    }

    @Override
    public String toString() {
        return "MqttSessionId{" +
                "id=" + id +
                "}";
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toUidStr() {
        return "mqtt" + id;
    }
}
