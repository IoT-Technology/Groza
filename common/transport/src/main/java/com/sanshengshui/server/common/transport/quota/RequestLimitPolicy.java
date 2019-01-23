package com.sanshengshui.server.common.transport.quota;

/**
 * @author james mu
 * @date 19-1-23 下午3:32
 */
public abstract class RequestLimitPolicy {

    private final long limit;

    public RequestLimitPolicy(long limit){
        this.limit = limit;
    }

    public boolean isValid(long currentValue){
        return currentValue <= limit;
    }
}
