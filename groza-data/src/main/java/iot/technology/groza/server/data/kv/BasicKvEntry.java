package iot.technology.groza.server.data.kv;

import java.util.Objects;
import java.util.Optional;

/**
 * @author mushuwei
 */
public abstract class BasicKvEntry implements KvEntry {


	private final String key;

	protected BasicKvEntry(String key) {
		this.key = key;
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public Optional<String> getStrValue() {
		return Optional.ofNullable(null);
	}

	@Override
	public Optional<Long> getLongValue() {
		return Optional.ofNullable(null);
	}

	@Override
	public Optional<Boolean> getBooleanValue() {
		return Optional.ofNullable(null);
	}

	@Override
	public Optional<Double> getDoubleValue() {
		return Optional.ofNullable(null);
	}

	@Override
	public Optional<String> getJsonValue() {
		return Optional.ofNullable(null);
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof BasicKvEntry)) {
			return false;
		}
		BasicKvEntry that = (BasicKvEntry) o;
		return Objects.equals(key, that.key);
	}

	public int hashCode() {
		return Objects.hash(key);
	}

	public String toString() {
		return "BasicKvEntry{" +
				"key='" + key + '\'' +
				'}';
	}

}
