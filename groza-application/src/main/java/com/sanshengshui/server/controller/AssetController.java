package com.sanshengshui.server.controller;

import com.sanshengshui.server.common.data.EntityType;
import com.sanshengshui.server.common.data.asset.Asset;
import com.sanshengshui.server.common.data.audit.ActionType;
import com.sanshengshui.server.common.data.exception.GrozaErrorCode;
import com.sanshengshui.server.common.data.exception.GrozaException;
import com.sanshengshui.server.common.data.id.AssetId;
import com.sanshengshui.server.common.data.page.TextPageData;
import com.sanshengshui.server.common.data.security.Authority;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author james mu
 * @date 19-1-31 上午10:35
 * @description
 */
@RestController
@RequestMapping("/api")
public class AssetController extends BaseController{

    public static final String ASSET_ID = "assetId";

    @PreAuthorize("hasAnyAuthority('TENANT_ADMIN', 'CUSTOMER_USER')")
    @RequestMapping(value = "/asset/{assetId}", method = RequestMethod.GET)
    @ResponseBody
    public Asset getAssetById(@PathVariable(ASSET_ID) String strAssetId) throws GrozaException {
        checkParameter(ASSET_ID, strAssetId);
        try {
            AssetId assetId = new AssetId(toUUID(strAssetId));
            return checkAssetId(assetId);
        } catch (Exception e){
            throw handleException(e);
        }

    }

    @PreAuthorize("hasAnyAuthority('TENANT_ADMIN', 'CUSTOMER_USER')")
    @RequestMapping(value = "/asset", method = RequestMethod.POST)
    @ResponseBody
    public Asset saveAsset(@RequestBody Asset asset) throws GrozaException {
        asset.setTenantId(getCurrentUser().getTenantId());
        if (getCurrentUser().getAuthority() == Authority.CUSTOMER_USER) {
            if (asset.getId() == null || asset.getId().isNullUid() ||
                    asset.getCustomerId() == null || asset.getCustomerId().isNullUid()) {
                throw new GrozaException("You don't have permission to perform this operation!",
                        GrozaErrorCode.PERMISSION_DENIED);
            } else {
                checkCustomerId(asset.getCustomerId());
            }
        }
        Asset savedAsset = checkNotNull(assetService.saveAsset(asset));
        return savedAsset;
    }

}
