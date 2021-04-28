package iot.technology.groza.queue;

import java.util.Map;

/**
 * @author jamesmsw
 * @date 2021/4/27 9:22 下午
 */
public interface GaQueueMsgHeaders {

    byte[] put(String key, byte[] value);

    byte[] get(String key);

    Map<String, byte[]> getData();
}

