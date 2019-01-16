package com.sanshengshui.server.transport.coap;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.server.resources.CoapExchange;

@Slf4j
public class CoapTransportResource extends CoapResource {

    private final long timeout;

    public CoapTransportResource(String name,long timeout){
        super(name);
        this.timeout = timeout;
    }
    @Override
    public void handleGET(CoapExchange exchange) {
        super.handleGET(exchange);
    }


}
