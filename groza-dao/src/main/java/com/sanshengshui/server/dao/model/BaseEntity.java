package com.sanshengshui.server.dao.model;

import java.util.UUID;

/**
 * @author james mu
 * @date 18-12-13 下午3:53
 */
public interface BaseEntity<D> extends ToData<D> {

    UUID getId();

    void setId(UUID id);
}
