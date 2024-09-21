package com.itheima.restkeeper.enums;

import com.itheima.restkeeper.basic.IBasicEnum;

/**
 * @ClassName SmsTemplateEnum.java
 * @Description TODO
 */
public enum  SmsTemplateEnum implements IBasicEnum {

    SUCCEED("200","操作成功"),
    FAIL("1000","操作失败"),
    PAGE_FAIL("48001", "查询模板列表失败"),
    CREATE_FAIL("48002", "保存模板失败"),
    UPDATE_FAIL("48003", "修改模板失败"),
    DELETE_FAIL("48004", "修改模板失败"),
    SELECT_FAIL("48005", "查询模板失败");

    private String code;
    private String msg;

    SmsTemplateEnum(String code, String msg) {
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
