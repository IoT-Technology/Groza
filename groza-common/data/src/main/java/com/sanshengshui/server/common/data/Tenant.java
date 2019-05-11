package com.sanshengshui.server.common.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sanshengshui.server.common.data.id.TenantId;
import lombok.EqualsAndHashCode;

/**
 * @author james mu
 * @date 19-1-3 下午12:06
 */
@EqualsAndHashCode(callSuper = true)
public class Tenant extends ContactBased<TenantId> implements HasName {

    private static final long serialVersionUID = 8057243243859922101L;

    private String title;
    private String region;

    public Tenant(){
        super();
    }
    public Tenant(TenantId id){
        super(id);
    }

    public Tenant(Tenant tenant){
        super(tenant);
        this.title = tenant.getTitle();
        this.region = tenant.getRegion();
    }
    @Override
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public String getName() {
        return title;
    }

    @Override
    public String getSearchText() {
        return getTitle();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Tenant [title=");
        builder.append(title);
        builder.append(", region=");
        builder.append(region);
        builder.append(", additionalInfo=");
        builder.append(getAdditionalInfo());
        builder.append(", country=");
        builder.append(country);
        builder.append(", state=");
        builder.append(state);
        builder.append(", city=");
        builder.append(city);
        builder.append(", address=");
        builder.append(address);
        builder.append(", address2=");
        builder.append(address2);
        builder.append(", zip=");
        builder.append(zip);
        builder.append(", phone=");
        builder.append(phone);
        builder.append(", email=");
        builder.append(email);
        builder.append(", createdTime=");
        builder.append(createdTime);
        builder.append(", id=");
        builder.append(id);
        builder.append("]");
        return builder.toString();
    }
}
