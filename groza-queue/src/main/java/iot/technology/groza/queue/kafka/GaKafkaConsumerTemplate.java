package iot.technology.groza.queue.kafka;

import iot.technology.groza.queue.GaQueueMsg;
import iot.technology.groza.queue.common.AbstractGaQueueConsumerTemplate;
import lombok.Builder;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.io.IOException;
import java.util.List;

/**
 * @author mushuwei
 */
public class GaKafkaConsumerTemplate<T extends GaQueueMsg> extends AbstractGaQueueConsumerTemplate<ConsumerRecord<String, byte[]>, T> {
    

    @Builder
    private GaKafkaConsumerTemplate(String topic) {
        super(topic);
    }

    @Override
    protected List<ConsumerRecord<String, byte[]>> doPoll(long durationInMillis) {
        return null;
    }

    @Override
    protected T decode(ConsumerRecord<String, byte[]> record) throws IOException {
        return null;
    }

    @Override
    protected void doSubscribe(List<String> topicNames) {

    }

    @Override
    protected void doCommit() {

    }

    @Override
    protected void doUnsubscribe() {

    }
}
