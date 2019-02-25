package com.sanshengshui.server.dao.rule;

import com.sanshengshui.server.common.data.page.TextPageLink;
import com.sanshengshui.server.common.data.rule.RuleChain;
import com.sanshengshui.server.dao.Dao;

import java.util.List;
import java.util.UUID;

/**
 * @author james mu
 * @date 19-2-25 上午10:48
 * @description
 */
public interface RuleChainDao extends Dao<RuleChain> {

    /**
     * Find rule chains by tenantId and page link.
     *
     * @param tenantId the tenantId
     * @param pageLink the page link
     * @return the list of rule chain objects
     */
    List<RuleChain> findRuleChainsByTenantId(UUID tenantId, TextPageLink pageLink);
}
