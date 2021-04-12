package iot.technology.server.common.transport;

import iot.technology.server.common.data.Device;
import iot.technology.server.common.msg.aware.SessionAwareMsg;

/**
 * @author james mu
 * @date 19-1-21 下午4:36
 */
public interface SessionMsgProcessor {

    void process(SessionAwareMsg msg);

    void onDeviceAdded(Device device);
}
