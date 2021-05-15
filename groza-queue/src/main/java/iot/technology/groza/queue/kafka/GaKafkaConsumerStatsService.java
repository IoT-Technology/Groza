package iot.technology.groza.queue.kafka;

import iot.technology.groza.queue.common.GaThreadFactory;
import iot.technology.groza.queue.discovery.PartitionService;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author mushuwei
 */
@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "queue", value = "type", havingValue = "kafka")
public class GaKafkaConsumerStatsService {
    private final Set<String> monitoredGroups = ConcurrentHashMap.newKeySet();

    private final GaKafkaSettings kafkaSettings;
    private final GaKafkaConsumerStatisticConfig statsConfig;
    private final PartitionService partitionService;

    private AdminClient adminClient;
    private Consumer<String, byte[]> consumer;
    private ScheduledExecutorService statsPrintScheduler;

    @PostConstruct
    public void init() {
        if (!statsConfig.getEnabled()) {
            return;
        }
        this.adminClient = AdminClient.create(kafkaSettings.toAdminProps());
        this.statsPrintScheduler = Executors.newSingleThreadScheduledExecutor(GaThreadFactory.forName("kafka-consumer-stats"));

        Properties consumerProps = kafkaSettings.toConsumerProps();
        consumerProps.put(ConsumerConfig.CLIENT_ID_CONFIG, "consumer-stats-loader-client");
        consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, "consumer-stats-loader-client-group");
        this.consumer = new KafkaConsumer<>(consumerProps);

    }

    private void startLogScheduling() {
        Duration timeoutDuration = Duration.ofMillis(statsConfig.getKafkaResponseTimeoutMs());
        statsPrintScheduler.scheduleWithFixedDelay(() -> {

        }, statsConfig.getPrintIntervalMs(), statsConfig.getPrintIntervalMs(), TimeUnit.MILLISECONDS);
    }


    @Builder
    @Data
    private static class GroupTopicStats {
        private String topic;
        private int partition;
        private long committedOffset;
        private long endOffset;
        private long lag;

        @Override
        public String toString() {
            return "[" +
                    "topic='" + topic + '\'' +
                    ", partition=" + partition +
                    ", committedOffset=" + committedOffset +
                    ", endOffset=" + endOffset +
                    ", lag=" + lag +
                    ']';
        }
    }
}
