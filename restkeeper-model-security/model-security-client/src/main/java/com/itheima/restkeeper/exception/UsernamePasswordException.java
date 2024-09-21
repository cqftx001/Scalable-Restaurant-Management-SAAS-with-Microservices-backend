package com.itheima.restkeeper.exception;

import org.springframework.security.authentication.BadCredentialsException;

/**
 * @Description:
 * @Version: V1.0
 */
public class UsernamePasswordException extends BadCredentialsException {
    /**
     * @param msg the detail message
     */
    public UsernamePasswordException(String msg) {
        super(msg);
    }

    /**
     * @param msg the detail message
     * @param t   root cause
     */
    public UsernamePasswordException(String msg, Throwable t) {
        super(msg, t);
    }
}
