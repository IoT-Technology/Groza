package com.sanshengshui.server.dao.device;

import com.sanshengshui.server.common.data.security.DeviceCredentials;
import com.sanshengshui.server.dao.Dao;

import java.util.UUID;

/**
 * @author james mu
 * @date 18-12-24 下午4:55
 */
public interface DeviceCredentialsDao extends Dao<DeviceCredentials> {
    /**
     * Save or update device credentials object.
     *
     * @param deviceCredentials the device credentials object
     * @return saved device credentials object
     */
    DeviceCredentials save(DeviceCredentials deviceCredentials);

    /**
     * Find device credentials by device id.
     *
     * @param deviceId the device id
     * @return the device credentials object
     */
    DeviceCredentials findByDeviceId(UUID deviceId);

    /**
     * Find device credentials by credentials id.
     *
     * @param credentialsId the credentials id
     * @return the device credentials object
     */
    DeviceCredentials findByCredentialsId(String credentialsId);
}
