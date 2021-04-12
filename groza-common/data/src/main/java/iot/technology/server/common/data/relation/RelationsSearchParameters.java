package iot.technology.server.common.data.relation;

import iot.technology.server.common.data.EntityType;
import iot.technology.server.common.data.id.EntityId;
import iot.technology.server.common.data.id.EntityIdFactory;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

/**
 * @author james mu
 * @date 19-1-22 上午10:12
 */
@Data
@AllArgsConstructor
public class RelationsSearchParameters {

    private UUID rootId;
    private EntityType rootType;
    private EntitySearchDirection direction;
    private RelationTypeGroup relationTypeGroup;
    private int maxLevel = 1;

    public RelationsSearchParameters(EntityId entityId, EntitySearchDirection direction, int maxLevel) {
        this(entityId, direction, maxLevel, RelationTypeGroup.COMMON);
    }

    public RelationsSearchParameters(EntityId entityId, EntitySearchDirection direction, int maxLevel, RelationTypeGroup relationTypeGroup) {
        this.rootId = entityId.getId();
        this.rootType = entityId.getEntityType();
        this.direction = direction;
        this.maxLevel = maxLevel;
        this.relationTypeGroup = relationTypeGroup;
    }

    public EntityId getEntityId() {
        return EntityIdFactory.getByTypeAndUuid(rootType, rootId);
    }
}
