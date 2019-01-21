package com.sanshengshui.server.common.msg.session;

import java.io.Serializable;

/**
 * @author james mu
 * @date 19-1-21 下午4:21
 */
public interface ToDeviceMsg extends Serializable {

    boolean isSuccess();

    SessionMsgType getSessionMsgType();
}
