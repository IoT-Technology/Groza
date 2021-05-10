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

    private final InMemoryStorage storage = InMemoryStorage.getInstance();

    private final String defaultTopic;

    public InMemoryGaQueueProducer(String defaultTopic) {
        this.defaultTopic = defaultTopic;
    }

    @Override
    public void init() {

    }

    @Override
    public String getDefaultTopic() {
        return null;
    }

    @Override
    public void send(TopicPartitionInfo tpi, T msg, GaQueueCallback callback) {
        boolean result = storage.put(tpi.getFullTopicName(), msg);
        if (result) {
            if (callback != null) {
                callback.onSuccess(null);
            }
        } else {
            if (callback != null) {
                callback.onFailure(new RuntimeException("Failure add msg to InMemoryQueue"));
            }
        }
    }

    @Override
    public void stop() {

    }
}
