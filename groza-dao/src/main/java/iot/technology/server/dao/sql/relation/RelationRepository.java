package iot.technology.server.dao.sql.relation;

import iot.technology.server.dao.model.sql.RelationCompositeKey;
import iot.technology.server.dao.model.sql.RelationEntity;
import iot.technology.server.dao.util.SqlDao;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author james mu
 * @date 19-1-22 上午11:05
 */
@SqlDao
public interface RelationRepository
        extends CrudRepository<RelationEntity, RelationCompositeKey>, JpaSpecificationExecutor<RelationEntity> {

    List<RelationEntity> findAllByFromIdAndFromTypeAndRelationTypeGroup(String fromId,
                                                                        String fromType,
                                                                        String relationTypeGroup);

    List<RelationEntity> findAllByFromIdAndFromTypeAndRelationTypeAndRelationTypeGroup(String fromId,
                                                                                       String fromType,
                                                                                       String relationType,
                                                                                       String relationTypeGroup);

    List<RelationEntity> findAllByToIdAndToTypeAndRelationTypeGroup(String toId,
                                                                    String toType,
                                                                    String relationTypeGroup);

    List<RelationEntity> findAllByToIdAndToTypeAndRelationTypeAndRelationTypeGroup(String toId,
                                                                                   String toType,
                                                                                   String relationType,
                                                                                   String relationTypeGroup);

    List<RelationEntity> findAllByFromIdAndFromType(String fromId,
                                                    String fromType);

    @Transactional
    RelationEntity save(RelationEntity entity);

//    @Transactional
//    void delete(RelationCompositeKey id);

    @Transactional
    void deleteByFromIdAndFromType(String fromId, String fromType);
}
