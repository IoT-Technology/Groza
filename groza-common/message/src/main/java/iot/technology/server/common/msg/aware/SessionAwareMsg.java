package iot.technology.server.common.msg.aware;

import iot.technology.server.common.data.id.SessionId;

/**
 * @author james mu
 * @date 19-1-21 下午4:12
 * @description 会话信息
 */
public interface SessionAwareMsg {

    SessionId getSessionId();
}
