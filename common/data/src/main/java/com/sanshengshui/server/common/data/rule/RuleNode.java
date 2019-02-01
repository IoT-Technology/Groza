/**
 * Copyright © 2016-2018 The Thingsboard Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sanshengshui.server.common.data.rule;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import com.sanshengshui.server.common.data.HasName;
import com.sanshengshui.server.common.data.SearchTextBasedWithAdditionalInfo;
import com.sanshengshui.server.common.data.id.RuleChainId;
import com.sanshengshui.server.common.data.id.RuleNodeId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * 规则节点表实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Slf4j
public class RuleNode extends SearchTextBasedWithAdditionalInfo<RuleNodeId> implements HasName {

    private static final long serialVersionUID = -5656679015121235465L;
    /**
     * 规则链编号
     */
    private RuleChainId ruleChainId;
    /**
     * 类型
     */
    private String type;
    /**
     * 名称
     */
    private String name;
    /**
     * 是否DEBUG
     */
    private boolean debugMode;
    /**
     * 配置
     */
    private transient JsonNode configuration;
    @JsonIgnore
    private byte[] configurationBytes;

    public RuleNode() {
        super();
    }

    public RuleNode(RuleNodeId id) {
        super(id);
    }

    public RuleNode(RuleNode ruleNode) {
        super(ruleNode);
        this.ruleChainId = ruleNode.getRuleChainId();
        this.type = ruleNode.getType();
        this.name = ruleNode.getName();
        this.debugMode = ruleNode.isDebugMode();
        this.setConfiguration(ruleNode.getConfiguration());
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
