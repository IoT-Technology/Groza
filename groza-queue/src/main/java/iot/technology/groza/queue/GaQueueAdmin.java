package iot.technology.groza.queue;

/**
 * @author mushuwei
 */
public interface GaQueueAdmin {

    void createTopicIfNotExists(String topic);

    void destroy();
}
