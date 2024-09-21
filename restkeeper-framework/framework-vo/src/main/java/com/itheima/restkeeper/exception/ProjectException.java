package com.itheima.restkeeper.exception;

import com.itheima.restkeeper.basic.IBasicEnum;

/**
 * @Description：自定义异常
 */
public class ProjectException extends RuntimeException {

    //错误编码
    private String code;

    //提示信息
    private String message;

    //异常接口
    private IBasicEnum basicEnumIntface;

    public ProjectException() {

    }
    public ProjectException(String code,String message) {
        this.code = code;
        this.message = message;
    }


    public ProjectException(IBasicEnum basicEnumIntface) {
        this.code = basicEnumIntface.getCode();
        this.message = basicEnumIntface.getMsg();
        this.basicEnumIntface = basicEnumIntface;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public IBasicEnum getBasicEnumIntface() {
        return basicEnumIntface;
    }

    public void setBasicEnumIntface(IBasicEnum basicEnumIntface) {
        this.basicEnumIntface = basicEnumIntface;
    }

    @Override
    public String toString() {
        return "ProjectException{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
