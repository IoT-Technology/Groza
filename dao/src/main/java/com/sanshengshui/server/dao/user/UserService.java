package com.sanshengshui.server.dao.user;

import com.google.common.util.concurrent.ListenableFuture;
import com.sanshengshui.server.common.data.User;
import com.sanshengshui.server.common.data.id.CustomerId;
import com.sanshengshui.server.common.data.id.TenantId;
import com.sanshengshui.server.common.data.id.UserId;
import com.sanshengshui.server.common.data.page.TextPageData;
import com.sanshengshui.server.common.data.page.TextPageLink;
import com.sanshengshui.server.common.data.security.UserCredentials;

/**
 * @author james mu
 * @date 19-2-20 下午2:27
 * @description
 */
public interface UserService {

    User findUserById(UserId userId);

    ListenableFuture<User> findUserByIdAsync(UserId userId);

    User findUserByEmail(String email);

    User saveUser(User user);

    UserCredentials findUserCredentialsByUserId(UserId userId);

    UserCredentials findUserCredentialsByActivateToken(String activateToken);

    UserCredentials findUserCredentialsByResetToken(String resetToken);

    UserCredentials saveUserCredentials(UserCredentials userCredentials);

    UserCredentials activateUserCredentials(String activateToken, String password);

    UserCredentials requestPasswordReset(String email);

    void deleteUser(UserId userId);

    TextPageData<User> findTenantAdmins(TenantId tenantId, TextPageLink pageLink);

    void deleteTenantAdmins(TenantId tenantId);

    TextPageData<User> findCustomerUsers(TenantId tenantId, CustomerId customerId, TextPageLink pageLink);

    void deleteCustomerUsers(TenantId tenantId, CustomerId customerId);
}

