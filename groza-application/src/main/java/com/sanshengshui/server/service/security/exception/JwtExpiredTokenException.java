package com.sanshengshui.server.service.security.exception;

import com.sanshengshui.server.service.security.model.token.JwtToken;
import org.springframework.security.core.AuthenticationException;


/**
 * @author james mu
 * @date 19-3-18 下午6:24
 * @description
 */
public class JwtExpiredTokenException extends AuthenticationException {
    private static final long serialVersionUID = -5959543783324224864L;

    private JwtToken token;

    public JwtExpiredTokenException(String msg) {
        super(msg);
    }

    public JwtExpiredTokenException(JwtToken token, String msg, Throwable t) {
        super(msg, t);
        this.token = token;
    }

    public String token() {
        return this.token.getToken();
    }
}
