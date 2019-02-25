package com.sanshengshui.server.dao.sql.rule;

import com.sanshengshui.server.dao.model.sql.RuleNodeEntity;
import com.sanshengshui.server.dao.util.SqlDao;
import org.springframework.data.repository.CrudRepository;

/**
 * @author james mu
 * @date 19-2-25 上午11:18
 * @description
 */
@SqlDao
public interface RuleNodeRepository extends CrudRepository<RuleNodeEntity,String> {
}
