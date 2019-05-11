package com.sanshengshui.server.service.state;

import com.sanshengshui.server.common.data.id.DeviceId;
import com.sanshengshui.server.common.data.id.TenantId;
import lombok.Builder;
import lombok.Data;

/**
 * @Author: 穆书伟
 * @Date: 19-4-8 下午1:33
 * @Version 1.0
 */
@Data
@Builder
public class DeviceStateData {

    private final TenantId tenantId;
    private final DeviceId deviceId;

    private final DeviceState state;
}
