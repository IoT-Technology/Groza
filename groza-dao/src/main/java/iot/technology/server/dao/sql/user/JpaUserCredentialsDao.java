package iot.technology.server.dao.sql.user;

import iot.technology.server.common.data.UUIDConverter;
import iot.technology.server.common.data.security.UserCredentials;
import iot.technology.server.dao.DaoUtil;
import iot.technology.server.dao.model.sql.UserCredentialsEntity;
import iot.technology.server.dao.sql.JpaAbstractDao;
import iot.technology.server.dao.user.UserCredentialsDao;
import iot.technology.server.dao.util.SqlDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author james mu
 * @date 19-2-20 下午3:57
 * @description
 */
@Component
@SqlDao
public class JpaUserCredentialsDao extends JpaAbstractDao<UserCredentialsEntity, UserCredentials> implements UserCredentialsDao {

    @Autowired
    private UserCredentialsRepository userCredentialsRepository;

    @Override
    protected Class<UserCredentialsEntity> getEntityClass() {
        return UserCredentialsEntity.class;
    }

    @Override
    protected CrudRepository<UserCredentialsEntity, String> getCrudRepository() {
        return userCredentialsRepository;
    }

    @Override
    public UserCredentials findByUserId(UUID userId) {
        return DaoUtil.getData(userCredentialsRepository.findByUserId(UUIDConverter.fromTimeUUID(userId)));
    }

    @Override
    public UserCredentials findByActivateToken(String activateToken) {
        return DaoUtil.getData(userCredentialsRepository.findByActivateToken(activateToken));
    }

    @Override
    public UserCredentials findByResetToken(String resetToken) {
        return DaoUtil.getData(userCredentialsRepository.findByResetToken(resetToken));
    }
}
