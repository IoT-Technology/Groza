package com.sanshengshui.server.common.data.rule;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import com.sanshengshui.server.common.data.HasName;
import com.sanshengshui.server.common.data.HasTenantId;
import com.sanshengshui.server.common.data.SearchTextBasedWithAdditionalInfo;
import com.sanshengshui.server.common.data.id.RuleChainId;
import com.sanshengshui.server.common.data.id.RuleNodeId;
import com.sanshengshui.server.common.data.id.TenantId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * @author james mu
 * @date 19-2-25 上午10:49
 * @description
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Slf4j
public class RuleChain extends SearchTextBasedWithAdditionalInfo<RuleChainId> implements HasName, HasTenantId {

    public static final long serialVersionUID = -5656679015121935465L;

    /**
     * 租户编号
     */
    private TenantId tenantId;
    /**
     * 名称
     */
    private String name;
    /**
     * 第一个规则节点编号
     */
    private RuleNodeId firstRuleNodeId;
    /**
     * 是否根节点
     */
    private boolean root;
    /**
     * 是否debug模式
     */
    private boolean debugMode;
    /**
     * 配置
     */
    private transient JsonNode configuration;
    @JsonIgnore
    private byte[] configurationBytes;

    public RuleChain() {
        super();
    }

    public RuleChain(RuleChainId id) {
        super(id);
    }

    public RuleChain(RuleChain ruleChain) {
        super(ruleChain);
        this.tenantId = ruleChain.getTenantId();
        this.name = ruleChain.getName();
        this.firstRuleNodeId = ruleChain.getFirstRuleNodeId();
        this.root = ruleChain.isRoot();
        this.setConfiguration(ruleChain.getConfiguration());
    }

    @Override
    public String getSearchText() {
        return getName();
    }

    @Override
    public String getName() {
        return name;
    }

    public JsonNode getConfiguration() {
        return SearchTextBasedWithAdditionalInfo.getJson(() -> configuration, () -> configurationBytes);
    }

    public void setConfiguration(JsonNode data) {
        setJson(data, json -> this.configuration = json, bytes -> this.configurationBytes = bytes);
    }

}
