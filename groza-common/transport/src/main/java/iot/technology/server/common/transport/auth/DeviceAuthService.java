package iot.technology.server.common.transport.auth;

import iot.technology.server.common.data.Device;
import iot.technology.server.common.data.id.DeviceId;
import iot.technology.server.common.data.security.DeviceCredentialsFilter;

import java.util.Optional;

/**
 * @author james mu
 * @date 18-12-21 上午10:50
 */
public interface DeviceAuthService {

    DeviceAuthResult process(DeviceCredentialsFilter credentials);

    Optional<Device> findDeviceById(DeviceId deviceId);
}
