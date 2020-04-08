package com.trd.oecms.exception;

/**
 * @author tanruidong
 * @date 2020-04-08 18:00
 */
public class AuthNotPassException extends Exception {
    public AuthNotPassException() {
    }

    public AuthNotPassException(String message) {
        super(message);
    }
}
