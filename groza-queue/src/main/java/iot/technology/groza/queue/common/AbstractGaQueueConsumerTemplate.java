package iot.technology.groza.queue.common;

import iot.technology.groza.msg.queue.TopicPartitionInfo;
import iot.technology.groza.queue.GaQueueConsumer;
import iot.technology.groza.queue.GaQueueMsg;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * @author mushuwei
 */
@Slf4j
public abstract class AbstractGaQueueConsumerTemplate<R, T extends GaQueueMsg> implements GaQueueConsumer<T> {

    private volatile boolean subscribed;
    protected volatile boolean stopped;
    protected volatile Set<TopicPartitionInfo> partitions;
    protected final Lock consumerLock = new ReentrantLock();

    @Getter
    private final String topic;

    public AbstractGaQueueConsumerTemplate(String topic) {
        this.topic = topic;
    }


    @Override
    public void subscribe() {
        consumerLock.lock();
        try {
            partitions = Collections.singleton(new TopicPartitionInfo(topic, null, null, true));
            subscribed = false;
        } finally {
            consumerLock.unlock();
        }
    }

    @Override
    public void subscribe(Set<TopicPartitionInfo> partitions) {
        consumerLock.lock();
        try {
            this.partitions = partitions;
            subscribed = false;
        } finally {
            consumerLock.unlock();
        }
    }

    @Override
    public void unsubscribe() {
        stopped = true;
        consumerLock.lock();
        try {
            doUnsubscribe();
        } finally {
            consumerLock.unlock();
        }
    }

    @Override
    public List<T> poll(long durationInMillis) {
        if (!subscribed && partitions == null) {
            try {
                Thread.sleep(durationInMillis);
            } catch (InterruptedException e) {
                log.debug("Failed to await subscription", e);
            }
        } else {
            long pollStartTs = System.currentTimeMillis();
            consumerLock.lock();
            try {
                if (!subscribed) {
                    List<String> topicNames = partitions.stream().map(TopicPartitionInfo::getFullTopicName).collect(Collectors.toList());
                    doSubscribe(topicNames);
                    subscribed = true;
                }
                List<R> records;
                if (partitions.isEmpty()) {
                    records = Collections.emptyList();
                } else {
                    records = doPoll(durationInMillis);
                }
                if (!records.isEmpty()) {
                    List<T> result = new ArrayList<>(records.size());
                    records.forEach(record -> {
                        try {
                            if (record != null) {
                                result.add(decode(record));
                            }
                        } catch (IOException e) {
                            log.error("Failed decode record: [{}]", record);
                            throw new RuntimeException("Failed to decode record: ", e);
                        }
                    });
                    return result;
                } else {
                    long pollDuration = System.currentTimeMillis() - pollStartTs;
                    if (pollDuration < durationInMillis) {
                        try {
                            Thread.sleep(durationInMillis - pollDuration);
                        } catch (InterruptedException e) {
                            if (!stopped) {
                                log.error("Failed to wait.", e);
                            }
                        }
                    }
                }
            } finally {
                consumerLock.unlock();
            }
        }
        return Collections.emptyList();
    }

    @Override
    public void commit() {
        consumerLock.lock();
        try {
            doCommit();
        } finally {
            consumerLock.unlock();
        }
    }

    abstract protected List<R> doPoll(long durationInMillis);

    abstract protected T decode(R record) throws IOException;

    abstract protected void doSubscribe(List<String> topicNames);

    abstract protected void doCommit();

    abstract protected void doUnsubscribe();

}
