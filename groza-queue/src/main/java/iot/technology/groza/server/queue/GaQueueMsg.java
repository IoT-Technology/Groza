package iot.technology.groza.server.queue;

import java.util.UUID;

/**
 * @author mushuwei
 * @date 2021/4/28 10:41 上午
 */
public interface GaQueueMsg {

    UUID geKey();

    GaQueueMsgHeaders getHeaders();

    byte[] getData();
}
