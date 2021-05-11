package iot.technology.groza.queue.kafka;

import iot.technology.groza.queue.GaQueueMsg;

import java.io.IOException;

/**
 * @author mushuwei
 */
public interface GaKafkaDecoder<T> {

    T decode(GaQueueMsg msg) throws IOException;
}
