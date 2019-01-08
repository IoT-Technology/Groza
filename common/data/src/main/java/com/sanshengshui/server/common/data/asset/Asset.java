package com.sanshengshui.server.common.data.asset;

import com.sanshengshui.server.common.data.HasCustomerId;
import com.sanshengshui.server.common.data.HasName;
import com.sanshengshui.server.common.data.HasTenantId;
import com.sanshengshui.server.common.data.SearchTextBasedWithAdditionalInfo;
import com.sanshengshui.server.common.data.id.AssetId;
import com.sanshengshui.server.common.data.id.CustomerId;
import com.sanshengshui.server.common.data.id.TenantId;
import lombok.EqualsAndHashCode;

/**
 * @author james mu
 * @date 19-1-3 下午4:59
 */
@EqualsAndHashCode(callSuper = true)
public class Asset extends SearchTextBasedWithAdditionalInfo<AssetId> implements HasName, HasTenantId, HasCustomerId {

    private static final long serialVersionUID = 2807343040519543363L;

    private TenantId tenantId;
    private CustomerId customerId;
    private String name;
    private String type;

    public Asset(){
        super();
    }

    public Asset(AssetId id){
        super(id);
    }

    public Asset(Asset asset){
        super(asset);
        this.tenantId = asset.getTenantId();
        this.customerId = asset.getCustomerId();
        this.name = asset.getName();
        this.type = asset.getType();
    }

    @Override
    public CustomerId getCustomerId() {
        return customerId;
    }

    public void setTenantId(TenantId tenantId) {
        this.tenantId = tenantId;
    }

    public void setCustomerId(CustomerId customerId) {
        this.customerId = customerId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public TenantId getTenantId() {
        return tenantId;
    }

    @Override
    public String getSearchText() {
        return getName();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Asset [tenantId=");
        builder.append(tenantId);
        builder.append(", customerId=");
        builder.append(customerId);
        builder.append(", name=");
        builder.append(name);
        builder.append(", type=");
        builder.append(type);
        builder.append(", additionalInfo=");
        builder.append(getAdditionalInfo());
        builder.append(", createdTime=");
        builder.append(createdTime);
        builder.append(", id=");
        builder.append(id);
        builder.append("]");
        return builder.toString();
    }
}
