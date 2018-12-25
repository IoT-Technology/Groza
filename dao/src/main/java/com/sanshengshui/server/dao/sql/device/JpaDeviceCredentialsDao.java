package com.sanshengshui.server.dao.sql.device;

import com.sanshengshui.server.common.data.UUIDConverter;
import com.sanshengshui.server.common.data.security.DeviceCredentials;
import com.sanshengshui.server.dao.DaoUtil;
import com.sanshengshui.server.dao.device.DeviceCredentialsDao;
import com.sanshengshui.server.dao.model.sql.DeviceCredentialsEntity;
import com.sanshengshui.server.dao.sql.JpaAbstractDao;
import com.sanshengshui.server.dao.util.SqlDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author james mu
 * @date 18-12-24 下午4:50
 */
@SqlDao
@Component
public class JpaDeviceCredentialsDao extends JpaAbstractDao<DeviceCredentialsEntity, DeviceCredentials> implements DeviceCredentialsDao {

    @Autowired
    private DeviceCredentialsRepository deviceCredentialsRepository;

    @Override
    public DeviceCredentials findByDeviceId(UUID deviceId) {
        return DaoUtil.getData(deviceCredentialsRepository.findByDeviceId(UUIDConverter.fromTimeUUID(deviceId)));
    }

    @Override
    public DeviceCredentials findByCredentialsId(String credentialsId) {
        return DaoUtil.getData(deviceCredentialsRepository.findByCredentialsId(credentialsId));
    }

    @Override
    protected Class<DeviceCredentialsEntity> getEntityClass() {
        return DeviceCredentialsEntity.class;
    }

    @Override
    protected CrudRepository<DeviceCredentialsEntity, String> getCrudRepository() {
        return deviceCredentialsRepository;
    }
}
