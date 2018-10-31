package com.sanshengshui.server.transport.coap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

@Service("CoapTransportService")
@ConfigurationProperties(prefix = "coap")
@Slf4j
public class CoapTransportService {
}
