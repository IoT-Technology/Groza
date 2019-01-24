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

    public static final String INCORRECT_TENANT_ID = "Incorrect tenantId ";
    public static final String INCORRECT_PAGE_LINK = "Incorrect page link ";
    public static final String INCORRECT_CUSTOMER_ID = "Incorrect customerId ";
    public static final String INCORRECT_DEVICE_ID = "Incorrect deviceId ";

    @Autowired
    private DeviceDao deviceDao;

    @Autowired
    private TenantDao tenantDao;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private DeviceCredentialsService deviceCredentialsService;



    @Override
    public Device findDeviceById(DeviceId deviceId) {
        log.trace("Executing findDeviceById [{}]", deviceId);
        validateId(deviceId , INCORRECT_DEVICE_ID + deviceId);
        return deviceDao.findById(deviceId.getId());
    }

    @Override
    public ListenableFuture<Device> findDeviceByIdAsync(DeviceId deviceId) {
        log.trace("Executing findDeviceById", deviceId);
        validateId(deviceId, INCORRECT_DEVICE_ID + deviceId);
        return deviceDao.findByIdAsync(deviceId.getId());
    }

    @Override
    public Device findDeviceByTenantIdAndName(TenantId tenantId, String name) {
        log.trace("Executing findDeviceByTenantIdAndName [{}][{}]", tenantId, name);
        validateId(tenantId, INCORRECT_TENANT_ID + tenantId);
        Optional<Device> deviceOpt = deviceDao.findDeviceByTenantIdAndName(tenantId.getId(), name);
        return deviceOpt.orElse(null);
    }

    @Override
    public Device saveDevice(Device device) {
        log.trace("Executing saveDevice [{}]", device);
        deviceValidator.validate(device);
        Device savedDevice = deviceDao.save(device);
        if (device.getId() == null){
            DeviceCredentials deviceCredentials = new DeviceCredentials();
            deviceCredentials.setDeviceId(new DeviceId(savedDevice.getUuidId()));
            deviceCredentials.setCredentialsType(DeviceCredentialsType.ACCESS_TOKEN);
            deviceCredentials.setCredentialsId(RandomStringUtils.randomAlphanumeric(20));
            deviceCredentialsService.createDeviceCredentials(deviceCredentials);
        }
        return savedDevice;
    }

    @Override
    public Device assignDeviceToCustomer(DeviceId deviceId, CustomerId customerId) {
        Device device = findDeviceById(deviceId);
        device.setCustomerId(null);
        return saveDevice(device);
    }

    @Override
    public Device unassignDeviceFromCustomer(DeviceId deviceId) {
        Device device = findDeviceById(deviceId);
        device.setCustomerId(null);
        return saveDevice(device);
    }

    @Override
    public void deleteDevice(DeviceId deviceId) {
        log.trace("Executing deleteDevice [{}]", deviceId);
        validateId(deviceId, INCORRECT_DEVICE_ID + deviceId);

        Device device = deviceDao.findById(deviceId.getId());

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

    private DataValidator<Device> deviceValidator =

            new DataValidator<Device>() {

                @Override
                protected void validateDataImpl(Device device) {
                    if (StringUtils.isEmpty(device.getType())) {
                        throw new DataValidationException("Device type should be specified!");
                    }
                    if (StringUtils.isEmpty(device.getName())) {
                        throw new DataValidationException("Device name should be specified!");
                    }
                    if (device.getTenantId() == null) {
                        throw new DataValidationException("Device should be assigned to tenant!");
                    } else {
                        Tenant tenant = tenantDao.findById(device.getTenantId().getId());
                        if (tenant == null) {
                            throw new DataValidationException("Device is referencing to non-existent tenant!");
                        }
                    }
                    if (device.getCustomerId() == null) {
                        device.setCustomerId(new CustomerId(NULL_UUID));
                    } else if (!device.getCustomerId().getId().equals(NULL_UUID)) {
                        Customer customer = customerDao.findById(device.getCustomerId().getId());
                        if (customer == null) {
                            throw new DataValidationException("Can't assign device to non-existent customer!");
                        }
                        if (!customer.getTenantId().getId().equals(device.getTenantId().getId())) {
                            throw new DataValidationException("Can't assign device to customer from different tenant!");
                        }
                    }
                }

                @Override
                protected void validateCreate(Device device) {
                    deviceDao.findDeviceByTenantIdAndName(device.getTenantId().getId(), device.getName()).ifPresent(
                            d -> {
                                throw new DataValidationException("Device with such name already exists!");
                            }
                    );
                }

                @Override
                protected void validateUpdate(Device device) {
                    deviceDao.findDeviceByTenantIdAndName(device.getTenantId().getId(), device.getName()).ifPresent(
                            d -> {
                                if (!d.getUuidId().equals(device.getUuidId())) {
                                    throw new DataValidationException("Device with such name already exists!");
                                }
                            }
                    );
                }

            };
}
