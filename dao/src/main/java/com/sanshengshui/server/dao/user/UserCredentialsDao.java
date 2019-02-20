package com.sanshengshui.server.dao.user;

import com.sanshengshui.server.common.data.security.UserCredentials;
import com.sanshengshui.server.dao.Dao;

import java.util.UUID;

/**
 * @author james mu
 * @date 19-2-20 下午2:32
 * @description The Interface UserCredentialsDao
 */
public interface UserCredentialsDao extends Dao<UserCredentials> {

    /**
     * Save or update user credentials object
     *
     * @param userCredentials the user credentials object
     * @return saved user credentials object
     */
    UserCredentials save(UserCredentials userCredentials);

    /**
     * Find user credentials by user id.
     *
     * @param userId the user id
     * @return the user credentials object
     */
    UserCredentials findByUserId(UUID userId);

    /**
     * Find user credentials by activate token.
     *
     * @param activateToken the activate token
     * @return the user credentials object
     */
    UserCredentials findByActivateToken(String activateToken);

    /**
     * Find user credentials by reset token.
     *
     * @param resetToken the reset token
     * @return the user credentials object
     */
    UserCredentials findByResetToken(String resetToken);
}
