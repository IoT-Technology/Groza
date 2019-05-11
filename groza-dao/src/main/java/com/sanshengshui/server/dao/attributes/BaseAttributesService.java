package com.sanshengshui.server.dao.attributes;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.sanshengshui.server.common.data.id.EntityId;
import com.sanshengshui.server.common.data.kv.AttributeKvEntry;
import com.sanshengshui.server.dao.exception.IncorrectParameterException;
import com.sanshengshui.server.dao.service.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @author james mu
 * @date 18-12-11 下午1:23
 */
@Service
public class BaseAttributesService implements AttributesService{

    @Autowired
    private AttributesDao attributesDao;

    @Override
    public ListenableFuture<Optional<AttributeKvEntry>> find(EntityId entityId, String scope, String attributeKey) {
        validate(entityId, scope);
        Validator.validateString(attributeKey, "Incorrect attribute key " + attributeKey);
        return attributesDao.find(entityId, scope, attributeKey);
    }

    @Override
    public ListenableFuture<List<AttributeKvEntry>> find(EntityId entityId, String scope, Collection<String> attributeKeys) {
        validate(entityId, scope);
        attributeKeys.forEach(attributeKey -> Validator.validateString(attributeKey, "Incorrect attribute key " + attributeKey));
        return attributesDao.find(entityId, scope, attributeKeys);
    }

    @Override
    public ListenableFuture<List<AttributeKvEntry>> findAll(EntityId entityId, String scope) {
        validate(entityId, scope);
        return attributesDao.findAll(entityId, scope);
    }

    @Override
    public ListenableFuture<List<Void>> save(EntityId entityId, String scope, List<AttributeKvEntry> attributes) {
        validate(entityId,scope);
        attributes.forEach(attribute -> validate(attribute));
        List<ListenableFuture<Void>> futures = Lists.newArrayListWithExpectedSize(attributes.size());
        for (AttributeKvEntry attribute : attributes){
            futures.add(attributesDao.save(entityId,scope,attribute));
        }
        return Futures.allAsList(futures);
    }

    @Override
    public ListenableFuture<List<Void>> removeAll(EntityId entityId, String scope, List<String> attributeKeys) {
        validate(entityId, scope);
        return attributesDao.removeAll(entityId, scope, attributeKeys);
    }

    private static void validate(EntityId id, String scope) {
        Validator.validateId(id.getId(), "Incorrect id " + id);
        Validator.validateString(scope, "Incorrect scope " + scope);
    }

    private static void validate(AttributeKvEntry kvEntry){
        if (kvEntry == null){
            throw new IncorrectParameterException("Key value entry can't be null");
        }else if (kvEntry.getDataType() == null){
            throw new IncorrectParameterException("Incorrect kvEntry. Data type can't be null");
        }else {
            Validator.validateString(kvEntry.getKey(), "Incorrect kvEntry. Key can't be empty");
            Validator.validatePositiveNumber(kvEntry.getLastUpdateTs(), "Incorrect last update ts. Ts should be positive");
        }
    }
}
