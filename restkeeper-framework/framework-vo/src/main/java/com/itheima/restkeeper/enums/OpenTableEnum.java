package com.itheima.restkeeper.enums;

import com.itheima.restkeeper.basic.IBasicEnum;

/**
 * @ClassName openTableEnum.java
 * @Description TODO
 */
public enum OpenTableEnum implements IBasicEnum {
    SUCCEED("200","操作成功"),
    CHANG_TABLE_FAIL("31001","开桌失败"),
    CREATE_ORDER_FAIL("31002", "创建订单失败"),
    TRY_LOCK_FAIL("31003","加锁失败")
    ;

    private String code;
    private String msg;

    OpenTableEnum(String code, String msg) {
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
