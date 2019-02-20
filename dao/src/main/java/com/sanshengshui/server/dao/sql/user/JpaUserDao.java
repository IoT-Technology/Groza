package com.sanshengshui.server.dao.sql.user;

import com.sanshengshui.server.common.data.User;
import com.sanshengshui.server.common.data.page.TextPageLink;
import com.sanshengshui.server.common.data.security.Authority;
import com.sanshengshui.server.dao.DaoUtil;
import com.sanshengshui.server.dao.model.sql.UserEntity;
import com.sanshengshui.server.dao.sql.JpaAbstractSearchTextDao;
import com.sanshengshui.server.dao.user.UserDao;
import com.sanshengshui.server.dao.util.SqlDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.sanshengshui.server.common.data.UUIDConverter.fromTimeUUID;
import static com.sanshengshui.server.dao.model.ModelConstants.NULL_UUID_STR;

/**
 * @author james mu
 * @date 19-2-20 下午4:06
 * @description
 */
@SqlDao
@Component
public class JpaUserDao extends JpaAbstractSearchTextDao<UserEntity, User> implements UserDao {

    @Autowired
    private UserRepository userRepository;

    @Override
    protected Class<UserEntity> getEntityClass() {
        return UserEntity.class;
    }

    @Override
    protected CrudRepository<UserEntity, String> getCrudRepository() {
        return userRepository;
    }

    @Override
    public User findByEmail(String email) {
        return DaoUtil.getData(userRepository.findByEmail(email));
    }

    @Override
    public List<User> findTenantAdmins(UUID tenantId, TextPageLink pageLink) {
        return DaoUtil.convertDataList(
                userRepository
                        .findUsersByAuthority(
                                fromTimeUUID(tenantId),
                                NULL_UUID_STR,
                                pageLink.getIdOffset() == null ? NULL_UUID_STR : fromTimeUUID(pageLink.getIdOffset()),
                                Objects.toString(pageLink.getTextSearch(), ""),
                                Authority.TENANT_ADMIN,
                                new PageRequest(0, pageLink.getLimit())));
    }

    @Override
    public List<User> findCustomerUsers(UUID tenantId, UUID customerId, TextPageLink pageLink) {
        return DaoUtil.convertDataList(
                userRepository
                        .findUsersByAuthority(
                                fromTimeUUID(tenantId),
                                fromTimeUUID(customerId),
                                pageLink.getIdOffset() == null ? NULL_UUID_STR : fromTimeUUID(pageLink.getIdOffset()),
                                Objects.toString(pageLink.getTextSearch(), ""),
                                Authority.CUSTOMER_USER,
                                new PageRequest(0, pageLink.getLimit())));
    }
}
