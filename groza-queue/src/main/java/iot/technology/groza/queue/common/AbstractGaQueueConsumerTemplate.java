package iot.technology.groza.queue.common;

import iot.technology.groza.queue.GaQueueConsumer;
import iot.technology.groza.queue.GaQueueMsg;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Set;

/**
 * @author mushuwei
 */
@Slf4j
public class AbstractGaQueueConsumerTemplate<R, T extends GaQueueMsg> implements GaQueueConsumer<T> {

    private volatile boolean subscribed;
    protected volatile boolean stopped;

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
