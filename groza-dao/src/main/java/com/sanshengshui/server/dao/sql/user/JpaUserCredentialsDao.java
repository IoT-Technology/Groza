package com.sanshengshui.server.dao.sql.user;

import com.sanshengshui.server.common.data.UUIDConverter;
import com.sanshengshui.server.common.data.security.UserCredentials;
import com.sanshengshui.server.dao.DaoUtil;
import com.sanshengshui.server.dao.model.sql.UserCredentialsEntity;
import com.sanshengshui.server.dao.sql.JpaAbstractDao;
import com.sanshengshui.server.dao.user.UserCredentialsDao;
import com.sanshengshui.server.dao.util.SqlDao;
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
