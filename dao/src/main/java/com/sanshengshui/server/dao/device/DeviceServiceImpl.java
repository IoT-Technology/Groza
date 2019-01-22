package com.sanshengshui.server.dao.device;

import com.google.common.util.concurrent.ListenableFuture;
import com.sanshengshui.server.common.data.Customer;
import com.sanshengshui.server.common.data.Device;
import com.sanshengshui.server.common.data.EntitySubtype;
import com.sanshengshui.server.common.data.Tenant;
import com.sanshengshui.server.common.data.device.DeviceSearchQuery;
import com.sanshengshui.server.common.data.id.CustomerId;
import com.sanshengshui.server.common.data.id.DeviceId;
import com.sanshengshui.server.common.data.id.TenantId;
import com.sanshengshui.server.common.data.page.TextPageData;
import com.sanshengshui.server.common.data.page.TextPageLink;
import com.sanshengshui.server.common.data.security.DeviceCredentials;
import com.sanshengshui.server.common.data.security.DeviceCredentialsType;
import com.sanshengshui.server.dao.customer.CustomerDao;
import com.sanshengshui.server.dao.exception.DataValidationException;
import com.sanshengshui.server.dao.service.DataValidator;
import com.sanshengshui.server.dao.tenant.TenantDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

import static com.sanshengshui.server.dao.model.ModelConstants.NULL_UUID;
import static com.sanshengshui.server.dao.service.Validator.validateId;

/**
 * @author james mu
 * @date 18-12-25 上午9:32
 */
@Service
@Slf4j
public class DeviceServiceImpl implements DeviceService{

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
    public TextPageData<Device> findDevicesByTenantId(TenantId tenantId, TextPageLink pageLink) {
        return null;
    }

    @Override
    public TextPageData<Device> findDevicesByTenantIdAndType(TenantId tenantId, String type, TextPageLink pageLink) {
        return null;
    }

    @Override
    public ListenableFuture<List<Device>> findDevicesByTenantIdAndIdsAsync(TenantId tenantId, List<DeviceId> deviceIds) {
        return null;
    }

    @Override
    public void deleteDevicesByTenantId(TenantId tenantId) {

    }

    @Override
    public TextPageData<Device> findDevicesByTenantIdAndCustomerId(TenantId tenantId, CustomerId customerId, TextPageLink pageLink) {
        return null;
    }

    @Override
    public TextPageData<Device> findDevicesByTenantIdAndCustomerIdAndType(TenantId tenantId, CustomerId customerId, String type, TextPageLink pageLink) {
        return null;
    }

    @Override
    public ListenableFuture<List<Device>> findDevicesByTenantIdCustomerIdAndIdsAsync(TenantId tenantId, CustomerId customerId, List<DeviceId> deviceIds) {
        return null;
    }

    @Override
    public void unassignCustomerDevices(TenantId tenantId, CustomerId customerId) {

    }

    @Override
    public ListenableFuture<List<Device>> findDevicesByQuery(DeviceSearchQuery query) {
        return null;
    }

    @Override
    public ListenableFuture<List<EntitySubtype>> findDeviceTypesByTenantId(TenantId tenantId) {
        return null;
    }
}
