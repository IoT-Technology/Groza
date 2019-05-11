package com.sanshengshui.server.dao.sql.relation;

import com.google.common.util.concurrent.ListenableFuture;
import com.sanshengshui.server.common.data.EntityType;
import com.sanshengshui.server.common.data.id.EntityId;
import com.sanshengshui.server.common.data.page.TimePageLink;
import com.sanshengshui.server.common.data.relation.EntityRelation;
import com.sanshengshui.server.common.data.relation.RelationTypeGroup;
import com.sanshengshui.server.dao.DaoUtil;
import com.sanshengshui.server.dao.model.sql.RelationCompositeKey;
import com.sanshengshui.server.dao.model.sql.RelationEntity;
import com.sanshengshui.server.dao.relation.RelationDao;
import com.sanshengshui.server.dao.sql.JpaAbstractDaoListeningExecutorService;
import com.sanshengshui.server.dao.sql.JpaAbstractSearchTimeDao;
import com.sanshengshui.server.dao.util.SqlDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

import static com.sanshengshui.server.common.data.UUIDConverter.fromTimeUUID;
import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * @author james mu
 * @date 19-1-22 上午11:06
 */
@Slf4j
@Component
@SqlDao
public class JpaRelationDao extends JpaAbstractDaoListeningExecutorService implements RelationDao {

    @Autowired
    private RelationRepository relationRepository;

    @Override
    public ListenableFuture<List<EntityRelation>> findAllByFrom(EntityId from, RelationTypeGroup typeGroup) {
        return service.submit(() -> DaoUtil.convertDataList(
                relationRepository.findAllByFromIdAndFromTypeAndRelationTypeGroup(
                        fromTimeUUID(from.getId()),
                        from.getEntityType().name(),
                        typeGroup.name())));
    }

    @Override
    public ListenableFuture<List<EntityRelation>> findAllByFromAndType(EntityId from, String relationType, RelationTypeGroup typeGroup) {
        return service.submit(() -> DaoUtil.convertDataList(
                relationRepository.findAllByFromIdAndFromTypeAndRelationTypeAndRelationTypeGroup(
                        fromTimeUUID(from.getId()),
                        from.getEntityType().name(),
                        relationType,
                        typeGroup.name())));
    }

    @Override
    public ListenableFuture<List<EntityRelation>> findAllByTo(EntityId to, RelationTypeGroup typeGroup) {
        return service.submit(() -> DaoUtil.convertDataList(
                relationRepository.findAllByToIdAndToTypeAndRelationTypeGroup(
                        fromTimeUUID(to.getId()),
                        to.getEntityType().name(),
                        typeGroup.name())));
    }

    @Override
    public ListenableFuture<List<EntityRelation>> findAllByToAndType(EntityId to, String relationType, RelationTypeGroup typeGroup) {
        return service.submit(() -> DaoUtil.convertDataList(
                relationRepository.findAllByToIdAndToTypeAndRelationTypeAndRelationTypeGroup(
                        fromTimeUUID(to.getId()),
                        to.getEntityType().name(),
                        relationType,
                        typeGroup.name())));
    }

    @Override
    public ListenableFuture<Boolean> checkRelation(EntityId from, EntityId to, String relationType, RelationTypeGroup typeGroup) {
        RelationCompositeKey key = getRelationCompositeKey(from, to, relationType, typeGroup);
        return service.submit(() -> relationRepository.findById(key).get() != null);
    }

    @Override
    public ListenableFuture<EntityRelation> getRelation(EntityId from, EntityId to, String relationType, RelationTypeGroup typeGroup) {
        RelationCompositeKey key = getRelationCompositeKey(from, to, relationType, typeGroup);
        return service.submit(() -> DaoUtil.getData(relationRepository.findById(key).get()));
    }

    private RelationCompositeKey getRelationCompositeKey(EntityId from, EntityId to, String relationType, RelationTypeGroup typeGroup) {
        return new RelationCompositeKey(fromTimeUUID(from.getId()),
                from.getEntityType().name(),
                fromTimeUUID(to.getId()),
                to.getEntityType().name(),
                relationType,
                typeGroup.name());
    }

    @Override
    public boolean saveRelation(EntityRelation relation) {
        return relationRepository.save(new RelationEntity(relation)) != null;
    }

