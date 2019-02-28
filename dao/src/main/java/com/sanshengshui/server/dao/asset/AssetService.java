package com.sanshengshui.server.dao.asset;

import com.google.common.util.concurrent.ListenableFuture;
import com.sanshengshui.server.common.data.EntitySubtype;
import com.sanshengshui.server.common.data.asset.Asset;
import com.sanshengshui.server.common.data.asset.AssetSearchQuery;
import com.sanshengshui.server.common.data.id.AssetId;
import com.sanshengshui.server.common.data.id.CustomerId;
import com.sanshengshui.server.common.data.id.TenantId;
import com.sanshengshui.server.common.data.page.TextPageData;
import com.sanshengshui.server.common.data.page.TextPageLink;

import java.util.List;
import java.util.Optional;

/**
 * @author james mu
 * @date 19-1-8 下午4:17
 */
public interface AssetService {

    Asset findAssetById(AssetId assetId);

    ListenableFuture<Asset> findAssetByIdAsync(AssetId assetId);

    Optional<Asset> findAssetByTenantIdAndName(TenantId tenantId, String name);

    Asset saveAsset(Asset asset);

    Asset assignAssetToCustomer(AssetId assetId, CustomerId customerId);

    Asset unassignAssetFromCustomer(AssetId assetId);

    void deleteAsset(AssetId assetId);

    TextPageData<Asset> findAssetsByTenantId(TenantId tenantId, TextPageLink pageLink);

    TextPageData<Asset> findAssetsByTenantIdAndType(TenantId tenantId, String type, TextPageLink pageLink);

    ListenableFuture<List<Asset>> findAssetsByTenantIdAndIdsAsync(TenantId tenantId, List<AssetId> assetIds);

    void deleteAssetsByTenantId(TenantId tenantId);

    TextPageData<Asset> findAssetsByTenantIdAndCustomerId(TenantId tenantId, CustomerId customerId, TextPageLink pageLink);

    TextPageData<Asset> findAssetsByTenantIdAndCustomerIdAndType(TenantId tenantId, CustomerId customerId, String type, TextPageLink pageLink);

    ListenableFuture<List<Asset>> findAssetsByTenantIdCustomerIdAndIdsAsync(TenantId tenantId, CustomerId customerId, List<AssetId> assetIds);

    void unassignCustomerAssets(TenantId tenantId, CustomerId customerId);

    ListenableFuture<List<Asset>> findAssetsByQuery(AssetSearchQuery query);

    ListenableFuture<List<EntitySubtype>> findAssetTypesByTenantId(TenantId tenantId);
}
