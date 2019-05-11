package com.sanshengshui.server.dao.model.sql;

import com.datastax.driver.core.utils.UUIDs;
import com.sanshengshui.server.common.data.id.UserCredentialsId;
import com.sanshengshui.server.common.data.id.UserId;
import com.sanshengshui.server.common.data.security.UserCredentials;
import com.sanshengshui.server.dao.model.BaseEntity;
import com.sanshengshui.server.dao.model.BaseSqlEntity;
import com.sanshengshui.server.dao.model.ModelConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author james mu
 * @date 19-2-20 下午2:55
 * @description
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = ModelConstants.USER_CREDENTIALS_COLUMN_FAMILY_NAME)
public class UserCredentialsEntity extends BaseSqlEntity<UserCredentials> implements BaseEntity<UserCredentials> {

    @Column(name = ModelConstants.USER_CREDENTIALS_USER_ID_PROPERTY, unique = true)
    private String userId;

    @Column(name = ModelConstants.USER_CREDENTIALS_ENABLED_PROPERTY)
    private boolean enabled;

    @Column(name = ModelConstants.USER_CREDENTIALS_PASSWORD_PROPERTY)
    private String password;

    @Column(name = ModelConstants.USER_CREDENTIALS_ACTIVATE_TOKEN_PROPERTY, unique = true)
    private String activateToken;

    @Column(name = ModelConstants.USER_CREDENTIALS_RESET_TOKEN_PROPERTY, unique = true)
    private String resetToken;

    public UserCredentialsEntity() {
        super();
    }

    public UserCredentialsEntity(UserCredentials userCredentials) {
        if (userCredentials.getId() != null) {
            this.setId(userCredentials.getId().getId());
        }
        if (userCredentials.getUserId() != null) {
            this.userId = toString(userCredentials.getUserId().getId());
        }
        this.enabled = userCredentials.isEnabled();
        this.password = userCredentials.getPassword();
        this.activateToken = userCredentials.getActivateToken();
        this.resetToken = userCredentials.getResetToken();
    }

    @Override
    public UserCredentials toData() {
        UserCredentials userCredentials = new UserCredentials(new UserCredentialsId(getId()));
        userCredentials.setCreatedTime(UUIDs.unixTimestamp(getId()));
        if (userId != null) {
            userCredentials.setUserId(new UserId(toUUID(userId)));
        }
        userCredentials.setEnabled(enabled);
        userCredentials.setPassword(password);
        userCredentials.setActivateToken(activateToken);
        userCredentials.setResetToken(resetToken);
        return userCredentials;
    }
}
