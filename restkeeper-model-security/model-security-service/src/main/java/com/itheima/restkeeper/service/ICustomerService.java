package com.itheima.restkeeper.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.restkeeper.pojo.Customer;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.restkeeper.pojo.Customer;
import com.itheima.restkeeper.req.CustomerVo;

import java.util.List;

/**
 * @Description： 服务类
 */
public interface ICustomerService extends IService<Customer> {
    /**
     * @Description 客户列表
     * @param customerVo 查询条件
     * @return
     */
    Page<Customer> findCustomerVoPage(CustomerVo customerVo, int pageNum, int pageSize);

    /**
     * @Description 创建客户
     * @param customerVo 对象信息
     * @return
     */
    Customer createCustomer(CustomerVo customerVo);

    /**
     * @Description 修改客户
     * @param customerVo 对象信息
     * @return
     */
    Boolean updateCustomer(CustomerVo customerVo);

    /**
     * @Description 删除客户
     * @param checkedIds 选择的客户ID
     * @return
     */
    Boolean deleteCustomer(String[] checkedIds);

    /**
     * @Description 查找客户list
     * @return
     */
    List<Customer> findCustomerVoList();

    /**
     * @Description 启用禁用客户
     * @param customerVo 客户信息
     * @return
     */
    Boolean updateCustomerEnableFlag(CustomerVo customerVo);
}
