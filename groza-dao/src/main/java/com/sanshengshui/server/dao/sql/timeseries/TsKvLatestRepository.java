package com.sanshengshui.server.dao.sql.timeseries;

import com.sanshengshui.server.common.data.EntityType;
import com.sanshengshui.server.dao.model.sql.TsKvLatestCompositeKey;
import com.sanshengshui.server.dao.model.sql.TsKvLatestEntity;
import com.sanshengshui.server.dao.util.SqlDao;
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
