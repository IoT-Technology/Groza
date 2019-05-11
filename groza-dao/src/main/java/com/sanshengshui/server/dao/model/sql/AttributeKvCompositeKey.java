package com.sanshengshui.server.dao.model.sql;

import com.sanshengshui.server.common.data.EntityType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author james mu
 * @date 18-12-13 上午10:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttributeKvCompositeKey implements Serializable {
    private EntityType entityType;
    private String entityId;
    private String attributeType;
    private String attributeKey;
}
