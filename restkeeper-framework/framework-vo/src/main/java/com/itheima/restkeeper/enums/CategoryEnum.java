package com.itheima.restkeeper.enums;

import com.itheima.restkeeper.basic.IBasicEnum;

/**
 * @ClassName CategoryEnum.java
 * @Description TODO
 */
public enum CategoryEnum implements IBasicEnum {
    SUCCEED("200","操作成功"),
    FAIL("1000","操作失败"),
    PAGE_FAIL("24001", "查询分类列表失败"),
    CREATE_FAIL("24002", "保存分类失败"),
    UPDATE_FAIL("24003", "修改分类失败"),
    DELETE_FAIL("24004", "修改分类失败"),
    SELECT_CATEGORY_FAIL("24005", "查询分类失败"),
    SELECT_CATEGORY_LIST_FAIL("24006", "查询分类list失败")
    ;

    private String code;
    private String msg;

    CategoryEnum(String code, String msg) {
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

