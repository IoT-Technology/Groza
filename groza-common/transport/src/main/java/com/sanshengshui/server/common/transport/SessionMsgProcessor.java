package com.sanshengshui.server.common.transport;

import com.sanshengshui.server.common.data.Device;
import com.sanshengshui.server.common.msg.aware.SessionAwareMsg;

/**
 * @author james mu
 * @date 19-1-21 下午4:36
 */
public interface SessionMsgProcessor {

    void process(SessionAwareMsg msg);

    void onDeviceAdded(Device device);
}
