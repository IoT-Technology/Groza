package com.sanshengshui.server.common.msg.session;

import java.io.Serializable;

/**
 * @author james mu
 * @date 18-12-6 下午4:27
 */
public interface FromDeviceMsg extends Serializable {
    SessionMsgType getMsgType();
}
