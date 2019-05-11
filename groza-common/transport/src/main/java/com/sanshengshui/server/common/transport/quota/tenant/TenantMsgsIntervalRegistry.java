package com.sanshengshui.server.common.transport.quota.tenant;

import com.sanshengshui.server.common.transport.quota.inmemory.KeyBasedIntervalRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author james mu
 * @date 19-1-23 下午4:30
 */
@Component
public class TenantMsgsIntervalRegistry extends KeyBasedIntervalRegistry {

    public TenantMsgsIntervalRegistry(@Value("${quota.rule.tenant.intervalMs}") long intervalDurationMs,
                                      @Value("${quota.rule.tenant.ttlMs}") long ttlMs,
                                      @Value("${quota.rule.tenant.whitelist}") String whiteList,
                                      @Value("${quota.rule.tenant.blacklist}") String blackList){
        super(intervalDurationMs,ttlMs,whiteList,blackList,"Rule Tenant");
    }
}
