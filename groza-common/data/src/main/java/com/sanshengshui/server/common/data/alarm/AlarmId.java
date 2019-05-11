package com.sanshengshui.server.common.data.alarm;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sanshengshui.server.common.data.EntityType;
import com.sanshengshui.server.common.data.id.EntityId;
import com.sanshengshui.server.common.data.id.UUIDBased;

import java.util.UUID;

/**
 * @author james mu
 * @date 19-1-3 下午4:20
 */
public class AlarmId extends UUIDBased implements EntityId {

    private static final long serialVersionUID = 1L;

    @JsonCreator
    public AlarmId(@JsonProperty("id") UUID id){
        super(id);
    }

    public static AlarmId fromString(String alarmId){
        return new AlarmId(UUID.fromString(alarmId));
    }

    @JsonIgnore
    @Override
    public EntityType getEntityType() {
        return EntityType.ALARM;
    }
}
