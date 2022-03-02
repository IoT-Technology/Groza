package iot.technology.groza.server.queue.common;

import iot.technology.groza.server.queue.GaQueueMsg;
import iot.technology.groza.server.queue.GaQueueMsgHeaders;
import lombok.Data;

import java.util.UUID;

/**
 * @author mushuwei
 * @date 2021/4/28 10:45 上午
 */
@Data
public class DefaultGaQueueMsg implements GaQueueMsg {
    private final UUID key;
    private final byte[] data;
    private final DefaultGaQueueMsgHeaders headers;

    public DefaultGaQueueMsg(GaQueueMsg msg) {
        this.key = msg.geKey();
        this.data = msg.getData();
        DefaultGaQueueMsgHeaders headers = new DefaultGaQueueMsgHeaders();
        msg.getHeaders().getData().forEach(headers::put);
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
        return data;
    }
}
