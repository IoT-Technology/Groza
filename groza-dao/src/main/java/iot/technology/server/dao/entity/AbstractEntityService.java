package iot.technology.server.dao.entity;

import iot.technology.server.common.data.id.EntityId;
import iot.technology.server.dao.relation.RelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author james mu
 * @date 19-1-22 上午11:01
 */
@Slf4j
public abstract class AbstractEntityService {

    @Autowired
    protected RelationService relationService;

    protected void deleteEntityRelations(EntityId entityId) {
        log.trace("Executing deleteEntityRelations [{}]", entityId);
        relationService.deleteEntityRelations(entityId);
    }
}
