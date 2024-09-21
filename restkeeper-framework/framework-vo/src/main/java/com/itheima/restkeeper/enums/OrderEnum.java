package com.itheima.restkeeper.enums;

import com.itheima.restkeeper.basic.IBasicEnum;

/**
 * @ClassName OrderEnum.java
 * @Description TODO
 */
public enum  OrderEnum implements IBasicEnum {

    SUCCEED("200","操作成功"),
    FAIL("1000","操作失败"),
    PAGE_FAIL("32001", "查询订单列表失败"),
    STATUS_FAIL("32002", "订单状态非待付款，不可修改"),
    UPDATE_FAIL("32009", "修改订单失败"),
    DISH_STATUS_FAIL("32003", "菜品状态非起售，不可修改"),
    REFUND_FAIL("32004", "收付款员工不同，不可退款"),
    SELECT_TABLE_ORDER_FAIL("32005", "查询桌台订单"),
    OPERTION_SHOPPING_CART_FAIL("32006", "操作购物车失败"),
    CLEAR_SHOPPING_CART_FAIL("32007", "清理购物车失败"),
    PLACE_ORDER_FAIL("32008", "下单失败"),
    ;

    private String code;
    private String msg;

    OrderEnum(String code, String msg) {
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
