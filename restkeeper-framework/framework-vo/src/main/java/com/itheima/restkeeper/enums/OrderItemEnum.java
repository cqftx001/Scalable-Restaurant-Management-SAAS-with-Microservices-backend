package com.itheima.restkeeper.enums;

import com.itheima.restkeeper.basic.IBasicEnum;

/**
 * @ClassName OrderItem.java
 * @Description TODO
 */
public enum OrderItemEnum implements IBasicEnum {
    SUCCEED("200","操作成功"),
    UPDATE_ORDERITEM_FAIL("33001","修改订单项失败"),
    DELETE_ORDERITEM_FAIL("33002","删除订单项失败"),
    SAVE_ORDER_FAIL("33003","保存订单失败"),
    LOCK_ORDER_FAIL("33004","保存订单失败"),
            ;

    private String code;
    private String msg;

    OrderItemEnum(String code, String msg) {
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
