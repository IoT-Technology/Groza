package com.sanshengshui.server.common.msg.session;

import com.sanshengshui.server.common.msg.aware.SessionAwareMsg;

/**
 * @author james mu
 * @date 19-1-21 下午4:19
 */
public interface SessionMsg extends SessionAwareMsg {

    SessionContext getSessionContext();

}
