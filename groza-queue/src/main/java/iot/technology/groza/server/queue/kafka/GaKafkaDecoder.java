package iot.technology.groza.server.queue.kafka;

import iot.technology.groza.server.queue.GaQueueMsg;

import java.io.IOException;

/**
 * @author mushuwei
 */
public interface GaKafkaDecoder<T> {

    T decode(GaQueueMsg msg) throws IOException;
}
