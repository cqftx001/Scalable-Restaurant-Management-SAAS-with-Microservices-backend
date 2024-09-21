package com.itheima.restkeeper.enums;

import com.itheima.restkeeper.basic.IBasicEnum;

/**
 * @ClassName ResourceEnum.java
 * @Description 资源枚举
 */
public enum ResourceEnum implements IBasicEnum {
    SUCCEED("200","操作成功"),
    FAIL("1000","操作失败"),
    PAGE_FAIL("35001", "查询资源列表失败"),
    INIT_TREE_FAIL("35002", "初始化资源树失败"),
    CREATE_FAIL("35003", "保存资源失败"),
    UPDATE_FAIL("35004", "修改资源失败"),
    DELETE_FAIL("35005", "修改资源失败")
            ;

    private String code;
    private String msg;

    ResourceEnum(String code, String msg) {
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
