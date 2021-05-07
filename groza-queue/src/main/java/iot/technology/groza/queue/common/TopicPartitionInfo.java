package iot.technology.groza.queue.common;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;
import java.util.Optional;

/**
 * @author mushuwei
 * @date 2021/5/7 11:28 下午
 */
@ToString
public class TopicPartitionInfo {

    private final String topic;
    //TODO 租户ID设置
    private final String tenantId;
    private final Integer partition;
    @Getter
    private final String fullTopicName;
    @Getter
    private final boolean myPartition;

    @Builder
    public TopicPartitionInfo(String topic, String tenantId, Integer partition, boolean myPartition) {
        this.topic = topic;
        this.tenantId = tenantId;
        this.partition = partition;
        this.myPartition = myPartition;
        String tmp = topic;
        if (tenantId != null) {
            tmp += "." + tenantId;
        }
        if (partition != null) {
            tmp += "." + partition;
        }
        this.fullTopicName = tmp;
    }

    public TopicPartitionInfo newByTopic(String topic) {
        return new TopicPartitionInfo(topic, this.tenantId, this.partition, this.myPartition);
    }

    public String getTopic() {
        return topic;
    }

    public Optional<String> getTenantId() {
        return Optional.ofNullable(tenantId);
    }

    public Optional<Integer> getPartition() {
        return Optional.ofNullable(partition);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TopicPartitionInfo that = (TopicPartitionInfo) o;
        return topic.equals(that.topic) &&
                Objects.equals(tenantId, that.tenantId) &&
                Objects.equals(partition, that.partition) &&
                fullTopicName.equals(that.fullTopicName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullTopicName);
    }
}
