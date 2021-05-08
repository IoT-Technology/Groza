package iot.technology.groza.queue.memory;

import iot.technology.groza.queue.GaQueueConsumer;
import iot.technology.groza.queue.GaQueueMsg;
import iot.technology.groza.queue.common.TopicPartitionInfo;

import java.util.List;
import java.util.Set;

/**
 * @author mushuwei
 * @date 2021/5/8 7:57 下午
 */
public class InMemoryGaQueueConsumer<T extends GaQueueMsg> implements GaQueueConsumer<T> {

    @Override
    public String getTopic() {
        return null;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void subscribe(Set<TopicPartitionInfo> partitions) {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public List<T> poll(long durationInMillis) {
        return null;
    }

    @Override
    public void commit() {

    }
}
