package com.sanshengshui.server.common.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sanshengshui.server.common.data.id.CustomerId;
import com.sanshengshui.server.common.data.id.TenantId;

/**
 * @author james mu
 * @date 19-1-3 下午3:46
 */
public class Customer extends ContactBased<CustomerId> implements HasName,HasTenantId {

    private static final long serialVersionUID = -1599722990298929275L;

    private String title;
    private TenantId tenantId;

    public Customer(){
        super();
    }

    public Customer(CustomerId id){
        super(id);
    }

    public Customer(Customer customer){
        super(customer);
        this.tenantId = customer.getTenantId();
        this.title = customer.getTitle();
    }

    @Override
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public String getName() {
        return title;
    }

    @Override
    public TenantId getTenantId() {
        return tenantId;
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

    public void setTenantId(TenantId tenantId) {
        this.tenantId = tenantId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((tenantId == null) ? 0 : tenantId.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Customer other = (Customer) obj;
        if (tenantId == null) {
            if (other.tenantId != null)
                return false;
        } else if (!tenantId.equals(other.tenantId))
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Customer [title=");
        builder.append(title);
        builder.append(", tenantId=");
        builder.append(tenantId);
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

    @JsonIgnore
    public boolean isPublic() {
        if (getAdditionalInfo() != null && getAdditionalInfo().has("isPublic")) {
            return getAdditionalInfo().get("isPublic").asBoolean();
        }

        return false;
    }

    @JsonIgnore
    public ShortCustomerInfo toShortCustomerInfo() {
        return new ShortCustomerInfo(id, title, isPublic());
    }
}
