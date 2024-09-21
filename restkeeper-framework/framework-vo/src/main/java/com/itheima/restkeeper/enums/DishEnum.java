package com.itheima.restkeeper.enums;

import com.itheima.restkeeper.basic.IBasicEnum;

/**
 * @ClassName DishEnum.java
 * @Description TODO
 */
public enum DishEnum implements IBasicEnum {
    SUCCEED("200","操作成功"),
    FAIL("1000","操作失败"),
    PAGE_FAIL("26001", "查询菜品列表失败"),
    CREATE_FAIL("26002", "保存菜品失败"),
    UPDATE_FAIL("26003", "修改菜品失败"),
    DELETE_FAIL("26004", "修改菜品失败"),
    SELECT_DISH_FAIL("26005", "查询菜品失败"),
    SELECT_DISH_LIST_FAIL("26007", "查询菜品list失败")
    ;

    private String code;
    private String msg;

    DishEnum(String code, String msg) {
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
