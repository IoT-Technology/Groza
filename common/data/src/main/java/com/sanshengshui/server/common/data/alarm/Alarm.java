package com.sanshengshui.server.common.data.alarm;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.sanshengshui.server.common.data.BaseData;
import com.sanshengshui.server.common.data.HasName;
import com.sanshengshui.server.common.data.HasTenantId;
import com.sanshengshui.server.common.data.id.EntityId;
import com.sanshengshui.server.common.data.id.TenantId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author james mu
 * @date 19-1-3 下午4:20
 */
@Data
@Builder
@AllArgsConstructor
public class Alarm extends BaseData<AlarmId> implements HasName, HasTenantId {

    private TenantId tenantId;
    private String type;
    private EntityId originator;
    private AlarmSeverity severity;
    private AlarmStatus status;
    private long startTs;
    private long endTs;
    private long ackTs;
    private long clearTs;
    private transient JsonNode details;
    private boolean propagate;

    public Alarm(){
        super();
    }
    public Alarm(AlarmId id){
        super(id);
    }

    public Alarm(Alarm alarm){
        super(alarm.getId());
        this.createdTime = alarm.getCreatedTime();
        this.tenantId = alarm.getTenantId();
        this.type = alarm.getType();
        this.originator = alarm.getOriginator();
        this.severity = alarm.getSeverity();
        this.status = alarm.getStatus();
        this.startTs = alarm.getStartTs();
        this.endTs = alarm.getEndTs();
        this.ackTs = alarm.getAckTs();
        this.clearTs = alarm.getClearTs();
        this.details = alarm.getDetails();
        this.propagate = alarm.isPropagate();
    }

    @Override
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public String getName() {
        return type;
    }
}
