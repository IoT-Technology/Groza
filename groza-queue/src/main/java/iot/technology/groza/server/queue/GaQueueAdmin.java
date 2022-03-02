package iot.technology.groza.server.queue;

/**
 * @author mushuwei
 */
public interface GaQueueAdmin {

    void createTopicIfNotExists(String topic);

    void destroy();
}
