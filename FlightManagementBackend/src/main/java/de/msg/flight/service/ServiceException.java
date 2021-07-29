package de.msg.flight.service;

public class ServiceException extends Exception {

	private static final long serialVersionUID = 7730566377985503131L;

	public ServiceException(final String message) {
		super(message);
	}

	public ServiceException(final String message, final Throwable cause) {
		super(message, cause);
	}

}
