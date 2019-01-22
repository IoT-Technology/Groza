package com.sanshengshui.server.dao.device;

import com.google.common.util.concurrent.ListenableFuture;
import com.sanshengshui.server.common.data.Device;
import com.sanshengshui.server.common.data.EntitySubtype;
import com.sanshengshui.server.common.data.device.DeviceSearchQuery;
import com.sanshengshui.server.common.data.id.CustomerId;
import com.sanshengshui.server.common.data.id.DeviceId;
import com.sanshengshui.server.common.data.id.TenantId;
import com.sanshengshui.server.common.data.page.TextPageData;
import com.sanshengshui.server.common.data.page.TextPageLink;

import java.util.List;

/**
 * @author james mu
 * @date 18-12-25 上午9:28
 */
public interface DeviceService {

    Device findDeviceById(DeviceId deviceId);

    ListenableFuture<Device> findDeviceByIdAsync(DeviceId deviceId);

    Device findDeviceByTenantIdAndName(TenantId tenantId, String name);

    Device saveDevice(Device device);

    Device assignDeviceToCustomer(DeviceId deviceId, CustomerId customerId);

    Device unassignDeviceFromCustomer(DeviceId deviceId);

    void deleteDevice(DeviceId deviceId);

    TextPageData<Device> findDevicesByTenantId(TenantId tenantId, TextPageLink pageLink);

    TextPageData<Device> findDevicesByTenantIdAndType(TenantId tenantId, String type, TextPageLink pageLink);

    ListenableFuture<List<Device>> findDevicesByTenantIdAndIdsAsync(TenantId tenantId, List<DeviceId> deviceIds);

    void deleteDevicesByTenantId(TenantId tenantId);

    TextPageData<Device> findDevicesByTenantIdAndCustomerId(TenantId tenantId, CustomerId customerId, TextPageLink pageLink);

    TextPageData<Device> findDevicesByTenantIdAndCustomerIdAndType(TenantId tenantId, CustomerId customerId, String type, TextPageLink pageLink);

    ListenableFuture<List<Device>> findDevicesByTenantIdCustomerIdAndIdsAsync(TenantId tenantId, CustomerId customerId, List<DeviceId> deviceIds);

    void unassignCustomerDevices(TenantId tenantId, CustomerId customerId);

    ListenableFuture<List<Device>> findDevicesByQuery(DeviceSearchQuery query);

    ListenableFuture<List<EntitySubtype>> findDeviceTypesByTenantId(TenantId tenantId);
}
