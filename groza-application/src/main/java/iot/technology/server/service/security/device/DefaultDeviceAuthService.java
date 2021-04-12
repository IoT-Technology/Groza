package iot.technology.server.service.security.device;

import iot.technology.server.common.data.Device;
import iot.technology.server.common.data.id.DeviceId;
import iot.technology.server.common.data.security.DeviceCredentials;
import iot.technology.server.common.data.security.DeviceCredentialsFilter;
import iot.technology.server.common.transport.auth.DeviceAuthResult;
import iot.technology.server.common.transport.auth.DeviceAuthService;
import iot.technology.server.dao.device.DeviceCredentialsService;
import iot.technology.server.dao.device.DeviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author james mu
 * @date 18-12-21 上午11:07
 */
@Slf4j
@Service
public class DefaultDeviceAuthService implements DeviceAuthService {

    @Autowired
    DeviceService deviceService;

    @Autowired
    DeviceCredentialsService deviceCredentialsService;

    @Override
    public DeviceAuthResult process(DeviceCredentialsFilter credentialsFilter) {
        log.trace("Lookup device credentials using filter {}", credentialsFilter);
        DeviceCredentials credentials = deviceCredentialsService.findDeviceCredentialsByCredentialsId(credentialsFilter.getCredentialsId());
        if (credentials != null) {
            log.trace("Credentials found {}", credentials);
            if (credentials.getCredentialsType() == credentialsFilter.getCredentialsType()) {
                switch (credentials.getCredentialsType()) {
                    case ACCESS_TOKEN:
                        // Credentials ID matches Credentials value in this
                        // primitive case;
                        return DeviceAuthResult.of(credentials.getDeviceId());
                    case X509_CERTIFICATE:
                        return DeviceAuthResult.of(credentials.getDeviceId());
                    default:
                        return DeviceAuthResult.of("Credentials Type is not supported yet!");
                }
            } else {
                return DeviceAuthResult.of("Credentials Type mismatch!");
            }
        } else {
            log.trace("Credentials not found!");
            return DeviceAuthResult.of("Credentials Not Found!");
        }
    }

    @Override
    public Optional<Device> findDeviceById(DeviceId deviceId) {
        return Optional.ofNullable(deviceService.findDeviceById(deviceId));
    }
}
