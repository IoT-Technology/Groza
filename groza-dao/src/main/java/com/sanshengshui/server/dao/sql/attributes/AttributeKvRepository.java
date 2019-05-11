package com.sanshengshui.server.dao.sql.attributes;

import com.sanshengshui.server.common.data.EntityType;
import com.sanshengshui.server.dao.model.sql.AttributeKvCompositeKey;
import com.sanshengshui.server.dao.model.sql.AttributeKvEntity;
import com.sanshengshui.server.dao.util.SqlDao;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author james mu
 * @date 18-12-11 下午2:04
 */
@SqlDao
public interface AttributeKvRepository extends CrudRepository<AttributeKvEntity,AttributeKvCompositeKey> {


    List<AttributeKvEntity> findAllByEntityTypeAndEntityIdAndAttributeType(EntityType entityType,
                                                                           String entityId,
                                                                           String attributeType);


}
