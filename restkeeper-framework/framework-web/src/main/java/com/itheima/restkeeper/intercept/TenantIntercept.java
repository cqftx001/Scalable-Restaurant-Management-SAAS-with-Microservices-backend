package com.itheima.restkeeper.intercept;

import com.itheima.restkeeper.constant.SuperConstant;
import com.itheima.restkeeper.utils.EmptyUtil;
import com.itheima.restkeeper.utils.UserVoContext;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName TenantIntercept.java
 * @Description 多租户放到dubbo上下文中
 */
@Component
public class TenantIntercept implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        //从头部中拿到用户信息
        String userVoString = request.getHeader(SuperConstant.CURRENT_USER);
        if (!EmptyUtil.isNullOrEmpty(userVoString)){
            //放入当前线程中：用户当前的web直接获得对象使用
            UserVoContext.setUserVoString(userVoString);
            //放入当前RpcContext中：隐式传参
            RpcContext.getContext().setAttachment(SuperConstant.CURRENT_USER,userVoString);
        }
        //从头部中拿到storeId
        String storeId = request.getHeader(SuperConstant.CURRENT_STORE);
        if (!EmptyUtil.isNullOrEmpty(storeId)){
            //放入当前RpcContext中
            RpcContext.getContext().setAttachment(SuperConstant.CURRENT_STORE,storeId);
        }
        return true;
    }

}
