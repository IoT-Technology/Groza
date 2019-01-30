package com.sanshengshui.server.dao.model.sql;

import com.sanshengshui.server.common.data.EntityType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Transient;
import java.io.Serializable;

/**
 * @author james mu
 * @date 19-1-30 下午4:47
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TsKvCompositeKey implements Serializable {

    @Transient
    public static final long serialVersionUID = -4089175869616037523L;

    private EntityType entityType;
    private String entityId;
    private String key;
    private long ts;
}
