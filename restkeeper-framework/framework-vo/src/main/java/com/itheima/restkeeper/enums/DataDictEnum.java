package com.itheima.restkeeper.enums;

import com.itheima.restkeeper.basic.IBasicEnum;

/**
 * @Description：数字字典枚举
 */
public enum DataDictEnum implements IBasicEnum {
    SUCCEED("200","操作成功"),
    FAIL("1000","操作失败"),
    PAGE_FAIL("25001", "查询数据字典列表失败"),
    SAVE_FAIL("25002", "保存或修改字典数据失败"),
    UPDATE_FAIL("25003", "保存或修改字典数据失败"),
    UPDATE_DATAKEY_ENABLEFLAG_FAIL("25004", "禁用，启用数据字典失败"),
    SELECT_DATAKEY_FAIL("25005", "根据dataKey查询失败"),
    SELECT_PARENTKEY_FAIL("25006", "根据parentKey查询失败")
    ;

    private String code;
    private String msg;

    DataDictEnum(String code, String msg) {
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
