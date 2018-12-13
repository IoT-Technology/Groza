package com.sanshengshui.server.dao.model;

/**
 * @author james mu
 * @date 18-12-13 上午10:12
 */
public interface ToData<T> {
    /**
     * This method convert domain model object to data transfer object
     *
     * @return the dto object
     */
    T toData();
}
