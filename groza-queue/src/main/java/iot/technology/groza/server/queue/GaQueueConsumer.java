package iot.technology.groza.server.queue;


import iot.technology.groza.server.msg.queue.TopicPartitionInfo;

import java.util.List;
import java.util.Set;

/**
 * @author mushuwei
 * @date 2021/5/7 11:25 下午
 */
public interface GaQueueConsumer<T extends GaQueueMsg> {

    String getTopic();

    void subscribe();

    void subscribe(Set<TopicPartitionInfo> partitions);

    void unsubscribe();

    List<T> poll(long durationInMillis);

    void commit();
}
