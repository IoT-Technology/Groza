package com.sanshengshui.server.common.transport.adaptor;

/**
 * @author james mu
 * @date 19-1-21 下午4:02
 */
public class AdaptorException extends Exception{

    private static final long serialVersionUID = 1L;

    public AdaptorException(){
        super();
    }

    public AdaptorException(String cause) {
        super(cause);
    }

    public AdaptorException(Exception cause) {
        super(cause);
    }
}
