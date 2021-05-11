package iot.technology.groza.queue.kafka;

import iot.technology.groza.queue.GaQueueMsgMetadata;
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
