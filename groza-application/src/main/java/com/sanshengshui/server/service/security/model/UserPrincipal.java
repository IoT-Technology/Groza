package com.sanshengshui.server.service.security.model;

import java.io.Serializable;

/**
 * @author james mu
 * @date 19-1-25 上午9:20
 */
public class UserPrincipal implements Serializable {

    private final Type type;
    private final String value;

    public UserPrincipal(Type type, String value) {
        this.type = type;
        this.value = value;
    }

    public Type getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public enum Type {
        USER_NAME,
        PUBLIC_ID
    }
}
