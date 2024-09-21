package com.itheima.restkeeper.web;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.restkeeper.CustomerFace;
import com.itheima.restkeeper.basic.ResponseWrap;
import com.itheima.restkeeper.enums.CustomerEnum;
import com.itheima.restkeeper.req.CustomerVo;
import com.itheima.restkeeper.utils.CustomerVoContext;
import com.itheima.restkeeper.utils.ResponseWrapBuild;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName CustomerController.java
 * @Description 客户Controller
 */
@RestController
@RequestMapping("customer")
@Slf4j
@Api(tags = "客户controller")
public class CustomerController {

    @DubboReference(version = "${dubbo.application.version}",check = false)
    CustomerFace customerFace;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * @Description 客户列表
     * @param customerVo 查询条件
     * @return
     */
    @PostMapping("page/{pageNum}/{pageSize}")
    @ApiOperation(value = "查询客户分页",notes = "查询客户分页")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "customerVo",value = "客户查询对象",required = false,dataType = "CustomerVo"),
        @ApiImplicitParam(paramType = "path",name = "pageNum",value = "页码",example = "1",dataType = "Integer"),
        @ApiImplicitParam(paramType = "path",name = "pageSize",value = "每页条数",example = "10",dataType = "Integer")
    })
    public ResponseWrap<Page<CustomerVo>> findCustomerVoPage(
        @RequestBody CustomerVo customerVo,
        @PathVariable("pageNum") int pageNum,
        @PathVariable("pageSize") int pageSize)  {
        Page<CustomerVo> customerVoPage = customerFace.findCustomerVoPage(customerVo, pageNum, pageSize);
        return ResponseWrapBuild.build(CustomerEnum.SUCCEED,customerVoPage);
    }

    /**
     * @Description 注册客户
     * @param customerVo 对象信息
     * @return
     */
    @PostMapping
    @ApiOperation(value = "注册客户",notes = "注册客户")
    @ApiImplicitParam(name = "customerVo",value = "客户对象",required = true,dataType = "CustomerVo")
    ResponseWrap<CustomerVo> registerCustomer(@RequestBody CustomerVo customerVo)  {
        String plainPassword = customerVo.getPassword();
        //必须要加{bcrypt}要不认证不通过
        String password = "{bcrypt}"+bCryptPasswordEncoder.encode(plainPassword);
        customerVo.setPassword(password);
        CustomerVo customerVoResult = customerFace.createCustomer(customerVo);
        return ResponseWrapBuild.build(CustomerEnum.SUCCEED,customerVoResult);
    }

    /**
     * @Description 修改客户
     * @param customerVo 对象信息
     * @return
     */
    @PatchMapping
    @ApiOperation(value = "修改客户",notes = "修改客户")
    @ApiImplicitParam(name = "customerVo",value = "客户对象",required = true,dataType = "CustomerVo")
    ResponseWrap<Boolean> updateCustomer(@RequestBody CustomerVo customerVo)  {
        Boolean flag = customerFace.updateCustomer(customerVo);
        return ResponseWrapBuild.build(CustomerEnum.SUCCEED,flag);
    }

    /**
     * @Description 删除客户
     * @param customerVo 查询对象
     * @return
     */
    @DeleteMapping
    @ApiOperation(value = "删除客户",notes = "删除客户")
    @ApiImplicitParam(name = "customerVo",value = "客户查询对象",required = true,dataType = "CustomerVo")
    ResponseWrap<Boolean> deleteCustomer(@RequestBody CustomerVo customerVo ) {
        String[] checkedIds = customerVo.getCheckedIds();
        Boolean flag = customerFace.deleteCustomer(checkedIds);
        return ResponseWrapBuild.build(CustomerEnum.SUCCEED,flag);
    }

    /**
     * @Description 查找客户
     * @param customerId 登录名
     * @return
     */
    @GetMapping("select-by-customerId/{customerId}")
    @ApiOperation(value = "查找客户",notes = "查找客户")
    @ApiImplicitParam(paramType = "path",name = "customerId",value = "客户Id",example = "1",dataType = "Long")
    ResponseWrap<CustomerVo> findCustomerByCustomerId(@PathVariable("customerId") Long customerId)  {
        CustomerVo customerVo = customerFace.findCustomerByCustomerId(customerId);
        return ResponseWrapBuild.build(CustomerEnum.SUCCEED,customerVo);
    }

    /**
     * @Description 查找客户list
     * @return
     */
    @GetMapping("select-list")
    @ApiOperation(value = "查找客户list",notes = "查找客户list")
    ResponseWrap<List<CustomerVo>> findCustomerVoList()  {
        List<CustomerVo> list = customerFace.findCustomerVoList();
        return ResponseWrapBuild.build(CustomerEnum.SUCCEED,list);
    }

    @GetMapping("find-current-customer")
    @ApiOperation(value = "查询当前客户",notes = "查询当前客户")
    ResponseWrap<CustomerVo> findCurrentCustomer()  {
        String currentCustomer = CustomerVoContext.getCustomerVoString();
        CustomerVo customerVo = JSON.parseObject(currentCustomer, CustomerVo.class);
        return ResponseWrapBuild.build(CustomerEnum.SUCCEED,customerVo);
    }

    @PostMapping("update-customer-enableFlag")
    @ApiOperation(value = "修改客户状态",notes = "修改客户状态")
    @ApiImplicitParam(name = "customerVo",value = "客户对象",required = true,dataType = "CustomerVo")
    ResponseWrap<Boolean> updateCustomerEnableFlag(@RequestBody CustomerVo customerVo)  {
        Boolean flag = customerFace.updateCustomerEnableFlag(customerVo);
        return ResponseWrapBuild.build(CustomerEnum.SUCCEED,flag);
    }

}
