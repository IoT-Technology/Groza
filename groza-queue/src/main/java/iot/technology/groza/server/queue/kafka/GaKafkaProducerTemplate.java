package iot.technology.groza.server.queue.kafka;

import iot.technology.groza.server.msg.queue.TopicPartitionInfo;
import iot.technology.groza.server.queue.GaQueueAdmin;
import iot.technology.groza.server.queue.GaQueueCallback;
import iot.technology.groza.server.queue.GaQueueMsg;
import iot.technology.groza.server.queue.GaQueueProducer;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.util.StringUtils;

import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author mushuwei
 */
@Slf4j
public class GaKafkaProducerTemplate<T extends GaQueueMsg> implements GaQueueProducer<T> {

    private final KafkaProducer<String, byte[]> producer;

    @Getter
    private final String defaultTopic;

    @Getter
    private final GaKafkaSettings settings;

    private final GaQueueAdmin admin;

    private final Set<TopicPartitionInfo> topics;

    public GaKafkaProducerTemplate(GaKafkaSettings settings, String defaultTopic, String clientId, GaQueueAdmin admin) {
        Properties props = settings.toProducerProps();
        if (!StringUtils.isEmpty(clientId)) {
            props.put(ProducerConfig.CLIENT_ID_CONFIG, clientId);
        }
        this.settings = settings;
        this.producer = new KafkaProducer<>(props);
        this.defaultTopic = defaultTopic;
        this.admin = admin;
        topics = ConcurrentHashMap.newKeySet();
    }

    @Override
    public void init() {
    }


    @Override
    public void send(TopicPartitionInfo tpi, T msg, GaQueueCallback callback) {
        createTopicIfNotExist(tpi);
        String key = msg.geKey().toString();
        byte[] data = msg.getData();
        ProducerRecord<String, byte[]> record;
        Iterable<Header> headers = msg.getHeaders().getData().entrySet()
                .stream().map(e -> new RecordHeader(e.getKey(), e.getValue())).collect(Collectors.toList());
        record = new ProducerRecord<>(tpi.getFullTopicName(), null, key, data, headers);
        producer.send(record, (metadata, exception) -> {
            if (exception == null) {
                if (callback != null) {
                    callback.onSuccess(new KafkaGaQueueMsgMetadata(metadata));
                }
            } else {
                if (callback != null) {
                    callback.onFailure(exception);
                } else {
                    log.warn("Producer template failure: {}", exception.getMessage(), exception);
                }
            }
        });
    }

    @Override
    public void stop() {
        if (producer != null) {
            producer.close();
        }

    }

    private void createTopicIfNotExist(TopicPartitionInfo tpi) {
        if (topics.contains(tpi)) {
            return;
        }
        admin.createTopicIfNotExists(tpi.getFullTopicName());
        topics.add(tpi);
    }
}
