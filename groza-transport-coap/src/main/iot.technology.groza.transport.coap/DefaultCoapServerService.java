import lombok.extern.slf4j.Slf4j;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.config.CoapConfig;
import org.eclipse.californium.core.network.CoapEndpoint;
import org.eclipse.californium.core.server.resources.Resource;
import org.eclipse.californium.elements.config.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import static org.eclipse.californium.core.config.CoapConfig.DEFAULT_BLOCKWISE_STATUS_LIFETIME_IN_SECONDS;


/**
 * @author mushuwei
 */
@Slf4j
@Component
public class DefaultCoapServerService implements CoapServerService {

	@Autowired
	private CoapServerContext coapServerContext;

	private CoapServer server;

	@PostConstruct
	private void init() throws UnknownHostException {
		createCoapServer();
	}

	@PreDestroy
	private void shutdown() {
		log.info("Stopping CoAP server!");
		server.destroy();
		log.info("CoAP server stopped!");
	}

	@Override
	public CoapServer getCoapServer() throws UnknownHostException {
		if (server != null) {
			return server;
		} else {
			return createCoapServer();
		}
	}

	@Override
	public long getTimeout() {
		return coapServerContext.getTimeout();
	}

	private CoapServer createCoapServer() throws UnknownHostException {
		Configuration networkConfig = new Configuration();
		networkConfig.set(CoapConfig.BLOCKWISE_STRICT_BLOCK2_OPTION, true);
		networkConfig.set(CoapConfig.BLOCKWISE_ENTITY_TOO_LARGE_AUTO_FAILOVER, true);
		networkConfig.set(CoapConfig.BLOCKWISE_STATUS_LIFETIME, DEFAULT_BLOCKWISE_STATUS_LIFETIME_IN_SECONDS, TimeUnit.SECONDS);
		networkConfig.set(CoapConfig.MAX_RESOURCE_BODY_SIZE, 256 * 1024 * 1024);
		networkConfig.set(CoapConfig.RESPONSE_MATCHING, CoapConfig.MatcherMode.RELAXED);
		networkConfig.set(CoapConfig.PREFERRED_BLOCK_SIZE, 1024);
		networkConfig.set(CoapConfig.MAX_MESSAGE_SIZE, 1024);
		networkConfig.set(CoapConfig.MAX_RETRANSMIT, 4);
		networkConfig.set(CoapConfig.COAP_PORT, coapServerContext.getPort());
		server = new CoapServer(networkConfig);

		CoapEndpoint.Builder noSecCoapEndpointBuilder = new CoapEndpoint.Builder();
		InetAddress addr = InetAddress.getByName(coapServerContext.getHost());
		InetSocketAddress sockAddr = new InetSocketAddress(addr, coapServerContext.getPort());
		noSecCoapEndpointBuilder.setInetSocketAddress(sockAddr);

		noSecCoapEndpointBuilder.setConfiguration(networkConfig);
		CoapEndpoint noSecCoapEndpoint = noSecCoapEndpointBuilder.build();
		server.addEndpoint(noSecCoapEndpoint);
		Resource root = server.getRoot();
		GaCoapServerMessageDeliverer messageDeliverer = new GaCoapServerMessageDeliverer(root);
		server.setMessageDeliverer(messageDeliverer);

		server.start();
		return server;
	}
}
