package com.sanshengshui.server.transport.coap.session;

import com.sanshengshui.server.common.data.id.SessionId;

/**
 * @author james mu
 * @date 19-1-2 下午1:08
 */
public class CoapSessionId implements SessionId {

    private final String clientAddress;
    private final int clientPort;
    private final String token;

    public CoapSessionId(String host,int port,String token){
        super();
        this.clientAddress = host;
        this.clientPort = port;
        this.token = token;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }
        if (obj == null){
            return false;
        }
        if (getClass() != obj.getClass())
            return false;
        CoapSessionId other = (CoapSessionId) obj;
        if (clientAddress == null){
            if (other.clientAddress != null)
                return false;
        } else if (!clientAddress.equals(other.clientAddress))
            return false;
        if (clientPort != other.clientPort)
            return false;
        if (token == null){
            if (other.token != null)
                return false;
        } else if (!token.equals(other.token))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((clientAddress == null) ? 0 : clientAddress.hashCode());
        result = prime * result + clientPort;
        result = prime * result + ((token == null) ? 0 : token.hashCode());
        return result;
    }

    @Override
    public String toUidStr() {
        return clientAddress + ":" + clientPort + ":" + token;
    }

    @Override
    public String toString() {
        return "CoapSessionId [clientAddress=" + clientAddress + ",clientPort=" + clientPort + ",token=" + token + "]";
    }
}
