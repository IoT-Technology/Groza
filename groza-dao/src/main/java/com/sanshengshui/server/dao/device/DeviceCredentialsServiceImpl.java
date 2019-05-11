package com.sanshengshui.server.dao.device;

import com.sanshengshui.server.common.data.id.DeviceId;
import com.sanshengshui.server.common.data.security.DeviceCredentials;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.sanshengshui.server.dao.service.Validator.validateId;
import static com.sanshengshui.server.dao.service.Validator.validateString;

/**
 * @author james mu
 * @date 18-12-25 上午9:21
 */
@Slf4j
@Service
public class DeviceCredentialsServiceImpl implements DeviceCredentialsService{

    @Autowired
    private DeviceCredentialsDao deviceCredentialsDao;

    @Autowired
    private DeviceService deviceService;

    @Override
    public DeviceCredentials findDeviceCredentialsByDeviceId(DeviceId deviceId) {
        log.trace("Executing findDeviceCredentialsByDeviceID [{}]", deviceId);
        validateId(deviceId, "Incorrect deviceId " + deviceId);
        return deviceCredentialsDao.findByDeviceId(deviceId.getId());
    }

    @Override
    public DeviceCredentials findDeviceCredentialsByCredentialsId(String credentialsId) {
        log.trace("Executing findDeviceCredentialsByCredentialsId [{}]", credentialsId);
        validateString(credentialsId, "Incorrect credentialsId " + credentialsId);
        return deviceCredentialsDao.findByCredentialsId(credentialsId);
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
