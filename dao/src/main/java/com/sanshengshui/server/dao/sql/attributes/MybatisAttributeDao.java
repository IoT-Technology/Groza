package com.sanshengshui.server.dao.sql.attributes;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.sanshengshui.server.common.data.id.EntityId;
import com.sanshengshui.server.common.data.kv.AttributeKvEntry;
import com.sanshengshui.server.dao.DaoUtil;
import com.sanshengshui.server.dao.attributes.AttributesDao;
import com.sanshengshui.server.dao.model.sql.AttributeKvCompositeKey;
import com.sanshengshui.server.dao.model.sql.AttributeKvEntity;
import com.sanshengshui.server.dao.sql.MybatisAbstractDaoListeningExecutorService;
import com.sanshengshui.server.dao.util.SqlDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static com.sanshengshui.server.common.data.UUIDConverter.fromTimeUUID;

/**
 * @author james mu
 * @date 18-12-11 下午2:19
 */
@Component
@Slf4j
@SqlDao
public class MybatisAttributeDao extends MybatisAbstractDaoListeningExecutorService implements AttributesDao {

    @Autowired
    private AttributeKvRepository attributeKvRepository;

    @Override
    public ListenableFuture<Optional<AttributeKvEntry>> find(EntityId entityId, String attributeType, String attributeKey) {
        AttributeKvCompositeKey compositeKey = getAttributeKvCompositeKey(entityId,attributeType,attributeKey);
        return Futures.immediateFuture(
                Optional.ofNullable(DaoUtil.getData(attributeKvRepository.findOne(compositeKey))));

    }

    @Override
    public ListenableFuture<List<AttributeKvEntry>> find(EntityId entityId, String attributeType, Collection<String> attributeKey) {
        return null;
    }

    @Override
    public ListenableFuture<List<AttributeKvEntry>> findAll(EntityId entityId, String attributeType) {
        return null;
    }

    @Override
    public ListenableFuture<Void> save(EntityId entityId, String attributeType, AttributeKvEntry attribute) {
        AttributeKvEntity entity = new AttributeKvEntity();
        entity.setEntityType(entityId.getEntityType());
        entity.setEntityId(fromTimeUUID(entityId.getId()));
        entity.setAttributeType(attributeType);
        entity.setAttributeKey(attribute.getKey());
        entity.setLastUpdateTs(attribute.getLastUpdateTs());
        entity.setStrValue(attribute.getStrValue().orElse(null));
        entity.setDoubleValue(attribute.getDoubleValue().orElse(null));
        entity.setLongValue(attribute.getLongValue().orElse(null));
        entity.setBooleanValue(attribute.getBooleanValue().orElse(null));
        return service.submit(() -> {
            attributeKvRepository.save(entity);
            return null;
        });
    }

    @Override
    public ListenableFuture<List<Void>> removeAll(EntityId entityId, String attributeType, List<String> keys) {
        return null;
    }

    private AttributeKvCompositeKey getAttributeKvCompositeKey(EntityId entityId,String attributeType,String attributeKey){
        return new AttributeKvCompositeKey(
                entityId.getEntityType(),
                fromTimeUUID(entityId.getId()),
                attributeType,
                attributeKey
        );
    }
}
