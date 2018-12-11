package com.sanshengshui.server.dao.sql.attributes;

import com.google.common.util.concurrent.ListenableFuture;
import com.sanshengshui.server.common.data.id.EntityId;
import com.sanshengshui.server.common.data.kv.AttributeKvEntry;
import com.sanshengshui.server.dao.attributes.AttributesDao;
import com.sanshengshui.server.dao.sql.MybatisAbstractDaoListeningExecutorService;
import com.sanshengshui.server.dao.util.SqlDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @author james mu
 * @date 18-12-11 下午2:19
 */
@Component
@Slf4j
@SqlDao
public class MybatisAttributeDao extends MybatisAbstractDaoListeningExecutorService implements AttributesDao {
    @Override
    public ListenableFuture<Optional<AttributeKvEntry>> find(EntityId entityId, String attributeType, String attributeKey) {
        return null;
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
        return null;
    }

    @Override
    public ListenableFuture<List<Void>> removeAll(EntityId entityId, String attributeType, List<String> keys) {
        return null;
    }
}
