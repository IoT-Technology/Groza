package com.sanshengshui.server.common.data.id;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

/**
 * @author james mu
 * @date 18-12-21 上午11:43
 */
public class DeviceCredentialsId extends UUIDBased {

    @JsonCreator
    public DeviceCredentialsId(@JsonProperty("id") UUID id){
        super(id);
    }
}
