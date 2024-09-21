package com.itheima.restkeeper.enums;

import com.itheima.restkeeper.basic.IBasicEnum;

/**
 * @ClassName SmsSignEnum.java
 * @Description TODO
 */
public enum SmsSignEnum implements IBasicEnum {
    SUCCEED("200","操作成功"),
    FAIL("1000","操作失败"),
    PAGE_FAIL("46001", "查询签名列表失败"),
    CREATE_FAIL("46002", "保存签名失败"),
    UPDATE_FAIL("46003", "修改签名失败"),
    DELETE_FAIL("46004", "删除签名失败"),
    SELECT_FAIL("46005", "查询签名失败");
    private String code;
    private String msg;

    SmsSignEnum(String code, String msg) {
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
