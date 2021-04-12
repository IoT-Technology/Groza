package iot.technology.server.dao.rule;

import com.google.common.util.concurrent.ListenableFuture;
import iot.technology.server.common.data.id.RuleChainId;
import iot.technology.server.common.data.id.RuleNodeId;
import iot.technology.server.common.data.id.TenantId;
import iot.technology.server.common.data.page.TextPageData;
import iot.technology.server.common.data.page.TextPageLink;
import iot.technology.server.common.data.relation.EntityRelation;
import iot.technology.server.common.data.rule.RuleChain;
import iot.technology.server.common.data.rule.RuleChainMetaData;
import iot.technology.server.common.data.rule.RuleNode;

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
