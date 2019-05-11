package com.sanshengshui.server.dao.rule;

import com.google.common.util.concurrent.ListenableFuture;
import com.sanshengshui.server.common.data.id.RuleChainId;
import com.sanshengshui.server.common.data.id.RuleNodeId;
import com.sanshengshui.server.common.data.id.TenantId;
import com.sanshengshui.server.common.data.page.TextPageData;
import com.sanshengshui.server.common.data.page.TextPageLink;
import com.sanshengshui.server.common.data.relation.EntityRelation;
import com.sanshengshui.server.common.data.rule.RuleChain;
import com.sanshengshui.server.common.data.rule.RuleChainMetaData;
import com.sanshengshui.server.common.data.rule.RuleNode;

import java.util.List;

/**
 * @author james mu
 * @date 19-2-25 上午10:54
 * @description
 */
public interface RuleChainService {

    RuleChain saveRuleChain(RuleChain ruleChain);

    boolean setRootRuleChain(RuleChainId ruleChainId);

    RuleChainMetaData saveRuleChainMetaData(RuleChainMetaData ruleChainMetaData);

    RuleChainMetaData loadRuleChainMetaData(RuleChainId ruleChainId);

    RuleChain findRuleChainById(RuleChainId ruleChainId);

    RuleNode findRuleNodeById(RuleNodeId ruleNodeId);

    ListenableFuture<RuleChain> findRuleChainByIdAsync(RuleChainId ruleChainId);

    ListenableFuture<RuleNode> findRuleNodeByIdAsync(RuleNodeId ruleNodeId);

    RuleChain getRootTenantRuleChain(TenantId tenantId);

    List<RuleNode> getRuleChainNodes(RuleChainId ruleChainId);

    List<EntityRelation> getRuleNodeRelations(RuleNodeId ruleNodeId);

    TextPageData<RuleChain> findTenantRuleChains(TenantId tenantId, TextPageLink pageLink);

    void deleteRuleChainById(RuleChainId ruleChainId);

    void deleteRuleChainsByTenantId(TenantId tenantId);
}
