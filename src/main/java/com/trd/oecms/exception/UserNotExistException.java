package com.trd.oecms.exception;

/**
 * @author Trd
 * @date 2020-04-06 23:09
 */
public class UserNotExistException extends Exception {

	private static final long serialVersionUID = 1L;

	public UserNotExistException() {
		super();
	}

	public UserNotExistException(String message) {
		super(message);
	}
}
