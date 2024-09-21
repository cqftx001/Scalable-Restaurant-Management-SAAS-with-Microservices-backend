package com.itheima.restkeeper.utils;

import com.itheima.restkeeper.enums.CustomerEnum;
import com.itheima.restkeeper.exception.ProjectException;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName CustomerVoContext.java
 * @Description 用于dubbot参数传递
 */
@Slf4j
public class CustomerVoContext {

    // 创建线程局部变量，并初始化值
    private static ThreadLocal<String> userIdThreadLocal = new ThreadLocal<String>() {
        @Override
        protected String initialValue() {
            return null;
        }
    };

    // 提供线程局部变量set方法
    public static void setCustomerVoString(String userVoString) {
        userIdThreadLocal.set(userVoString);
    }

    // 提供线程局部变量get方法
    public static String getCustomerVoString() {
        try {
            return userIdThreadLocal.get();
        } catch (Exception e) {
            log.error("查询当前用户异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(CustomerEnum.SELECT_CURRENT_USER);
        }

    }
}
