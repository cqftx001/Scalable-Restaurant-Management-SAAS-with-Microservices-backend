package com.itheima.restkeeper.enums;

import com.itheima.restkeeper.basic.IBasicEnum;

/**
 * @ClassName AuthEnum.java
 * @Description 认证枚举
 */
public enum  AuthEnum implements IBasicEnum {

    SUCCEED("200","操作成功"),
    FAIL("1000","操作失败"),
    AUTH_FAIL("22001", "鉴权失败"),
    AUTH_UPWD_FAIL("22003", "用户名或密码错误"),
    AUTH_CODE_ISNULL("22004", "验证码过期或不存在"),
    AUTH_CODE_ERROR("22005", "验证码输入错误"),
    NEED_LOGIN("22002", "请先登陆")
            ;

    private String code;
    private String msg;

    AuthEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
