package com.sanshengshui.server.dao.relation;

import com.google.common.util.concurrent.ListenableFuture;
import com.sanshengshui.server.common.data.EntityType;
import com.sanshengshui.server.common.data.id.EntityId;
import com.sanshengshui.server.common.data.page.TimePageLink;
import com.sanshengshui.server.common.data.relation.EntityRelation;
import com.sanshengshui.server.common.data.relation.RelationTypeGroup;

import java.util.List;

/**
 * @author james mu
 * @date 19-1-4 上午9:36
 */
public interface RelationDao {

    ListenableFuture<List<EntityRelation>> findAllByFrom(EntityId from, RelationTypeGroup typeGroup);

    ListenableFuture<List<EntityRelation>> findAllByFromAndType(EntityId from, String relationType, RelationTypeGroup typeGroup);

    ListenableFuture<List<EntityRelation>> findAllByTo(EntityId to, RelationTypeGroup typeGroup);

    ListenableFuture<List<EntityRelation>> findAllByToAndType(EntityId to, String relationType, RelationTypeGroup typeGroup);

    ListenableFuture<Boolean> checkRelation(EntityId from, EntityId to, String relationType, RelationTypeGroup typeGroup);

    ListenableFuture<EntityRelation> getRelation(EntityId from, EntityId to, String relationType, RelationTypeGroup typeGroup);

    boolean saveRelation(EntityRelation relation);

    ListenableFuture<Boolean> saveRelationAsync(EntityRelation relation);

//    boolean deleteRelation(EntityRelation relation);
//
//    ListenableFuture<Boolean> deleteRelationAsync(EntityRelation relation);
//
//    boolean deleteRelation(EntityId from, EntityId to, String relationType, RelationTypeGroup typeGroup);
//
//    ListenableFuture<Boolean> deleteRelationAsync(EntityId from, EntityId to, String relationType, RelationTypeGroup typeGroup);

    boolean deleteOutboundRelations(EntityId entity);

    ListenableFuture<Boolean> deleteOutboundRelationsAsync(EntityId entity);

    ListenableFuture<List<EntityRelation>> findRelations(EntityId from, String relationType, RelationTypeGroup typeGroup, EntityType toType, TimePageLink pageLink);
}
