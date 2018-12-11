package com.sanshengshui.server.dao.exception;

/**
 * @author james mu
 * @date 18-12-11 下午1:28
 */
public class IncorrectParameterException extends RuntimeException{
    private static final long serialVersionUID = 601995650578985289L;

    public IncorrectParameterException(String message) {
        super(message);
    }

    public IncorrectParameterException(String message, Throwable cause) {
        super(message, cause);
    }
}
