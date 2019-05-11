package com.sanshengshui.server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author james mu
 * @date 19-3-20 下午2:54
 * @description
 */
@Configuration
@ConfigurationProperties(prefix = "audit-log.logging-level")
public class AuditLogLevelProperties {

    private Map<String, String> mask = new HashMap<>();

    public AuditLogLevelProperties() {
        super();
    }

    public void setMask(Map<String, String> mask) {
        this.mask = mask;
    }

    public Map<String, String> getMask() {
        return this.mask;
    }
}
