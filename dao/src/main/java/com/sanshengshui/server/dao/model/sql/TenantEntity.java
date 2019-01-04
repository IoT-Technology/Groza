package com.sanshengshui.server.dao.model.sql;

import com.datastax.driver.core.utils.UUIDs;
import com.fasterxml.jackson.databind.JsonNode;
import com.sanshengshui.server.common.data.Tenant;
import com.sanshengshui.server.common.data.id.TenantId;
import com.sanshengshui.server.dao.model.BaseSqlEntity;
import com.sanshengshui.server.dao.model.ModelConstants;
import com.sanshengshui.server.dao.model.SearchTextEntity;
import com.sanshengshui.server.dao.util.mapping.JsonStringType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author james mu
 * @date 19-1-4 下午5:03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@TypeDef(name = "json", typeClass = JsonStringType.class)
@Table(name = ModelConstants.TENANT_COLUMN_FAMILY_NAME)
public final class TenantEntity extends BaseSqlEntity<Tenant> implements SearchTextEntity<Tenant> {

    @Column(name = ModelConstants.TENANT_TITLE_PROPERTY)
    private String title;

    @Column(name = ModelConstants.SEARCH_TEXT_PROPERTY)
    private String searchText;

    @Column(name = ModelConstants.TENANT_REGION_PROPERTY)
    private String region;

    @Column(name = ModelConstants.COUNTRY_PROPERTY)
    private String country;

    @Column(name = ModelConstants.STATE_PROPERTY)
    private String state;

    @Column(name = ModelConstants.CITY_PROPERTY)
    private String city;

    @Column(name = ModelConstants.ADDRESS_PROPERTY)
    private String address;

    @Column(name = ModelConstants.ADDRESS2_PROPERTY)
    private String address2;

    @Column(name = ModelConstants.ZIP_PROPERTY)
    private String zip;

    @Column(name = ModelConstants.PHONE_PROPERTY)
    private String phone;

    @Column(name = ModelConstants.EMAIL_PROPERTY)
    private String email;

    @Type(type = "json")
    @Column(name = ModelConstants.TENANT_ADDITIONAL_INFO_PROPERTY)
    private JsonNode additionalInfo;

    public TenantEntity() {
        super();
    }

    public TenantEntity(Tenant tenant) {
        if (tenant.getId() != null) {
            this.setId(tenant.getId().getId());
        }
        this.title = tenant.getTitle();
        this.region = tenant.getRegion();
        this.country = tenant.getCountry();
        this.state = tenant.getState();
        this.city = tenant.getCity();
        this.address = tenant.getAddress();
        this.address2 = tenant.getAddress2();
        this.zip = tenant.getZip();
        this.phone = tenant.getPhone();
        this.email = tenant.getEmail();
        this.additionalInfo = tenant.getAdditionalInfo();
    }

    @Override
    public String getSearchTextSource() {
        return title;
    }

    @Override
    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public String getSearchText() {
        return searchText;
    }

    @Override
    public Tenant toData() {
        Tenant tenant = new Tenant(new TenantId(getId()));
        tenant.setCreatedTime(UUIDs.unixTimestamp(getId()));
        tenant.setTitle(title);
        tenant.setRegion(region);
        tenant.setCountry(country);
        tenant.setState(state);
        tenant.setCity(city);
        tenant.setAddress(address);
        tenant.setAddress2(address2);
        tenant.setZip(zip);
        tenant.setPhone(phone);
        tenant.setEmail(email);
        tenant.setAdditionalInfo(additionalInfo);
        return tenant;
    }
}
