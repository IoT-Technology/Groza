package com.sanshengshui.server.common.data.kv;

/**
 * @author james mu
 * @date 19-1-30 下午5:12
 * @description
 */
public interface TsKvQuery {

    String getKey();

    long getStartTs();

    long getEndTs();

    long getInterval();

    int getLimit();

    Aggregation getAggregation();
}
