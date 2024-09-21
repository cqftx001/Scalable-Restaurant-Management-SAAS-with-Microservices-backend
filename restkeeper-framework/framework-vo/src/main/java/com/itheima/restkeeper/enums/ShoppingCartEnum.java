package com.itheima.restkeeper.enums;

import com.itheima.restkeeper.basic.IBasicEnum;

/**
 * @ClassName ShoppingCartEnum.java
 * @Description 购物车操作枚举
 */
public enum ShoppingCartEnum implements IBasicEnum {
    SUCCEED("200","操作成功"),
    UPDATE_DISHNUM_FAIL("38001","修改购物车数量失败"),
    CREATE_ORDERITEM_FAIL("38002", "创建购物车订单项失败"),
    UPDATE_DISHNUMBER_FAIL("38003","修改菜品数量失败"),
    UNDERSTOCK("38004","库存不足")
    ;

    private String code;
    private String msg;

    ShoppingCartEnum(String code, String msg) {
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
