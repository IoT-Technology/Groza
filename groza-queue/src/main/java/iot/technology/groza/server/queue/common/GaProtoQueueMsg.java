package iot.technology.groza.server.queue.common;

import iot.technology.groza.server.queue.GaQueueMsg;
import iot.technology.groza.server.queue.GaQueueMsgHeaders;
import lombok.Data;

import java.util.UUID;

/**
 * @author jamesmsw
 * @date 2021/4/28 11:06 上午
 */
@Data
public class GaProtoQueueMsg<T extends com.google.protobuf.GeneratedMessageV3> implements GaQueueMsg {

    private final UUID key;
    protected final T value;
    private final GaQueueMsgHeaders headers;

    public GaProtoQueueMsg(UUID key, T value) {
        this(key, value, new DefaultGaQueueMsgHeaders());
    }

    public GaProtoQueueMsg(UUID key, T value, GaQueueMsgHeaders headers) {
        this.key = key;
        this.value = value;
        this.headers = headers;
    }

    @Override
    public UUID geKey() {
        return key;
    }

    @Override
    public GaQueueMsgHeaders getHeaders() {
        return headers;
    }

    @Override
    public byte[] getData() {
        return value.toByteArray();
    }
}
