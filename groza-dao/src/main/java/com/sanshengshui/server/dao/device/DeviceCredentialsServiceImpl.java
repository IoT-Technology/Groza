package com.sanshengshui.server.dao.device;

import com.sanshengshui.server.common.data.Device;
import com.sanshengshui.server.common.data.id.DeviceId;
import com.sanshengshui.server.common.data.security.DeviceCredentials;
import com.sanshengshui.server.common.data.security.DeviceCredentialsType;
import com.sanshengshui.server.dao.EncryptionUtil;
import com.sanshengshui.server.dao.exception.DataValidationException;
import com.sanshengshui.server.dao.service.DataValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import static com.sanshengshui.server.common.data.CacheConstants.DEVICE_CREDENTIALS_CACHE;
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
    @Cacheable(cacheNames = DEVICE_CREDENTIALS_CACHE, unless="#result == null")
    public DeviceCredentials findDeviceCredentialsByCredentialsId(String credentialsId) {
        log.trace("Executing findDeviceCredentialsByCredentialsId [{}]", credentialsId);
        validateString(credentialsId, "Incorrect credentialsId " + credentialsId);
        return deviceCredentialsDao.findByCredentialsId(credentialsId);
    }

    @Override
    @CacheEvict(cacheNames = DEVICE_CREDENTIALS_CACHE, keyGenerator = "previousDeviceCredentialsId", beforeInvocation = true)
    public DeviceCredentials updateDeviceCredentials(DeviceCredentials deviceCredentials) {
        return saveOrUpdare(deviceCredentials);
    }

    @Override
    public DeviceCredentials createDeviceCredentials(DeviceCredentials deviceCredentials) {
        return saveOrUpdare(deviceCredentials);
    }

    private DeviceCredentials saveOrUpdare(DeviceCredentials deviceCredentials) {
        if (deviceCredentials.getCredentialsType() == DeviceCredentialsType.X509_CERTIFICATE) {
            formatCertData(deviceCredentials);
        }
        log.trace("Executing updateDeviceCredentials [{}]", deviceCredentials);
        credentialsValidator.validate(deviceCredentials);
        return deviceCredentialsDao.save(deviceCredentials);
    }

    private void formatCertData(DeviceCredentials deviceCredentials) {
        String cert = EncryptionUtil.trimNewLines(deviceCredentials.getCredentialsValue());
        String sha3Hash = EncryptionUtil.getSha3Hash(cert);
        deviceCredentials.setCredentialsId(sha3Hash);
        deviceCredentials.setCredentialsValue(cert);
    }

    @Override
    @CacheEvict(cacheNames = DEVICE_CREDENTIALS_CACHE, key = "#deviceCredentials.credentialsId")
    public void deleteDeviceCredentials(DeviceCredentials deviceCredentials) {
        log.trace("Executing deleteDeviceCredentials [{}]", deviceCredentials);
        deviceCredentialsDao.removeById(deviceCredentials.getUuidId());
    }

    private DataValidator<DeviceCredentials> credentialsValidator =
            new DataValidator<DeviceCredentials>() {

                @Override
                protected void validateDataImpl(DeviceCredentials deviceCredentials) {
                    if (deviceCredentials.getDeviceId() == null) {
                        throw new DataValidationException("Device credentials should be assigned to device!");
                    }
                    if (deviceCredentials.getCredentialsType() == null) {
                        throw new DataValidationException("Device credentials type should be specified!");
                    }
                    if (StringUtils.isEmpty(deviceCredentials.getCredentialsId())) {
                        throw new DataValidationException("Device credentials id should be specified!");
                    }
                    Device device = deviceService.findDeviceById(deviceCredentials.getDeviceId());
                    if (device == null) {
                        throw new DataValidationException("Can't assign device credentials to non-existent device!");
                    }
                }

                @Override
                protected void validateCreate(DeviceCredentials deviceCredentials) {
                    DeviceCredentials existingCredentialsEntity = deviceCredentialsDao.findByCredentialsId(deviceCredentials.getCredentialsId());
                    if (existingCredentialsEntity != null) {
                        throw new DataValidationException("Create of existent device credentials!");
                    }
                }

                @Override
                protected void validateUpdate(DeviceCredentials deviceCredentials) {
                    DeviceCredentials existingCredentialsEntity = deviceCredentialsDao.findById(deviceCredentials.getUuidId());
                    if (existingCredentialsEntity == null) {
                        throw new DataValidationException("Unable to update non-existent device credentials!");
                    }
                    DeviceCredentials sameCredentialsId = deviceCredentialsDao.findByCredentialsId(deviceCredentials.getCredentialsId());
                    if (sameCredentialsId != null && !sameCredentialsId.getUuidId().equals(deviceCredentials.getUuidId())) {
                        throw new DataValidationException("Specified credentials are already registered!");
                    }
                }

            };
}
