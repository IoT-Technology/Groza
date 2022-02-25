import lombok.extern.slf4j.Slf4j;
import org.eclipse.californium.core.coap.OptionSet;
import org.eclipse.californium.core.network.Exchange;
import org.eclipse.californium.core.server.DelivererException;
import org.eclipse.californium.core.server.ServerMessageDeliverer;
import org.eclipse.californium.core.server.resources.Resource;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author mushuwei
 */
@Slf4j
public class GaCoapServerMessageDeliverer extends ServerMessageDeliverer {

	public GaCoapServerMessageDeliverer(Resource root) {
		super(root);
	}

	@Override
	protected Resource findResource(Exchange exchange) throws DelivererException {
		return super.findResource(exchange);
	}

	private void validateUriPath(Exchange exchange) {
		OptionSet options = exchange.getRequest().getOptions();
		List<String> uriPathList = options.getUriPath();
		String path = toPath(uriPathList);
		if (path != null) {
			options.setUriPath(path);
			exchange.getRequest().setOptions(options);
		}
	}

	private String toPath(List<String> list) {
		if (!CollectionUtils.isEmpty(list) && list.size() == 1) {
			final String slash = "/";
			String path = list.get(0);
			if (path.startsWith(slash)) {
				path = path.substring(slash.length());
			}
			return path;
		}
		return null;
	}
}
