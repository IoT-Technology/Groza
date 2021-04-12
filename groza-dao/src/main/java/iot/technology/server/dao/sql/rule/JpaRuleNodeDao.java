package iot.technology.server.dao.sql.rule;

import iot.technology.server.common.data.rule.RuleNode;
import iot.technology.server.dao.model.sql.RuleNodeEntity;
import iot.technology.server.dao.rule.RuleNodeDao;
import iot.technology.server.dao.sql.JpaAbstractSearchTextDao;
import iot.technology.server.dao.util.SqlDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

/**
 * @author james mu
 * @date 19-2-25 上午11:11
 * @description
 */
@Slf4j
@Component
@SqlDao
public class JpaRuleNodeDao extends JpaAbstractSearchTextDao<RuleNodeEntity, RuleNode> implements RuleNodeDao {

    @Autowired
    private RuleNodeRepository ruleNodeRepository;

    @Override
    protected Class getEntityClass() {
        return RuleNodeEntity.class;
    }

    @Override
    protected CrudRepository getCrudRepository() {
        return ruleNodeRepository;
    }
}
