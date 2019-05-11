package com.sanshengshui.server.dao;


import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;
import java.util.UUID;

/**
 * @author james mu
 * @date 18-12-11 上午9:52
 */
public interface Dao<T> {

    List<T> find();

    T findById(UUID id);

    ListenableFuture<T> findByIdAsync(UUID id);

    T save(T t);

    boolean removeById(UUID id);


}
