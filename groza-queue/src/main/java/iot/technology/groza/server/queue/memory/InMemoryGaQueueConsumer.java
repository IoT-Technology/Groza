package iot.technology.groza.server.queue.memory;

import iot.technology.groza.server.msg.queue.TopicPartitionInfo;
import iot.technology.groza.server.queue.GaQueueConsumer;
import iot.technology.groza.server.queue.GaQueueMsg;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author mushuwei
 * @date 2021/5/8 7:57 下午
 */
@Slf4j
public class InMemoryGaQueueConsumer<T extends GaQueueMsg> implements GaQueueConsumer<T> {
    private final InMemoryStorage storage = InMemoryStorage.getInstance();
    private volatile Set<TopicPartitionInfo> partitions;
    private volatile boolean stopped;
    private volatile boolean subscribed;
    private final String topic;

    public InMemoryGaQueueConsumer(String topic) {
        this.topic = topic;
        stopped = false;
    }

    @Override
    public String getTopic() {
        return topic;
    }

    @Override
    public void subscribe() {
        partitions = Collections.singleton(new TopicPartitionInfo(topic, null, null, true));
        subscribed = true;
    }

    @Override
    public void subscribe(Set<TopicPartitionInfo> partitions) {
        this.partitions = partitions;
        subscribed = true;
    }

    @Override
    public void unsubscribe() {
        stopped = true;
    }

    @Override
    public List<T> poll(long durationInMillis) {
        if (subscribed) {
            List<T> messages = partitions
                    .stream()
                    .map(tpi -> {
                        try {
                            return storage.get(tpi.getFullTopicName());
                        } catch (InterruptedException e) {
                            if (!stopped) {
                                log.error("Queue was interrupted.", e);
                            }
                            return Collections.emptyList();
                        }
                    })
                    .flatMap(List::stream)
                    .map(msg -> (T) msg).collect(Collectors.toList());
            if (messages.size() > 0) {
                return messages;
            }
            try {
                Thread.sleep(durationInMillis);
            } catch (InterruptedException e) {
                if (!stopped) {
                    log.error("Failed to sleep.", e);
                }
            }
        }
        return Collections.emptyList();
    }

    @Override
    public void commit() {

    }
}
