package com.sanshengshui.server.common.msg.core;

import com.sanshengshui.server.common.msg.session.FromDeviceMsg;
import com.sanshengshui.server.common.msg.session.SessionMsgType;
import lombok.Data;

/**
 * @author james mu
 * @date 19-1-23 上午10:35
 */
@Data
public class SessionOpenMsg implements FromDeviceMsg {

    @Override
    public SessionMsgType getMsgType() {
        return SessionMsgType.SESSION_OPEN;
    }
}
