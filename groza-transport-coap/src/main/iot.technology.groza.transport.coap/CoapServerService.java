import org.eclipse.californium.core.CoapServer;

import java.net.UnknownHostException;

/**
 * @author mushuwei
 */
public interface CoapServerService {

	CoapServer getCoapServer() throws UnknownHostException;

	long getTimeout();
}
