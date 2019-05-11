package com.sanshengshui.server.dao.exception;

/**
 * @author james mu
 * @date 19-1-3 上午11:45
 */
public class DataValidationException extends RuntimeException{

    public static final long serialVersionUID = 7659985660312721830L;

    public DataValidationException(String message){
        super(message);
    }

    public DataValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
