package com.sanshengshui.server.dao.sql.attributes;

import com.sanshengshui.server.common.data.EntityType;
import com.sanshengshui.server.dao.model.sql.AttributeKvCompositeKey;
import com.sanshengshui.server.dao.model.sql.AttributeKvEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author james mu
 * @date 18-12-11 下午2:04
 */
@Mapper
public interface AttributeKvRepository {

    AttributeKvEntity findOne(AttributeKvCompositeKey attributeKvCompositeKey);

    List<AttributeKvEntity> findAllByEntityTypeAndEntityIdAndAttributeType(EntityType entityType,
                                                                           String entityId,
                                                                           String attributeType);

    List<AttributeKvEntity> findAll(List<AttributeKvCompositeKey> attributeKvCompositeKeyList);

    @Insert("insert into attribute_kv(entity_type,entity_id,attribute_type,attribute_key,bool_v,str_v,long_v,dbl_v,last_update_ts) " +
            "values(#{entityType},#{entityId},#{attributeType},#{attributeKey},#{booleanValue},#{strValue},#{longValue},#{doubleValue},#{lastUpdateTs})")
    int save(AttributeKvEntity attributeKvEntity);

    int delete(List<AttributeKvEntity> entitiesToDelete);
}
