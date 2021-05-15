package iot.technology.groza.queue.kafka;

import iot.technology.groza.queue.GaQueueAdmin;
import iot.technology.groza.queue.GaQueueMsg;
import iot.technology.groza.queue.common.AbstractGaQueueConsumerTemplate;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

/**
 * @author mushuwei
 */
public class GaKafkaConsumerTemplate<T extends GaQueueMsg> extends AbstractGaQueueConsumerTemplate<ConsumerRecord<String, byte[]>, T> {

    private final GaQueueAdmin admin;
    private final KafkaConsumer<String, byte[]> consumer;
    private final GaKafkaDecoder<T> decoder;

    private final String groupId;

    private GaKafkaConsumerTemplate(GaKafkaSettings settings, GaKafkaDecoder<T> decoder,
                                    String clientId, String groupId, String topic,
                                    GaQueueAdmin admin) {
        super(topic);
        Properties props = settings.toConsumerProps();
        props.put(ConsumerConfig.CLIENT_ID_CONFIG, clientId);
        if (groupId != null) {
            props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        }
        this.groupId = groupId;
        this.admin = admin;
        this.consumer = new KafkaConsumer<>(props);
        this.decoder = decoder;

    }

    @Override
    protected List<ConsumerRecord<String, byte[]>> doPoll(long durationInMillis) {
        ConsumerRecords<String, byte[]> records = consumer.poll(Duration.ofMillis(durationInMillis));
        if (records.isEmpty()) {
            return Collections.emptyList();
        } else {
            List<ConsumerRecord<String, byte[]>> recordList = new ArrayList<>(256);
            records.forEach(recordList::add);
            return recordList;
        }
    }

    @Override
    protected T decode(ConsumerRecord<String, byte[]> record) throws IOException {
        return decoder.decode(new KafkaGaQueueMsg(record));
    }

    @Override
    protected void doSubscribe(List<String> topicNames) {
        if (!topicNames.isEmpty()) {
            topicNames.forEach(admin::createTopicIfNotExists);
            consumer.subscribe(topicNames);
        } else {
            consumer.unsubscribe();
        }
    }

    @Override
    protected void doCommit() {
        consumer.commitAsync();
    }

    @Override
    protected void doUnsubscribe() {
        if (consumer != null) {
            consumer.unsubscribe();
            consumer.close();
        }
    }
}
