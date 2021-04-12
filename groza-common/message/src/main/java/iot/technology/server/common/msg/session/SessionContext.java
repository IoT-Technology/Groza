package iot.technology.server.common.msg.session;

import iot.technology.server.common.msg.aware.SessionAwareMsg;
import iot.technology.server.common.msg.session.ex.SessionException;

/**
 * @author james mu
 * @date 19-1-21 下午4:15
 */
public interface SessionContext extends SessionAwareMsg {

    SessionType getSessionType();

    void onMsg(SessionActorToAdaptorMsg msg) throws SessionException;

    void onMsg(SessionCtrlMsg msg) throws SessionException;

    boolean isClosed();

    long getTimeout();

}
