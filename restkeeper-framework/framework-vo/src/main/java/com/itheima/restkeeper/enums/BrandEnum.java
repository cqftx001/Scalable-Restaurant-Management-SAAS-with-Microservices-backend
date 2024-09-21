package com.itheima.restkeeper.enums;

import com.itheima.restkeeper.basic.IBasicEnum;

/**
 * @ClassName BrandEnum.java
 * @Description TODO
 */
public enum BrandEnum implements IBasicEnum {
    SUCCEED("200","操作成功"),
    FAIL("1000","操作失败"),
    PAGE_FAIL("23001", "查询品牌列表失败"),
    CREATE_FAIL("23002", "保存品牌失败"),
    UPDATE_FAIL("23003", "修改品牌失败"),
    DELETE_FAIL("23004", "修改品牌失败"),
    SELECT_BRAND_FAIL("23005", "查询品牌失败")
    ;

    private String code;
    private String msg;

    BrandEnum(String code, String msg) {
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

