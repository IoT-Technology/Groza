package iot.technology.server.transport.http.session;

import iot.technology.server.common.data.id.SessionId;

import java.util.UUID;

/**
 * @author james mu
 * @date 19-1-2 上午11:22
 */
public class HttpSessionId implements SessionId {

    private final UUID id;

    public HttpSessionId(){
        this.id = UUID.randomUUID();
    }

    @Override
    public String toUidStr() {
        return id.toString();
    }
}
