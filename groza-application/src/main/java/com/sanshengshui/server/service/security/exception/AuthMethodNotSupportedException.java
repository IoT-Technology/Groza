package com.sanshengshui.server.service.security.exception;

import org.springframework.security.authentication.AuthenticationServiceException;

/**
 * @author james mu
 * @date 19-3-18 下午6:24
 * @description
 */
public class AuthMethodNotSupportedException extends AuthenticationServiceException {

    private static final long serialVersionUID = 3705043083010304496L;

    public AuthMethodNotSupportedException(String msg) {
        super(msg);
    }
}
