package com.itheima.restkeeper.enums;

import com.itheima.restkeeper.basic.IBasicEnum;

/**
 * @ClassName EnterpriseEnum.java
 * @Description 企业
 */
public enum EnterpriseEnum implements IBasicEnum {
    SUCCEED("200","操作成功"),
    FAIL("1000","操作失败"),
    LOGOUT_SUCCEED("27001","退出成功"),
    PAGE_FAIL("27002", "查询企业列表失败"),
    CREATE_FAIL("27003", "保存企业失败"),
    UPDATE_FAIL("27004", "修改企业失败"),
    DELETE_FAIL("27005", "修改企业失败"),
    INIT_ENTERPRISEID_OPTIONS_FAIL("27006", "初始化企业树失败")
    ;

    private String code;
    private String msg;

    EnterpriseEnum(String code, String msg) {
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
