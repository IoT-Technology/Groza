package iot.technology.groza.server.queue.common;

import iot.technology.groza.server.queue.GaQueueMsgHeaders;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jamesmsw
 * @date 2021/4/28 10:36 上午
 */
public class DefaultGaQueueMsgHeaders implements GaQueueMsgHeaders {

    protected final Map<String, byte[]> data = new HashMap<>();

    @Override
    public byte[] put(String key, byte[] value) {
        return data.put(key, value);
    }

    @Override
    public byte[] get(String key) {
        return data.get(key);
    }

    @Override
    public Map<String, byte[]> getData() {
        return data;
    }
}
