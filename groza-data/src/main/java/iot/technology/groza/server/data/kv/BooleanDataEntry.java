package iot.technology.groza.server.data.kv;

import java.util.Objects;

/**
 * @author mushuwei
 */
public class BooleanDataEntry extends BasicKvEntry {
	private final Boolean value;

	public BooleanDataEntry(String key, Boolean value) {
		super(key);
		this.value = value;
	}

	@Override
	public DataType getDataType() {
		return DataType.BOOLEAN;
	}

	@Override
	public String getValueAsString() {
		return Boolean.toString(value);
	}

	@Override
	public Object getValue() {
		return value;
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), value);
	}

	@Override
	public String toString() {
		return "BooleanDataEntry{" +
				"value=" + value +
				"} " + super.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof BooleanDataEntry)) {
			return false;
		}
		if (!super.equals(o)) {
			return false;
		}
		BooleanDataEntry that = (BooleanDataEntry) o;
		return Objects.equals(value, that.value);
	}
}
