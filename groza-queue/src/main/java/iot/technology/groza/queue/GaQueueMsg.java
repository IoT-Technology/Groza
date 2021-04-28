package iot.technology.groza.queue;

import java.util.UUID;

/**
 * @author jamesmsw
 * @date 2021/4/28 10:41 上午
 */
public interface GaQueueMsg {

    UUID geKey();

    GaQueueMsgHeaders getHeaders();

    byte[] getData();
}
