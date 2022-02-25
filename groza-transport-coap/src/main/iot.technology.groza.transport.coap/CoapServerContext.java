import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author mushuwei
 */
@Slf4j
@Component
public class CoapServerContext {

	@Getter
	@Value("${transport.coap.bind_address}")
	private String host;

	@Getter
	@Value("${transport.coap.bind_port}")
	private Integer port;

	@Getter
	@Value("${transport.coap.timeout}")
	private Long timeout;
}
