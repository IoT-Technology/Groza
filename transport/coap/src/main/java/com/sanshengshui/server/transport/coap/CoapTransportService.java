package com.sanshengshui.server.transport.coap;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.network.CoapEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

@Service("CoapTransportService")
@ConditionalOnProperty(prefix = "coap",value = "enabled",havingValue = "true",matchIfMissing = true)
@Slf4j
public class CoapTransportService {
    private static final String V1 = "v1";
    private static final String API = "api";

    private CoapServer server;


    @Value("${coap.bind_address}")
    private String host;
    @Value("${coap.bind_port}")
    private Integer port;
    @Value("${coap.adaptor}")
    private String adaptorName;
    @Value("${coap.timeout}")
    private Long timeout;


    @PostConstruct
    public void init() throws UnknownHostException {
        log.info("Starting CoAP transport...");
        log.info("Lookup CoAP transport adaptor {}", adaptorName);
        log.info("Starting CoAP transport server");
        this.server = new CoapServer();
        createResources();
        InetAddress addr = InetAddress.getByName(host);
        InetSocketAddress sockAddr = new InetSocketAddress(addr, port);
        server.addEndpoint(new CoapEndpoint(sockAddr));
        server.start();
        log.info("CoAP transport started!");
    }

    private void createResources() {
        CoapResource api = new CoapResource(API);
        api.add(new CoapTransportResource(V1,timeout));
        server.add(api);
    }

    @PreDestroy
    public void shutdown() {
        log.info("Stopping CoAP transport!");
        this.server.destroy();
        log.info("CoAP transport stopped!");
    }


}
