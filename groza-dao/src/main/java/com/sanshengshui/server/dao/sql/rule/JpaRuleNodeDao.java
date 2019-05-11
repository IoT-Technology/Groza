package com.sanshengshui.server.dao.sql.rule;

import com.sanshengshui.server.common.data.rule.RuleNode;
import com.sanshengshui.server.dao.model.sql.RuleNodeEntity;
import com.sanshengshui.server.dao.rule.RuleNodeDao;
import com.sanshengshui.server.dao.sql.JpaAbstractSearchTextDao;
import com.sanshengshui.server.dao.util.SqlDao;
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
