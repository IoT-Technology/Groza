package com.sanshengshui.server.common.data.id;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sanshengshui.server.common.data.EntityType;

import java.util.UUID;

/**
 * @author james mu
 * @date 19-1-24 下午1:45
 */
public class EntityViewId extends UUIDBased implements EntityId{

    private static final long serialVersionUID = 1L;

    @JsonCreator
    public EntityViewId(@JsonProperty("id") UUID id) {
        super(id);
    }

    public static EntityViewId fromString(String entityViewID) {
        return new EntityViewId(UUID.fromString(entityViewID));
    }

    @Override
    public EntityType getEntityType(){
        return EntityType.ENTITY_VIEW;
    }
}
