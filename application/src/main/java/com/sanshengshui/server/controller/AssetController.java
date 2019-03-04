package com.sanshengshui.server.controller;

import com.sanshengshui.server.common.data.asset.Asset;
import com.sanshengshui.server.common.data.exception.GrozaException;
import com.sanshengshui.server.common.data.page.TextPageData;
import org.springframework.web.bind.annotation.*;

/**
 * @author james mu
 * @date 19-1-31 上午10:35
 * @description
 */
@RestController
@RequestMapping
public class AssetController extends BaseController{

    public static final String ASSET_ID = "assetId";

    @RequestMapping(value = "/asset", method = RequestMethod.POST)
    @ResponseBody
    public Asset saveAsset(@RequestBody Asset asset){
        return asset;
    }
}
