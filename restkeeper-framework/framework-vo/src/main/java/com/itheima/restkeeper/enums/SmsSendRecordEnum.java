package com.itheima.restkeeper.enums;

import com.itheima.restkeeper.basic.IBasicEnum;

/**
 * @ClassName SmsSendRecordEnum.java
 * @Description TODO
 */
public enum SmsSendRecordEnum implements IBasicEnum {
    
    SUCCEED("200","操作成功"),
    FAIL("1000","操作失败"),
    PAGE_FAIL("49001", "查询发送记录列表失败"),
    CREATE_FAIL("49002", "保存发送记录失败"),
    UPDATE_FAIL("49003", "修改发送记录失败"),
    DELETE_FAIL("49004", "修改发送记录失败"),
    SELECT_FAIL("49005", "查询发送记录失败"),
    SEND_RECORD_FAIL("49006", "重发失败");

    private String code;
    private String msg;

    SmsSendRecordEnum(String code, String msg) {
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
