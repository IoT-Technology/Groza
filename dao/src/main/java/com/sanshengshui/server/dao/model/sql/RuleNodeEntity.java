package com.sanshengshui.server.dao.model.sql;

import com.datastax.driver.core.utils.UUIDs;
import com.fasterxml.jackson.databind.JsonNode;
import com.sanshengshui.server.common.data.id.RuleChainId;
import com.sanshengshui.server.common.data.id.RuleNodeId;
import com.sanshengshui.server.common.data.rule.RuleNode;
import com.sanshengshui.server.dao.DaoUtil;
import com.sanshengshui.server.dao.model.BaseSqlEntity;
import com.sanshengshui.server.dao.model.ModelConstants;
import com.sanshengshui.server.dao.model.SearchTextEntity;
import com.sanshengshui.server.dao.util.mapping.JsonStringType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author james mu
 * @date 19-2-25 上午11:12
 * @description
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@TypeDef(name = "json", typeClass = JsonStringType.class)
@Table(name = ModelConstants.RULE_NODE_COLUMN_FAMILY_NAME)
public class RuleNodeEntity extends BaseSqlEntity<RuleNode> implements SearchTextEntity<RuleNode> {

    @Column(name = ModelConstants.RULE_NODE_CHAIN_ID_PROPERTY)
    private String ruleChainId;

    @Column(name = ModelConstants.RULE_NODE_TYPE_PROPERTY)
    private String type;

    @Column(name = ModelConstants.RULE_NODE_NAME_PROPERTY)
    private String name;

    @Column(name = ModelConstants.SEARCH_TEXT_PROPERTY)
    private String searchText;

    @Type(type = "json")
    @Column(name = ModelConstants.RULE_NODE_CONFIGURATION_PROPERTY)
    private JsonNode configuration;

    @Type(type = "json")
    @Column(name = ModelConstants.ADDITIONAL_INFO_PROPERTY)
    private JsonNode additionalInfo;

    @Column(name = ModelConstants.DEBUG_MODE)
    private boolean debugMode;

    public RuleNodeEntity() {
    }

    public RuleNodeEntity(RuleNode ruleNode) {
        if (ruleNode.getId() != null) {
            this.setId(ruleNode.getUuidId());
        }
        if (ruleNode.getRuleChainId() != null) {
            this.ruleChainId = toString(DaoUtil.getId(ruleNode.getRuleChainId()));
        }
        this.type = ruleNode.getType();
        this.name = ruleNode.getName();
        this.debugMode = ruleNode.isDebugMode();
        this.searchText = ruleNode.getName();
        this.configuration = ruleNode.getConfiguration();
        this.additionalInfo = ruleNode.getAdditionalInfo();
    }

    @Override
    public String getSearchTextSource() {
        return searchText;
    }

    @Override
    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    @Override
    public RuleNode toData() {
        RuleNode ruleNode = new RuleNode(new RuleNodeId(getId()));
        ruleNode.setCreatedTime(UUIDs.unixTimestamp(getId()));
        if (ruleChainId != null) {
            ruleNode.setRuleChainId(new RuleChainId(toUUID(ruleChainId)));
        }
        ruleNode.setType(type);
        ruleNode.setName(name);
        ruleNode.setDebugMode(debugMode);
        ruleNode.setConfiguration(configuration);
        ruleNode.setAdditionalInfo(additionalInfo);
        return ruleNode;
    }
}
