package com.sanshengshui.server.dao.device;

import com.sanshengshui.server.common.data.id.DeviceId;
import com.sanshengshui.server.common.data.security.DeviceCredentials;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author james mu
 * @date 18-12-25 上午9:21
 */
@Slf4j
@Service
public class DeviceCredentialsServiceImpl implements DeviceCredentialsService{

    private DeviceCredentialsDao deviceCredentialsDao;


    @Override
    public DeviceCredentials findDeviceCredentialsByDeviceId(DeviceId deviceId) {
        return null;
    }

    @Override
    public DeviceCredentials findDeviceCredentialsByCredentialsId(String credentialsId) {
        return null;
    }

    @Override
    public DeviceCredentials updateDeviceCredentials(DeviceCredentials deviceCredentials) {
        return null;
    }

    @Override
    public DeviceCredentials createDeviceCredentials(DeviceCredentials deviceCredentials) {
        return null;
    }

    @Override
    public void deleteDeviceCredentials(DeviceCredentials deviceCredentials) {

    }
}
