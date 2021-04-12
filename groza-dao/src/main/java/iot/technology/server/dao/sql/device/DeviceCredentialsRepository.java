package iot.technology.server.dao.sql.device;

import iot.technology.server.dao.model.sql.DeviceCredentialsEntity;
import iot.technology.server.dao.util.SqlDao;
import org.springframework.data.repository.CrudRepository;

/**
 * @author james mu
 * @date 18-12-24 下午5:24
 */
@SqlDao
public interface DeviceCredentialsRepository extends CrudRepository<DeviceCredentialsEntity,String> {

    DeviceCredentialsEntity findByDeviceId(String deviceId);

    DeviceCredentialsEntity findByCredentialsId(String credentialsId);
}
