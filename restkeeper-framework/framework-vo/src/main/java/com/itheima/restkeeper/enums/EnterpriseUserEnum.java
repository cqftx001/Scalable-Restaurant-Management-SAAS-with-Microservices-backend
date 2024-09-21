package com.itheima.restkeeper.enums;

import com.itheima.restkeeper.basic.IBasicEnum;

/**
 * @ClassName UserEnum.java
 * @Description TODO
 */
public enum EnterpriseUserEnum implements IBasicEnum {
    SUCCEED("200","操作成功"),
    FAIL("1000","操作失败"),
    PAGE_FAIL("28001", "查询用户列表失败"),
    CREATE_FAIL("28002", "保存用户失败"),
    UPDATE_FAIL("28003", "修改用户失败"),
    DELETE_FAIL("28004", "修改用户失败"),
    ;

    private String code;
    private String msg;

    EnterpriseUserEnum(String code, String msg) {
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

