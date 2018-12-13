package com.sanshengshui.server.dao.model.sql;

import com.sanshengshui.server.common.data.EntityType;
import com.sanshengshui.server.common.data.kv.*;
import com.sanshengshui.server.dao.model.ToData;
import lombok.Data;

import java.io.Serializable;

/**
 * @author james mu
 * @date 18-12-13 上午10:10
 */
@Data
public class AttributeKvEntity implements ToData<AttributeKvEntry>, Serializable {

    private EntityType entityType;
    private String entityId;
    private String attributeType;
    private String attributeKey;
    private Boolean booleanValue;
    private String strValue;
    private Long longValue;
    private Double doubleValue;
    private Long lastUpdateTs;

    @Override
    public AttributeKvEntry toData() {
        return null;
    }
}
