package com.sanshengshui.server.common.data.id;

import com.sanshengshui.server.common.data.EntityType;

import java.util.UUID;

/**
 * @author james mu
 * @date 18-12-6 下午3:48
 */
public class EntityIdFactory {
    public static EntityId getByTypeAndId(String type, String uuid) {
        return getByTypeAndUuid(EntityType.valueOf(type), UUID.fromString(uuid));
    }

    public static EntityId getByTypeAndUuid(String type, UUID uuid) {
        return getByTypeAndUuid(EntityType.valueOf(type), uuid);
    }

    public static EntityId getByTypeAndUuid(EntityType type, String uuid) {
        return getByTypeAndUuid(type, UUID.fromString(uuid));
    }

    public static EntityId getByTypeAndUuid(EntityType type, UUID uuid) {
        switch (type) {
            case DEVICE:
                return new DeviceId(uuid);
        }
        throw new IllegalArgumentException("EntityType " + type + " is not supported!");
    }
}
