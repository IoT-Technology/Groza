package com.sanshengshui.server.common.data;

import com.sanshengshui.server.common.data.id.CustomerId;
import com.sanshengshui.server.common.data.id.EntityId;
import com.sanshengshui.server.common.data.id.EntityViewId;
import com.sanshengshui.server.common.data.id.TenantId;
import com.sanshengshui.server.common.data.objects.TelemetryEntityView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author james mu
 * @date 19-1-24 下午1:48
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EntityView extends SearchTextBasedWithAdditionalInfo<EntityViewId>
        implements HasName, HasTenantId, HasCustomerId {

    private static final long serialVersionUID = 5582010124562018986L;

    private EntityId entityId;
    private TenantId tenantId;
    private CustomerId customerId;
    private String name;
    private String type;
    private TelemetryEntityView keys;
    private long startTimeMs;
    private long endTimeMs;

    public EntityView() {
        super();
    }

    public EntityView(EntityViewId id) {
        super(id);
    }

    public EntityView(EntityView entityView) {
        super(entityView);
    }

    @Override
    public String getSearchText() {
        return getName() /*What the ...*/;
    }

    @Override
    public CustomerId getCustomerId() {
        return customerId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public TenantId getTenantId() {
        return tenantId;
    }
}
