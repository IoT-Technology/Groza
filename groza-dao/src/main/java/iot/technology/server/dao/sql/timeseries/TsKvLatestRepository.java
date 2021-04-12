package iot.technology.server.dao.sql.timeseries;

import iot.technology.server.common.data.EntityType;
import iot.technology.server.dao.model.sql.TsKvLatestCompositeKey;
import iot.technology.server.dao.model.sql.TsKvLatestEntity;
import iot.technology.server.dao.util.SqlDao;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author james mu
 * @date 19-1-30 下午4:43
 * @description
 */
@SqlDao
public interface TsKvLatestRepository extends CrudRepository<TsKvLatestEntity, TsKvLatestCompositeKey> {

    List<TsKvLatestEntity> findAllByEntityTypeAndEntityId(EntityType entityType, String entityId);
}
