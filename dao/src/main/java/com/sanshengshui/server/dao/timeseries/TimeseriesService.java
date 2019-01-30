package com.sanshengshui.server.dao.timeseries;

import com.google.common.util.concurrent.ListenableFuture;
import com.sanshengshui.server.common.data.id.EntityId;
import com.sanshengshui.server.common.data.kv.TsKvEntry;
import com.sanshengshui.server.common.data.kv.TsKvQuery;

import java.util.Collection;
import java.util.List;

/**
 * @author james mu
 * @date 19-1-30 下午5:09
 * @description
 */
public interface TimeseriesService {

    ListenableFuture<List<TsKvEntry>> findAll(EntityId entityId, List<TsKvQuery> queries);

    ListenableFuture<List<TsKvEntry>> findLatest(EntityId entityId, Collection<String> keys);

    ListenableFuture<List<TsKvEntry>> findAllLatest(EntityId entityId);

    ListenableFuture<List<Void>> save(EntityId entityId, TsKvEntry tsKvEntry);

    ListenableFuture<List<Void>> save(EntityId entityId, List<TsKvEntry> tsKvEntry, long ttl);
}
