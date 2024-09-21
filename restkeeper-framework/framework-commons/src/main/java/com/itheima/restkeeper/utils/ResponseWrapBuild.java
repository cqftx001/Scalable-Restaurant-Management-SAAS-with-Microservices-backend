package com.itheima.restkeeper.utils;

import com.alibaba.fastjson.JSONObject;
import com.itheima.restkeeper.basic.IBasicEnum;
import com.itheima.restkeeper.basic.ResponseWrap;
import com.itheima.restkeeper.req.UserVo;

import java.util.Date;

/**
 * @Description 构造ResponseWrap工具
 */
public class ResponseWrapBuild {

    public static <T>ResponseWrap<T> build(IBasicEnum basicEnumIntface, T t){

        //从UserVoContext中拿到userVoString
        String userVoString = UserVoContext.getUserVoString();
        UserVo userVo = null;
        if (!EmptyUtil.isNullOrEmpty(userVoString)){
            userVo = JSONObject.parseObject(userVoString, UserVo.class);
        }else {
            userVo = new UserVo();
        }
        //构建对象
        return ResponseWrap.<T>builder()
                .code(basicEnumIntface.getCode())
                .msg(basicEnumIntface.getMsg())
                .operationTime(new Date())
                .userId(userVo.getId())
                .userName(userVo.getUsername())
                .datas(t)
                .build();
    }

}
