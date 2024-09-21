package com.itheima.restkeeper.enums;

import com.itheima.restkeeper.basic.IBasicEnum;

/**
 * @ClassName MenusEnum.java
 * @Description 菜单枚举
 */
public enum MenusEnum implements IBasicEnum {
    SUCCEED("200","操作成功"),
    FAIL("1000","操作失败"),
    PAGE_FAIL("30001", "查询菜单列表失败")
    ;

    private String code;
    private String msg;

    MenusEnum(String code, String msg) {
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
