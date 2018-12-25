package com.sanshengshui.server.dao.device;

import com.google.common.util.concurrent.ListenableFuture;
import com.sanshengshui.server.common.data.Device;
import com.sanshengshui.server.common.data.EntitySubtype;
import com.sanshengshui.server.common.data.id.CustomerId;
import com.sanshengshui.server.common.data.id.DeviceId;
import com.sanshengshui.server.common.data.id.TenantId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author james mu
 * @date 18-12-25 上午9:32
 */
@Service
@Slf4j
public class DeviceServiceImpl implements DeviceService{

    public static final String INCORRECT_TENANT_ID = "Incorrect tenantId ";
    public static final String INCORRECT_PAGE_LINK = "Incorrect page link ";
    public static final String INCORRECT_CUSTOMER_ID = "Incorrect customerId ";
    public static final String INCORRECT_DEVICE_ID = "Incorrect deviceId ";

    @Override
    public Device findDeviceById(DeviceId deviceId) {
        return null;
    }

    @Override
    public ListenableFuture<Device> findDeviceByIdAsync(DeviceId deviceId) {
        return null;
    }

    @Override
    public Device findDeviceByTenantIdAndName(TenantId tenantId, String name) {
        return null;
    }

    @Override
    public Device saveDevice(Device device) {
        return null;
    }

    @Override
    public Device assignDeviceToCustomer(DeviceId deviceId, CustomerId customerId) {
        return null;
    }

    @Override
    public Device unassignDeviceFromCustomer(DeviceId deviceId) {
        return null;
    }

    @Override
    public void deleteDevice(DeviceId deviceId) {

    }

    @Override
    public ListenableFuture<List<Device>> findDevicesByTenantIdAndIdsAsync(TenantId tenantId, List<DeviceId> deviceIds) {
        return null;
    }

    @Override
    public void deleteDevicesByTenantId(TenantId tenantId) {

    }

    @Override
    public ListenableFuture<List<Device>> findDevicesByTenantIdCustomerIdAndIdsAsync(TenantId tenantId, CustomerId customerId, List<DeviceId> deviceIds) {
        return null;
    }

    @Override
    public void unassignCustomerDevices(TenantId tenantId, CustomerId customerId) {

    }

    @Override
    public ListenableFuture<List<EntitySubtype>> findDeviceTypesByTenantId(TenantId tenantId) {
        return null;
    }
}
