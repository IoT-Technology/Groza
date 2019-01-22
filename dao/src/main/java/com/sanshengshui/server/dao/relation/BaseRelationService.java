package com.sanshengshui.server.dao.relation;

import com.google.common.util.concurrent.ListenableFuture;
import com.sanshengshui.server.common.data.id.EntityId;
import com.sanshengshui.server.common.data.relation.EntityRelation;
import com.sanshengshui.server.common.data.relation.EntityRelationInfo;
import com.sanshengshui.server.common.data.relation.EntityRelationsQuery;
import com.sanshengshui.server.common.data.relation.RelationTypeGroup;
import com.sanshengshui.server.dao.util.SqlDao;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author james mu
 * @date 19-1-22 上午11:17
 */
@Service
@SqlDao
public class BaseRelationService implements RelationService{

    @Override
    public ListenableFuture<Boolean> checkRelation(EntityId from, EntityId to, String relationType, RelationTypeGroup typeGroup) {
        return null;
    }

    @Override
    public EntityRelation getRelation(EntityId from, EntityId to, String relationType, RelationTypeGroup typeGroup) {
        return null;
    }

    @Override
    public ListenableFuture<EntityRelation> getRelationAsync(EntityId from, EntityId to, String relationType, RelationTypeGroup typeGroup) {
        return null;
    }

    @Override
    public boolean saveRelation(EntityRelation relation) {
        return false;
    }

    @Override
    public ListenableFuture<Boolean> saveRelationAsync(EntityRelation relation) {
        return null;
    }

    @Override
    public boolean deleteRelation(EntityRelation relation) {
        return false;
    }

    @Override
    public ListenableFuture<Boolean> deleteRelationAsync(EntityRelation relation) {
        return null;
    }

    @Override
    public boolean deleteRelation(EntityId from, EntityId to, String relationType, RelationTypeGroup typeGroup) {
        return false;
    }

    @Override
    public ListenableFuture<Boolean> deleteRelationAsync(EntityId from, EntityId to, String relationType, RelationTypeGroup typeGroup) {
        return null;
    }

    @Override
    public void deleteEntityRelations(EntityId entity) {

    }

    @Override
    public ListenableFuture<Void> deleteEntityRelationsAsync(EntityId entity) {
        return null;
    }

    @Override
    public List<EntityRelation> findByFrom(EntityId from, RelationTypeGroup typeGroup) {
        return null;
    }

    @Override
    public ListenableFuture<List<EntityRelation>> findByFromAsync(EntityId from, RelationTypeGroup typeGroup) {
        return null;
    }

    @Override
    public ListenableFuture<List<EntityRelationInfo>> findInfoByFrom(EntityId from, RelationTypeGroup typeGroup) {
        return null;
    }

    @Override
    public List<EntityRelation> findByFromAndType(EntityId from, String relationType, RelationTypeGroup typeGroup) {
        return null;
    }

    @Override
    public ListenableFuture<List<EntityRelation>> findByFromAndTypeAsync(EntityId from, String relationType, RelationTypeGroup typeGroup) {
        return null;
    }

    @Override
    public List<EntityRelation> findByTo(EntityId to, RelationTypeGroup typeGroup) {
        return null;
    }

    @Override
    public ListenableFuture<List<EntityRelation>> findByToAsync(EntityId to, RelationTypeGroup typeGroup) {
        return null;
    }

    @Override
    public ListenableFuture<List<EntityRelationInfo>> findInfoByTo(EntityId to, RelationTypeGroup typeGroup) {
        return null;
    }

    @Override
    public List<EntityRelation> findByToAndType(EntityId to, String relationType, RelationTypeGroup typeGroup) {
        return null;
    }

    @Override
    public ListenableFuture<List<EntityRelation>> findByToAndTypeAsync(EntityId to, String relationType, RelationTypeGroup typeGroup) {
        return null;
    }

    @Override
    public ListenableFuture<List<EntityRelation>> findByQuery(EntityRelationsQuery query) {
        return null;
    }

    @Override
    public ListenableFuture<List<EntityRelationInfo>> findInfoByQuery(EntityRelationsQuery query) {
        return null;
    }
}
