package com.sanshengshui.server.common.data.security;

import com.sanshengshui.server.common.data.BaseData;
import com.sanshengshui.server.common.data.id.UserCredentialsId;
import com.sanshengshui.server.common.data.id.UserId;
import lombok.EqualsAndHashCode;

/**
 * @author james mu
 * @date 19-2-20 下午2:59
 * @description
 */
@EqualsAndHashCode(callSuper = true)
public class UserCredentials extends BaseData<UserCredentialsId> {

    public static final long serialVersionUID = -2108436378880529163L;

    private UserId userId;

    private boolean enabled;

    private String password;

    private String activateToken;

    private String resetToken;

    public UserCredentials() {
        super();
    }

    public UserCredentials(UserCredentialsId id) {
        super(id);
    }

    public UserCredentials(UserCredentials userCredentials) {
        super(userCredentials);
        this.userId = userCredentials.getUserId();
        this.password = userCredentials.getPassword();
        this.enabled = userCredentials.isEnabled();
        this.activateToken = userCredentials.getActivateToken();
        this.resetToken = userCredentials.getResetToken();
    }

    public UserId getUserId() {
        return userId;
    }

    public void setUserId(UserId userId) {
        this.userId = userId;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getActivateToken() {
        return activateToken;
    }

    public void setActivateToken(String activateToken) {
        this.activateToken = activateToken;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("UserCredentials [userId=");
        builder.append(userId);
        builder.append(", enabled=");
        builder.append(enabled);
        builder.append(", password=");
        builder.append(password);
        builder.append(", activateToken=");
        builder.append(activateToken);
        builder.append(", resetToken=");
        builder.append(resetToken);
        builder.append(", createdTime=");
        builder.append(createdTime);
        builder.append(", id=");
        builder.append(id);
        builder.append("]");
        return builder.toString();
    }

}
