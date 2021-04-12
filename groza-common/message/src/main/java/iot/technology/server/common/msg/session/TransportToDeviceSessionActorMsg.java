package iot.technology.server.common.msg.session;

import iot.technology.server.common.msg.TbActorMsg;
import iot.technology.server.common.msg.aware.CustomerAwareMsg;
import iot.technology.server.common.msg.aware.DeviceAwareMsg;
import iot.technology.server.common.msg.aware.SessionAwareMsg;
import iot.technology.server.common.msg.aware.TenantAwareMsg;

/**
 * @author james mu
 * @date 19-1-22 上午9:26
 */
public interface TransportToDeviceSessionActorMsg extends DeviceAwareMsg, CustomerAwareMsg, TenantAwareMsg, SessionAwareMsg, TbActorMsg {

    AdaptorToSessionActorMsg getSessionMsg();

}
