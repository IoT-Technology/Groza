package com.sanshengshui.server.common.data;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * @author james mu
 * @date 18-12-13 下午4:42
 */
public interface HasAdditionalInfo {

    JsonNode getAdditionalInfo();
}
