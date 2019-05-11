package com.sanshengshui.server.common.data.id;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sanshengshui.server.common.data.EntityType;

import java.util.UUID;

/**
 * @author james mu
 * @date 19-1-22 上午10:15
 */
public class UserId extends UUIDBased implements EntityId{

    @JsonCreator
    public UserId(@JsonProperty("id") UUID id) {
        super(id);
    }

    public static UserId fromString(String userId) {
        return new UserId(UUID.fromString(userId));
    }

    @JsonIgnore
    @Override
    public EntityType getEntityType() {
        return EntityType.USER;
    }
}
