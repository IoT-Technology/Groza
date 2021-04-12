package iot.technology.server.dao.sql.user;

import iot.technology.server.dao.model.sql.UserCredentialsEntity;
import iot.technology.server.dao.util.SqlDao;
import org.springframework.data.repository.CrudRepository;

/**
 * @author james mu
 * @date 19-2-20 下午2:54
 * @description
 */
@SqlDao
public interface UserCredentialsRepository extends CrudRepository<UserCredentialsEntity, String> {

    UserCredentialsEntity findByUserId(String userId);

    UserCredentialsEntity findByActivateToken(String activateToken);

    UserCredentialsEntity findByResetToken(String resetToken);
}
