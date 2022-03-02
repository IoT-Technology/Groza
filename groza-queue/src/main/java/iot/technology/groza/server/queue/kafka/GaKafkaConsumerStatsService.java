package iot.technology.groza.server.queue.kafka;

import iot.technology.groza.server.msg.queue.ServiceType;
import iot.technology.groza.server.queue.common.GaThreadFactory;
import iot.technology.groza.server.queue.discovery.PartitionService;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.Duration;
import java.util.*;
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
    public static final String defaultTenantId = "123";

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

        startLogScheduling();
    }

    private void startLogScheduling() {
        Duration timeoutDuration = Duration.ofMillis(statsConfig.getKafkaResponseTimeoutMs());
        statsPrintScheduler.scheduleWithFixedDelay(() -> {
            if (!isStatsPrintRequired()) {
                return;
            }
            for (String groupId : monitoredGroups) {
                try {
                    Map<TopicPartition, OffsetAndMetadata> groupOffsets =
                            adminClient.listConsumerGroupOffsets(groupId).partitionsToOffsetAndMetadata()
                                    .get(statsConfig.getKafkaResponseTimeoutMs(), TimeUnit.MILLISECONDS);
                    Map<TopicPartition, Long> endOffsets = consumer.endOffsets(groupOffsets.keySet(), timeoutDuration);

                    List<GroupTopicStats> lagTopicsStats = getTopicsStatsWithLag(groupOffsets, endOffsets);
                    if (!lagTopicsStats.isEmpty()) {
                        StringBuilder builder = new StringBuilder();
                        for (int i = 0; i < lagTopicsStats.size(); i++) {
                            builder.append(lagTopicsStats.get(i).toString());
                            if (i != lagTopicsStats.size() - 1) {
                                builder.append(", ");
                            }
                        }
                        log.info("[{}] Topic partitions with lag: [{}].", groupId, builder.toString());
                    }
                } catch (Exception e) {
                    log.warn("[{}] Failed to get consumer group stats. Reason - {}.", groupId, e.getMessage());
                    log.trace("Detailed error: ", e);
                }
            }
        }, statsConfig.getPrintIntervalMs(), statsConfig.getPrintIntervalMs(), TimeUnit.MILLISECONDS);
    }

    private boolean isStatsPrintRequired() {
        boolean isMyRuleEnginePartition =
                partitionService.resolve(ServiceType.GA_RULE_ENGINE, defaultTenantId, defaultTenantId).isMyPartition();
        boolean isMyCorePartition = partitionService.resolve(ServiceType.GA_CORE, defaultTenantId, defaultTenantId).isMyPartition();
        return log.isInfoEnabled() && (isMyRuleEnginePartition || isMyCorePartition);
    }

    private List<GroupTopicStats> getTopicsStatsWithLag(Map<TopicPartition, OffsetAndMetadata> groupOffsets,
                                                        Map<TopicPartition, Long> endOffsets) {
        List<GroupTopicStats> consumerGroupStats = new ArrayList<>();
        for (TopicPartition topicPartition : groupOffsets.keySet()) {
            long endOffset = endOffsets.get(topicPartition);
            long committedOffset = groupOffsets.get(topicPartition).offset();
            long lag = endOffset - committedOffset;
            if (lag != 0) {
                GroupTopicStats groupTopicStats = GroupTopicStats.builder()
                        .topic(topicPartition.topic())
                        .partition(topicPartition.partition())
                        .committedOffset(committedOffset)
                        .endOffset(endOffset)
                        .lag(lag)
                        .build();
                consumerGroupStats.add(groupTopicStats);
            }
        }
        return consumerGroupStats;
    }

    public void registerClientGroup(String groupId) {
        if (statsConfig.getEnabled() && !StringUtils.isEmpty(groupId)) {
            monitoredGroups.add(groupId);
        }
    }

    public void unregisterClientGroup(String groupId) {
        if (statsConfig.getEnabled() && !StringUtils.isEmpty(groupId)) {
            monitoredGroups.remove(groupId);
        }
    }

    @PreDestroy
    public void destroy() {
        if (statsPrintScheduler != null) {
            statsPrintScheduler.shutdownNow();
        }
        if (adminClient != null) {
            adminClient.close();
        }
        if (consumer != null) {
            consumer.close();
        }
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
