package com.sanshengshui.server.common.msg.aware;

import com.sanshengshui.server.common.data.id.SessionId;

/**
 * @author james mu
 * @date 19-1-21 下午4:12
 * @description 会话信息
 */
public interface SessionAwareMsg {

    SessionId getSessionId();
}
