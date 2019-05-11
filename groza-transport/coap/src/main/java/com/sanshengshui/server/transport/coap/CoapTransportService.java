package com.sanshengshui.server.transport.coap;

import com.sanshengshui.server.common.transport.SessionMsgProcessor;
import com.sanshengshui.server.common.transport.auth.DeviceAuthService;
import com.sanshengshui.server.common.transport.quota.host.HostRequestsQuotaService;
import com.sanshengshui.server.transport.coap.adaptors.CoapTransportAdaptor;
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

    @Autowired(required = false)
    private ApplicationContext appContext;

    @Autowired(required = false)
    private SessionMsgProcessor processor;

    @Autowired(required = false)
    private DeviceAuthService authService;

    @Autowired(required = false)
    private HostRequestsQuotaService quotaService;

    @Value("${coap.bind_address}")
    private String host;
    @Value("${coap.bind_port}")
    private Integer port;
    @Value("${coap.adaptor}")
    private String adaptorName;
    @Value("${coap.timeout}")
    private Long timeout;

    private CoapTransportAdaptor adaptor;

    @PostConstruct
    public void init() throws UnknownHostException {
        log.info("Starting CoAP transport...");
        log.info("Lookup CoAP transport adaptor {}", adaptorName);
        this.adaptor = (CoapTransportAdaptor) appContext.getBean(adaptorName);
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
        api.add(new CoapTransportResource(processor,authService,adaptor,V1,timeout, quotaService));
        server.add(api);
    }

    @PreDestroy
    public void shutdown() {
        log.info("Stopping CoAP transport!");
        this.server.destroy();
        log.info("CoAP transport stopped!");
    }


}
