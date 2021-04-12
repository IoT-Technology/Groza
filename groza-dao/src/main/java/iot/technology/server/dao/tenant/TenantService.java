package iot.technology.server.dao.tenant;

import com.google.common.util.concurrent.ListenableFuture;
import iot.technology.server.common.data.Tenant;
import iot.technology.server.common.data.id.TenantId;
import iot.technology.server.common.data.page.TextPageData;
import iot.technology.server.common.data.page.TextPageLink;

/**
 * @author james mu
 * @date 19-1-3 下午3:42
 */
public interface TenantService {

    Tenant findTenantById(TenantId tenantId);

    ListenableFuture<Tenant> findTenantByIdAsync(TenantId customerId);

    Tenant saveTenant(Tenant tenant);

    void deleteTenant(TenantId tenantId);

    TextPageData<Tenant> findTenants(TextPageLink pageLink);

    void deleteTenants();
}
