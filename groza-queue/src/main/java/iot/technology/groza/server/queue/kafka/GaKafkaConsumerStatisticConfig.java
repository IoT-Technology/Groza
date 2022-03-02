package iot.technology.groza.server.queue.kafka;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * @author mushuwei
 */
@Component
@ConditionalOnProperty(prefix = "queue", value = "type", havingValue = "kafka")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GaKafkaConsumerStatisticConfig {
    @Value("${queue.kafka.consumer-stats.enabled:true}")
    private Boolean enabled;
    @Value("${queue.kafka.consumer-stats.print-interval-ms:60000}")
    private Long printIntervalMs;
    @Value("${queue.kafka.consumer-stats.kafka-response-timeout-ms:1000}")
    private Long kafkaResponseTimeoutMs;
}
