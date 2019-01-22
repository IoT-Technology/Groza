package com.sanshengshui.server.dao.device;

import com.google.common.util.concurrent.ListenableFuture;
import com.sanshengshui.server.common.data.Device;
import com.sanshengshui.server.common.data.EntitySubtype;
import com.sanshengshui.server.common.data.page.TextPageLink;
import com.sanshengshui.server.dao.Dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author james mu
 * @date 18-12-11 上午10:03
 */
public interface DeviceDao extends Dao<Device> {
    /**
     * Save or update device object
     *
     * @param device the device object
     * @return saved device object
     */
    Device save(Device device);

    /**
     * Find devices by tenantId
     *
     * @param tenantId the tenantId
     * @return the list of device objects
     */
    List<Device> findDevicesByTenantId(UUID tenantId, TextPageLink pageLink);

    /**
     * find devices by tenantId and type
     * @param tenantId the tenantId
     * @param type the type
     * @return the list of device objects
     */
    List<Device> findDevicesByTenantIdAndType(UUID tenantId, String type, TextPageLink pageLink);

    /**
     * Find devices by tenantId and devices Ids.
     *
     * @param tenantId the tenantId
     * @param deviceIds the device Ids
     * @return the list of device objects
     */
    ListenableFuture<List<Device>> findDevicesByTenantIdAndIdsAsync(UUID tenantId, List<UUID> deviceIds);

    /**
     * Find devices by tenantId, customerId and page link.
     *
     * @param tenantId the tenantId
     * @param customerId the customerId
     * @return the list of device objects
     */
    List<Device> findDevicesByTenantIdAndCustomerId(UUID tenantId, UUID customerId, TextPageLink pageLink);

    /**
     * Find devices by tenantId, customerId and type
     *
     * @param tenantId the tenantId
     * @param customerId the customerId
     * @param type the type
     * @return the list of device objects
     */
    List<Device> findDevicesByTenantIdAndCustomerIdAndType(UUID tenantId, UUID customerId, String type, TextPageLink pageLink);


    /**
     * Find devices by tenantId, customerId and devices Ids.
     *
     * @param tenantId the tenantId
     * @param customerId the customerId
     * @param deviceIds the device Ids
     * @return the list of device objects
     */
    ListenableFuture<List<Device>> findDevicesByTenantIdCustomerIdAndIdsAsync(UUID tenantId, UUID customerId, List<UUID> deviceIds);

    /**
     * Find devices by tenantId and device name.
     *
     * @param tenantId the tenantId
     * @param name the device name
     * @return the optional device object
     */
    Optional<Device> findDeviceByTenantIdAndName(UUID tenantId, String name);

    /**
     * Find tenants device types.
     *
     * @return the list of tenant device type objects
     */
    ListenableFuture<List<EntitySubtype>> findTenantDeviceTypesAsync(UUID tenantId);
}
