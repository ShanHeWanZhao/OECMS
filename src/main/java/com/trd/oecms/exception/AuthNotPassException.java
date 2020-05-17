package com.trd.oecms.exception;

/**
 * @author tanruidong
 * @date 2020-04-08 18:00
 */
public class AuthNotPassException extends Exception {
    private static final long serialVersionUID = 1L;

    public AuthNotPassException() {
    }

    public AuthNotPassException(String message) {
        super(message);
    }
}
