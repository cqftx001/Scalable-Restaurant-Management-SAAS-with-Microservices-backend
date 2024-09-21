package com.itheima.restkeeper.enums;

import com.itheima.restkeeper.basic.IBasicEnum;

/**
 * @ClassName AffixEnum.java
 * @Description TODO
 */
public enum AffixEnum implements IBasicEnum {

    SUCCEED("200","操作成功"),
    FAIL("1000","操作失败"),
    UPLOAD_FAIL("21001", "查询数据字典列表失败"),
    SELECT_AFFIX_BUSINESSID_FAIL("21002", "查询业务对应附件失败"),
    DELETE_AFFIX_BUSINESSID_FAIL("21003", "删除业务对应附件失败"),
    PAGE_FAIL("21004", "查询附件列表失败"),
    DELETE_AFFIX_FAIL("21005", "删除附件失败"),
    ;

    private String code;
    private String msg;

    AffixEnum(String code, String msg) {
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
