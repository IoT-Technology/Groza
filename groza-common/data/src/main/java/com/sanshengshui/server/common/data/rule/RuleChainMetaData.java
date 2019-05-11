package com.sanshengshui.server.common.data.rule;

import com.fasterxml.jackson.databind.JsonNode;
import com.sanshengshui.server.common.data.id.RuleChainId;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author james mu
 * @date 19-2-1 下午3:31
 * @description
 */
@Data
public class RuleChainMetaData {

    private RuleChainId ruleChainId;

    private Integer firstNodeIndex;

    private List<RuleNode> nodes;

    private List<NodeConnectionInfo> connections;

    private List<RuleChainConnectionInfo> ruleChainConnections;

    public void addConnectionInfo(int fromIndex, int toIndex, String type) {
        NodeConnectionInfo connectionInfo = new NodeConnectionInfo();
        connectionInfo.setFromIndex(fromIndex);
        connectionInfo.setToIndex(toIndex);
        connectionInfo.setType(type);
        if (connections == null) {
            connections = new ArrayList<>();
        }
        connections.add(connectionInfo);
    }
    public void addRuleChainConnectionInfo(int fromIndex, RuleChainId targetRuleChainId, String type, JsonNode additionalInfo) {
        RuleChainConnectionInfo connectionInfo = new RuleChainConnectionInfo();
        connectionInfo.setFromIndex(fromIndex);
        connectionInfo.setTargetRuleChainId(targetRuleChainId);
        connectionInfo.setType(type);
        connectionInfo.setAdditionalInfo(additionalInfo);
        if (ruleChainConnections == null) {
            ruleChainConnections = new ArrayList<>();
        }
        ruleChainConnections.add(connectionInfo);
    }
}
