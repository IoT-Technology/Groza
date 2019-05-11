package com.sanshengshui.server.transport.coap.session;

import org.eclipse.californium.core.network.Exchange;
import org.eclipse.californium.core.network.ExchangeObserver;

public class CoapExchangeObserverProxy implements ExchangeObserver {

    private final ExchangeObserver proxy;
    private final CoapSessionCtx ctx;

    public CoapExchangeObserverProxy(ExchangeObserver proxy, CoapSessionCtx ctx) {
        super();
        this.proxy = proxy;
        this.ctx = ctx;
    }

    @Override
    public void completed(Exchange exchange) {
        proxy.completed(exchange);
        ctx.close();
    }
}
