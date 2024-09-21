package com.itheima.restkeeper.exception;

import com.itheima.restkeeper.basic.IBasicEnum;
import org.springframework.security.authentication.BadCredentialsException;

/**
 * @Description:
 * @Version: V1.0
 */
public class AuthCodeException extends BadCredentialsException {

    private IBasicEnum basicEnum;

    public AuthCodeException(IBasicEnum basicEnum) {
        super(basicEnum.getMsg());
        this.basicEnum = basicEnum;
    }

    /**
     * @param msg the detail message
     */
    public AuthCodeException(String msg) {
        super(msg);
    }

    /**
     * @param msg the detail message
     * @param t   root cause
     */
    public AuthCodeException(String msg, Throwable t) {
        super(msg, t);
    }

    public IBasicEnum getBasicEnum() {
        return basicEnum;
    }

    public void setBasicEnum(IBasicEnum basicEnum) {
        this.basicEnum = basicEnum;
    }
}
