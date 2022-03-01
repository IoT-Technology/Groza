package iot.technology.server.transport.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author mushuwei
 */
@Configuration
@Slf4j
public class JsonConverterConfig {

	@Value("${transport.json.type_cast_enabled:true}")
	public void setIsJsonTypeCastEnabled(boolean jsonTypeCastEnabled) {

	}

	@Value("${transport.json.max_string_value_length:0}")
	public void setMaxStringValueLength(int maxStringValueLength) {

	}
}
