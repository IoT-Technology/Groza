package com.sanshengshui.server.common.msg.core;

import com.sanshengshui.server.common.data.kv.KvEntry;
import com.sanshengshui.server.common.msg.session.FromDeviceRequestMsg;

import java.util.List;
import java.util.Map;

/**
 * @author james mu
 * @date 18-12-7 下午4:16
 */
public interface TelemetryUploadRequest extends FromDeviceRequestMsg {
    Map<Long, List<KvEntry>> getData();
}
