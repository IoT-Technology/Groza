package com.sanshengshui.server.common.msg.session;

import com.sanshengshui.server.common.msg.TbActorMsg;
import com.sanshengshui.server.common.msg.aware.CustomerAwareMsg;
import com.sanshengshui.server.common.msg.aware.DeviceAwareMsg;
import com.sanshengshui.server.common.msg.aware.SessionAwareMsg;
import com.sanshengshui.server.common.msg.aware.TenantAwareMsg;

/**
 * @author james mu
 * @date 19-1-22 上午9:26
 */
public interface TransportToDeviceSessionActorMsg extends DeviceAwareMsg, CustomerAwareMsg, TenantAwareMsg, SessionAwareMsg, TbActorMsg {

    AdaptorToSessionActorMsg getSessionMsg();

}
