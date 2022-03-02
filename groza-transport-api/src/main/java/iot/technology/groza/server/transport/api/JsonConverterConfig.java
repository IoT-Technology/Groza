package iot.technology.groza.server.transport.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author mushuwei
 */
@Configuration
public class JsonConverterConfig {

	@Value("${transport.json.type_cast_enabled:true}")
	public void setIsJsonTypeCastEnabled(boolean jsonTypeCastEnabled) {

	}

	@Value("${transport.json.max_string_value_length:0}")
	public void setMaxStringValueLength(int maxStringValueLength) {

	}
}
