package com.itheima.restkeeper.enums;

import com.itheima.restkeeper.basic.IBasicEnum;

/**
 * @ClassName TableEnum.java
 * @Description TODO
 */
public enum TableEnum implements IBasicEnum {
    SUCCEED("200","操作成功"),
    FAIL("1000","操作失败"),
    PAGE_FAIL("42001", "查询桌台列表失败"),
    CREATE_FAIL("42002", "保存桌台失败"),
    UPDATE_FAIL("42003", "修改桌台失败"),
    DELETE_FAIL("42004", "修改桌台失败"),
    SELECT_TABLE_FAIL("42005", "查询桌台失败"),
    OPEN_TABLE_FAIL("42006","开台失败"),
    SELECT_TABLE_LIST_FAIL("42007", "查询桌台list失败"),
    ROTARY_TABLE_FAIL("42008", "转桌失败")
    ;

    private String code;
    private String msg;

    TableEnum(String code, String msg) {
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

