package com.sanshengshui.server.common.msg.core;

import com.sanshengshui.server.common.data.kv.AttributeKvEntry;
import com.sanshengshui.server.common.msg.session.FromDeviceRequestMsg;

import java.util.Set;

/**
 * @author james mu
 * @date 18-12-6 下午4:30
 */
public interface AttributesUpdateRequest extends FromDeviceRequestMsg {

    Set<AttributeKvEntry> getAttributes();
}
