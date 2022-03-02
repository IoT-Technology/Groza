package iot.technology.groza.server.queue.kafka;

import iot.technology.groza.server.queue.GaQueueMsgMetadata;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.kafka.clients.producer.RecordMetadata;

/**
 * @author mushuwei
 */
@Data
@AllArgsConstructor
public class KafkaGaQueueMsgMetadata implements GaQueueMsgMetadata {
    private RecordMetadata metadata;
}
