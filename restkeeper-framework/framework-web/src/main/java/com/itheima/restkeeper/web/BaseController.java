package com.itheima.restkeeper.web;

import com.alibaba.fastjson.JSON;
import com.itheima.restkeeper.basic.ResponseWrap;
import com.itheima.restkeeper.enums.BasicEnum;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.utils.ExceptionsUtil;
import com.itheima.restkeeper.utils.ResponseWrapBuild;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.rpc.RpcException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName BaseController.java
 * @Description 基础的controller
 * ControllerAdvice：对controller层的增强，其他的controller则不需要继承，也会被拦截处理
 */
@ControllerAdvice
@Slf4j
public class BaseController {

    //表示当请求发生异常时，被ExceptionHandler注释的方法会去处理
    @ExceptionHandler
    public void ExceptionHandler(Exception ex, HttpServletResponse response) throws IOException {
        ResponseWrap<Object> responseWrap = null;
        //自定义异常
        if (ex instanceof ProjectException){
            ProjectException projectException = (ProjectException) ex;
            responseWrap = ResponseWrapBuild.build(projectException.getBasicEnumIntface(), null);
        //远程调用异常
        }else if (ex instanceof RpcException){
            responseWrap = ResponseWrapBuild.build(BasicEnum.DUBBO_FAIL, null);
        //参数校验异常
        }else if (ex instanceof MethodArgumentNotValidException){
            MethodArgumentNotValidException manvExceptionex = (MethodArgumentNotValidException) ex;
            log.warn("@Validated约束在参数模型前，校验该模型的字段时发生异常:{} ", manvExceptionex.getMessage());
            responseWrap = ResponseWrapBuild.build(BasicEnum.VALID_EXCEPTION, manvExceptionex.getMessage());
        }else {
        //系统异常
            responseWrap = ResponseWrapBuild.build(BasicEnum.SYSYTEM_FAIL, null);
            log.error("系统异常：{}",ExceptionsUtil.getStackTraceAsString(ex));
        }
        //编码防止中文问题
        response.setContentType("application/json;charset =utf-8");
        response.getWriter().write(JSON.toJSONString(responseWrap));
    }
}
