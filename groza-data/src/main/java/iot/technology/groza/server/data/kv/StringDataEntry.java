package iot.technology.groza.server.data.kv;

import java.util.Objects;
import java.util.Optional;

/**
 * @author mushuwei
 */
public class StringDataEntry extends BasicKvEntry {

	private static final long serialVersionUID = 1L;

	private final String value;

	public StringDataEntry(String key, String value) {
		super(key);
		this.value = value;
	}

	@Override
	public DataType getDataType() {
		return DataType.STRING;
	}

	public Optional<String> getStrValue() {
		return Optional.ofNullable(value);
	}

	@Override
	public Object getValue() {
		return null;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof StringDataEntry)) {
			return false;
		}
		if (!super.equals(o)) {
			return false;
		}
		StringDataEntry that = (StringDataEntry) o;
		return Objects.equals(value, that.value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), value);
	}

	@Override
	public String toString() {
		return "StringDataEntry{" + "value='" + value + '\'' + "} " + super.toString();
	}


	@Override
	public String getValueAsString() {
		return value;
	}

}


