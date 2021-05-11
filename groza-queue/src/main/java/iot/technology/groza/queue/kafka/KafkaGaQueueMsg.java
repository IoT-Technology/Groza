package iot.technology.groza.queue.kafka;

import iot.technology.groza.queue.GaQueueMsg;
import iot.technology.groza.queue.GaQueueMsgHeaders;
import iot.technology.groza.queue.common.DefaultGaQueueMsgHeaders;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.UUID;

/**
 * @author mushuwei
 */
public class KafkaGaQueueMsg implements GaQueueMsg {
    private final UUID key;
    private final GaQueueMsgHeaders headers;
    private final byte[] data;

    public KafkaGaQueueMsg(ConsumerRecord<String, byte[]> record) {
        this.key = UUID.fromString(record.key());
        GaQueueMsgHeaders headers = new DefaultGaQueueMsgHeaders();
        record.headers().forEach(header -> {
            headers.put(header.key(), header.value());
        });
        this.headers = headers;
        this.data = record.value();
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
