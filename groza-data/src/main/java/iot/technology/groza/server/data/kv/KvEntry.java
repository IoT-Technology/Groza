package iot.technology.groza.server.data.kv;

import java.io.Serializable;
import java.util.Optional;

/**
 * @author mushuwei
 */
public interface KvEntry extends Serializable {

	String getKey();

	DataType getDataType();

	Optional<String> getStrValue();

	Optional<Long> getLongValue();

	Optional<Boolean> getBooleanValue();

	Optional<Double> getDoubleValue();

	Optional<String> getJsonValue();

	String getValueAsString();

	Object getValue();
}
