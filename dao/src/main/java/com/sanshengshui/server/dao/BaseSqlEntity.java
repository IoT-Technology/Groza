package com.sanshengshui.server.dao;

import com.sanshengshui.server.common.data.UUIDConverter;
import com.sanshengshui.server.dao.model.BaseEntity;
import lombok.Data;

import java.util.UUID;

/**
 * @author james mu
 * @date 18-12-13 下午3:57
 */
@Data
public abstract class BaseSqlEntity<D> implements BaseEntity<D> {

    protected  String id;

    @Override
    public UUID getId() {
        if (id == null){
            return null;
        }
        return UUIDConverter.fromString(id);
    }


    @Override
    public void setId(UUID id) {
        this.id = UUIDConverter.fromTimeUUID(id);
    }

    protected UUID toUUID(String src){
        return UUIDConverter.fromString(src);
    }

    protected String toString(UUID timeUUID){
        return UUIDConverter.fromTimeUUID(timeUUID);
    }
}
