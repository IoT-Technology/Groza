package iot.technology.groza.server.queue.kafka;

/**
 * @author mushuwei
 */
public interface GaKafkaEncoder<T> {

    byte[] encode(T value);
}