    @Override
    public ListenableFuture<Boolean> saveRelationAsync(EntityRelation relation) {
        return service.submit(() -> relationRepository.save(new RelationEntity(relation)) != null);
    }

//    @Override
//    public boolean deleteRelation(EntityRelation relation) {
//        RelationCompositeKey key = new RelationCompositeKey(relation);
//        return deleteRelationIfExists(key);
//    }
//
//    @Override
//    public ListenableFuture<Boolean> deleteRelationAsync(EntityRelation relation) {
//        RelationCompositeKey key = new RelationCompositeKey(relation);
//        return service.submit(
//                () -> deleteRelationIfExists(key));
//    }
//
//    @Override
//    public boolean deleteRelation(EntityId from, EntityId to, String relationType, RelationTypeGroup typeGroup) {
//        RelationCompositeKey key = getRelationCompositeKey(from, to, relationType, typeGroup);
//        return deleteRelationIfExists(key);
//    }
//
//    @Override
//    public ListenableFuture<Boolean> deleteRelationAsync(EntityId from, EntityId to, String relationType, RelationTypeGroup typeGroup) {
//        RelationCompositeKey key = getRelationCompositeKey(from, to, relationType, typeGroup);
//        return service.submit(
//                () -> deleteRelationIfExists(key));
//    }

//    private boolean deleteRelationIfExists(RelationCompositeKey key) {
//        boolean relationExistsBeforeDelete = relationRepository.existsById(key);
//        if (relationExistsBeforeDelete) {
//            relationRepository.delete(key);
//        }
//        return relationExistsBeforeDelete;
//    }

    @Override
    public boolean deleteOutboundRelations(EntityId entity) {
        boolean relationExistsBeforeDelete = relationRepository
                .findAllByFromIdAndFromType(fromTimeUUID(entity.getId()), entity.getEntityType().name())
                .size() > 0;
        if (relationExistsBeforeDelete) {
            relationRepository.deleteByFromIdAndFromType(fromTimeUUID(entity.getId()), entity.getEntityType().name());
        }
        return relationExistsBeforeDelete;
    }

    @Override
    public ListenableFuture<Boolean> deleteOutboundRelationsAsync(EntityId entity) {
        return service.submit(
                () -> {
                    boolean relationExistsBeforeDelete = relationRepository
                            .findAllByFromIdAndFromType(fromTimeUUID(entity.getId()), entity.getEntityType().name())
                            .size() > 0;
                    if (relationExistsBeforeDelete) {
                        relationRepository.deleteByFromIdAndFromType(fromTimeUUID(entity.getId()), entity.getEntityType().name());
                    }
                    return relationExistsBeforeDelete;
                });
    }

    @Override
    public ListenableFuture<List<EntityRelation>> findRelations(EntityId from, String relationType, RelationTypeGroup typeGroup, EntityType childType, TimePageLink pageLink) {
        Specification<RelationEntity> timeSearchSpec = JpaAbstractSearchTimeDao.getTimeSearchPageSpec(pageLink, "toId");
        Specification<RelationEntity> fieldsSpec = getEntityFieldsSpec(from, relationType, typeGroup, childType);
        Sort.Direction sortDirection = pageLink.isAscOrder() ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = new PageRequest(0, pageLink.getLimit(), sortDirection, "toId");
        return service.submit(() ->
                DaoUtil.convertDataList(relationRepository.findAll(where(timeSearchSpec).and(fieldsSpec), pageable).getContent()));
    }

    private Specification<RelationEntity> getEntityFieldsSpec(EntityId from, String relationType, RelationTypeGroup typeGroup, EntityType childType) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (from != null) {
                Predicate fromIdPredicate = criteriaBuilder.equal(root.get("fromId"),  fromTimeUUID(from.getId()));
                predicates.add(fromIdPredicate);
                Predicate fromEntityTypePredicate = criteriaBuilder.equal(root.get("fromType"), from.getEntityType().name());
                predicates.add(fromEntityTypePredicate);
            }
            if (relationType != null) {
                Predicate relationTypePredicate = criteriaBuilder.equal(root.get("relationType"), relationType);
                predicates.add(relationTypePredicate);
            }
            if (typeGroup != null) {
                Predicate typeGroupPredicate = criteriaBuilder.equal(root.get("relationTypeGroup"), typeGroup.name());
                predicates.add(typeGroupPredicate);
            }
            if (childType != null) {
                Predicate childTypePredicate = criteriaBuilder.equal(root.get("toType"), childType.name());
                predicates.add(childTypePredicate);
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
