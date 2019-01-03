package com.sanshengshui.server.common.data.id;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sanshengshui.server.common.data.EntityType;

import java.util.UUID;

/**
 * @author james mu
 * @date 19-1-3 下午5:01
 */
public class AssetId extends UUIDBased implements EntityId {

    public static final long serialVersionUID = 1L;

    @JsonCreator
    public AssetId(@JsonProperty("id") UUID id){
        super(id);
    }

    public static AssetId fromString(String assetId){
        return new AssetId(UUID.fromString(assetId));
    }

    @JsonIgnore
    @Override
    public EntityType getEntityType(){
        return EntityType.ASSET;
    }

}
