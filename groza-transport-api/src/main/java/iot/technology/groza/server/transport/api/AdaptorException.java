package iot.technology.groza.server.transport.api;

/**
 * @author mushuwei
 */
public class AdaptorException extends Exception {

	private static final long serialVersionUID = 1L;

	public AdaptorException() {
		super();
	}

	public AdaptorException(String cause) {
		super(cause);
	}

	public AdaptorException(Exception cause) {
		super(cause);
	}

	public AdaptorException(String message, Exception cause) {
		super(message, cause);
	}
}
