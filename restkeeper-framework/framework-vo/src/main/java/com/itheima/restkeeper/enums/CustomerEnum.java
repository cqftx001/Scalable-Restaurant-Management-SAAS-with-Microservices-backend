package com.itheima.restkeeper.enums;

import com.itheima.restkeeper.basic.IBasicEnum;

/**
 * @ClassName CustomerEnum.java
 * @Description TODO
 */
public enum CustomerEnum implements IBasicEnum {
    SUCCEED("200","操作成功"),
    LOGOUT_SUCCEED("1004","退出成功"),
    FAIL("1000","操作失败"),
    PAGE_FAIL("50001", "查询客户列表失败"),
    CREATE_FAIL("50002", "保存客户失败"),
    UPDATE_FAIL("50003", "修改客户失败"),
    DELETE_FAIL("50004", "修改客户失败"),
    SELECT_USER_FAIL("50005", "查询客户失败"),
    SELECT_ROLE_FAIL("50006", "查询客户对应角色失败"),
    SELECT_RESOURCE_FAIL("50007", "查询客户对应资源失败"),
    SELECT_CURRENT_USER("50008", "查询当前客户失败"),
    SELECT_USER_LIST_FAIL("50009", "查询客户list失败"),
    ;

    private String code;
    private String msg;

    CustomerEnum(String code, String msg) {
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
