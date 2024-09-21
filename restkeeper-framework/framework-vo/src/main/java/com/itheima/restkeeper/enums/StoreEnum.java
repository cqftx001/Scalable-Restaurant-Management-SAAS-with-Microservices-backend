package com.itheima.restkeeper.enums;

import com.itheima.restkeeper.basic.IBasicEnum;

/**
 * @ClassName StoreEnum.java
 * @Description TODO
 */
public enum StoreEnum implements IBasicEnum {
    SUCCEED("200","操作成功"),
    FAIL("1000","操作失败"),
    PAGE_FAIL("40001", "查询门店列表失败"),
    CREATE_FAIL("40002", "保存门店失败"),
    UPDATE_FAIL("40003", "修改门店失败"),
    DELETE_FAIL("40004", "修改门店失败"),
    SELECT_STORE_FAIL("40005", "查询门店失败"),
    SELECT_STORE_LIST_FAIL("40006", "查询门店list失败")
    ;

    private String code;
    private String msg;

    StoreEnum(String code, String msg) {
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

