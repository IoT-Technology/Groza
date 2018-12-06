package com.sanshengshui.server.common.msg.core;

import java.io.Serializable;

/**
 * @author james mu
 * @date 18-12-6 下午4:36
 */
public class BasicRequest implements Serializable {
    public static final Integer DEFAULT_REQUEST_ID = 0;

    private final Integer requestId;

    public BasicRequest(Integer requestId) {
        this.requestId = requestId;
    }

    public Integer getRequestId() {
        return requestId;
    }
}
