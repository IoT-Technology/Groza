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

    @Insert("insert into attribute_kv(attribute_type,attribute_key,str_v) values(#{attributeType},#{attributeKey},#{strValue})")
    int save(AttributeKvEntity attributeKvEntity);

    int delete(List<AttributeKvEntity> entitiesToDelete);
}
