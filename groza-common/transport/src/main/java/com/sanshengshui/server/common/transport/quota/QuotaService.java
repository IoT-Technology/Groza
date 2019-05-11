package com.sanshengshui.server.common.transport.quota;

/**
 * @author james mu
 * @date 19-1-23 下午3:30
 */
public interface QuotaService {

    boolean isQuotaExceeded(String key);
}
