package com.itheima.restkeeper.enums;

import com.itheima.restkeeper.basic.IBasicEnum;

/**
 * @ClassName EnterpriseSettingEnum.java
 * @Description TODO
 */
public enum TradingSettingEnum implements IBasicEnum {
    
    SUCCEED("200","操作成功"),
    FAIL("1000","操作失败"),
    PAGE_FAIL("44001", "查询企业配置列表失败"),
    CREATE_FAIL("44002", "保存企业配置失败"),
    UPDATE_FAIL("44003", "修改企业配置失败"),
    DELETE_FAIL("44004", "修改企业配置失败"),
    SELECT_FAIL("44005", "修改企业配置失败")
            ;

    private String code;
    private String msg;

    TradingSettingEnum(String code, String msg) {
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
