package com.sanshengshui.server.common.data.id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sanshengshui.server.common.data.EntityType;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author james mu
 * @date 18-12-6 下午3:28
 */
@JsonDeserialize(using = EntityIdDeserializer.class)
@JsonSerialize()
public interface EntityId extends Serializable {

    UUID NULL_UUID = UUID.fromString("13814000-1dd2-11b2-8080-808080808080");

    UUID getId();

    EntityType getEntityType();

    @JsonIgnore
    default boolean isNullUid() {
        return NULL_UUID.equals(getId());
    }
}
