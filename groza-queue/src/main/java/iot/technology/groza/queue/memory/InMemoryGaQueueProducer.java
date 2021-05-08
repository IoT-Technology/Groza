package iot.technology.groza.queue.memory;

import iot.technology.groza.queue.GaQueueCallback;
import iot.technology.groza.queue.GaQueueMsg;
import iot.technology.groza.queue.GaQueueProducer;
import iot.technology.groza.queue.common.TopicPartitionInfo;

/**
 * @author mushuwei
 * @date 2021/5/8 7:58 下午
 */
public class InMemoryGaQueueProducer<T extends GaQueueMsg> implements GaQueueProducer<T> {

    @Override
    public void init() {

    }

    @Override
    public String getDefaultTopic() {
        return null;
    }

    @Override
    public void send(TopicPartitionInfo tpi, T msg, GaQueueCallback callback) {

    }

    @Override
    public void stop() {

    }
}
