package com.sanshengshui.server.service.state;

import lombok.Builder;
import lombok.Data;

/**
 * @Author: 穆书伟
 * @Date: 19-4-8 下午1:33
 * @Version 1.0
 */
@Data
@Builder
public class DeviceState {

    private boolean active;
    private long lastConnectTime;
    private long lastActivityTime;
    private long lastDisconnectTime;
    private long lastInactivityAlarmTime;
    private long inactivityTimeout;
}
