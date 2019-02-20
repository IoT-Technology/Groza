package com.sanshengshui.server.dao.model.sql;

import com.datastax.driver.core.utils.UUIDs;
import com.fasterxml.jackson.databind.JsonNode;
import com.sanshengshui.server.common.data.User;
import com.sanshengshui.server.common.data.id.CustomerId;
import com.sanshengshui.server.common.data.id.TenantId;
import com.sanshengshui.server.common.data.id.UserId;
import com.sanshengshui.server.common.data.security.Authority;
import com.sanshengshui.server.dao.model.BaseSqlEntity;
import com.sanshengshui.server.dao.model.ModelConstants;
import com.sanshengshui.server.dao.model.SearchTextEntity;
import com.sanshengshui.server.dao.util.mapping.JsonStringType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

import static com.sanshengshui.server.common.data.UUIDConverter.fromString;
import static com.sanshengshui.server.common.data.UUIDConverter.fromTimeUUID;

/**
 * @author james mu
 * @date 19-2-20 下午3:32
 * @description
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@TypeDef(name = "json", typeClass = JsonStringType.class)
@Table(name = ModelConstants.USER_PG_HIBERNATE_COLUMN_FAMILY_NAME)
public class UserEntity extends BaseSqlEntity<User> implements SearchTextEntity<User> {

    @Column(name = ModelConstants.USER_TENANT_ID_PROPERTY)
    private String tenantId;

    @Column(name = ModelConstants.USER_CUSTOMER_ID_PROPERTY)
    private String customerId;

    @Enumerated(EnumType.STRING)
    @Column(name = ModelConstants.USER_AUTHORITY_PROPERTY)
    private Authority authority;

    @Column(name = ModelConstants.USER_EMAIL_PROPERTY, unique = true)
    private String email;

    @Column(name = ModelConstants.SEARCH_TEXT_PROPERTY)
    private String searchText;

    @Column(name = ModelConstants.USER_FIRST_NAME_PROPERTY)
    private String firstName;

    @Column(name = ModelConstants.USER_LAST_NAME_PROPERTY)
    private String lastName;

    @Type(type = "json")
    @Column(name = ModelConstants.USER_ADDITIONAL_INFO_PROPERTY)
    private JsonNode additionalInfo;

    public UserEntity() {
    }

    public UserEntity(User user) {
        if (user.getId() != null) {
            this.setId(user.getId().getId());
        }
        this.authority = user.getAuthority();
        if (user.getTenantId() != null) {
            this.tenantId = fromTimeUUID(user.getTenantId().getId());
        }
        if (user.getCustomerId() != null) {
            this.customerId = fromTimeUUID(user.getCustomerId().getId());
        }
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.additionalInfo = user.getAdditionalInfo();
    }

    @Override
    public String getSearchTextSource() {
        return email;
    }

    @Override
    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    @Override
    public User toData() {
        User user = new User(new UserId(getId()));
        user.setCreatedTime(UUIDs.unixTimestamp(getId()));
        user.setAuthority(authority);
        if (tenantId != null) {
            user.setTenantId(new TenantId(fromString(tenantId)));
        }
        if (customerId != null) {
            user.setCustomerId(new CustomerId(fromString(customerId)));
        }
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setAdditionalInfo(additionalInfo);
        return user;
    }
}
