package com.sanshengshui.server.common.msg.session;

import com.sanshengshui.server.common.data.id.SessionId;

/**
 * @author james
 * @date 2019/1/21 19:47
 */
public class BasicSessionMsg implements SessionMsg{

    private final SessionContext ctx;

    public BasicSessionMsg(SessionContext ctx) {
        super();
        this.ctx = ctx;
    }

    @Override
    public SessionId getSessionId() {
        return ctx.getSessionId();
    }

    @Override
    public SessionContext getSessionContext() {
        return ctx;
    }

    @Override
    public String toString() {
        return "BasicSessionMsg [ctx=" + ctx + "]";
    }
}
