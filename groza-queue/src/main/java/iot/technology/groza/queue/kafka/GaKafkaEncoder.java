package iot.technology.groza.queue.kafka;

/**
 * @author mushuwei
 */
public interface GaKafkaEncoder<T> {

    byte[] encode(T value);
}
