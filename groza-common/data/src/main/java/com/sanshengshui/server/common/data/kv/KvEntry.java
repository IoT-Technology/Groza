package com.sanshengshui.server.common.data.kv;

import java.io.Serializable;
import java.util.Optional;

/**
 * @author james mu
 * @date 18-12-6 下午3:53
 */
public interface KvEntry extends Serializable {

    String getKey();

    DataType getDataType();

    Optional<String> getStrValue();

    Optional<Long> getLongValue();

    Optional<Boolean> getBooleanValue();

    Optional<Double> getDoubleValue();

    String getValueAsString();

    Object getValue();
}
