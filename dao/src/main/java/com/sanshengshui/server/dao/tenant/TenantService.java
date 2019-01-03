package com.sanshengshui.server.dao.tenant;

import com.google.common.util.concurrent.ListenableFuture;
import com.sanshengshui.server.common.data.Tenant;
import com.sanshengshui.server.common.data.id.TenantId;

/**
 * @author james mu
 * @date 19-1-3 下午3:42
 */
public interface TenantService {

    Tenant findTenantById(TenantId tenantId);

    ListenableFuture<Tenant> findTenantByIdAsync(TenantId customerId);
}
