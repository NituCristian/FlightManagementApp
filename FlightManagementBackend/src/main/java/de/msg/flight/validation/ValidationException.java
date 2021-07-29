package de.msg.flight.validation;

public class ValidationException extends Exception {

	private static final long serialVersionUID = 9109193114903135652L;

	public ValidationException(final String message) {
		super(message);
	}

	public ValidationException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
