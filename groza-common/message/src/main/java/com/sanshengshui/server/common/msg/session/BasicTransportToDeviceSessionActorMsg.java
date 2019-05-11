package com.sanshengshui.server.common.msg.session;

import com.sanshengshui.server.common.data.Device;
import com.sanshengshui.server.common.data.id.CustomerId;
import com.sanshengshui.server.common.data.id.DeviceId;
import com.sanshengshui.server.common.data.id.SessionId;
import com.sanshengshui.server.common.data.id.TenantId;
import com.sanshengshui.server.common.msg.MsgType;

/**
 * @author james mu
 * @date 19-1-22 上午9:24
 */
public class BasicTransportToDeviceSessionActorMsg implements TransportToDeviceSessionActorMsg{

    private final TenantId tenantId;
    private final CustomerId customerId;
    private final DeviceId deviceId;
    private final AdaptorToSessionActorMsg msg;

    public BasicTransportToDeviceSessionActorMsg(Device device, AdaptorToSessionActorMsg msg){
        super();
        this.tenantId = device.getTenantId();
        this.customerId = device.getCustomerId();
        this.deviceId = device.getId();
        this.msg = msg;
    }

    @Override
    public DeviceId getDeviceId() {
        return deviceId;
    }

    @Override
    public CustomerId getCustomerId() {
        return customerId;
    }

    public TenantId getTenantId() {
        return tenantId;
    }

    @Override
    public SessionId getSessionId() {
        return msg.getSessionId();
    }

    @Override
    public AdaptorToSessionActorMsg getSessionMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "BasicTransportToDeviceSessionActorMsg [tenantId=" + tenantId + ", customerId=" + customerId + ", deviceId=" + deviceId + ", msg=" + msg
                + "]";
    }

    @Override
    public MsgType getMsgType() {
        return MsgType.TRANSPORT_TO_DEVICE_SESSION_ACTOR_MSG;
    }
}
