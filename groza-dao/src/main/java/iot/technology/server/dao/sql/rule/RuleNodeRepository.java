package iot.technology.server.dao.sql.rule;

import iot.technology.server.dao.model.sql.RuleNodeEntity;
import iot.technology.server.dao.util.SqlDao;
import org.springframework.data.repository.CrudRepository;

/**
 * @author james mu
 * @date 19-2-25 上午11:18
 * @description
 */
@SqlDao
public interface RuleNodeRepository extends CrudRepository<RuleNodeEntity,String> {
}
