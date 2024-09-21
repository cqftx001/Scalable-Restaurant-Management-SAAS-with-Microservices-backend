package com.itheima.restkeeper.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.restkeeper.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.restkeeper.req.UserVo;

import java.util.List;

/**
 * @Description：用户表 服务类
 */
public interface IUserService extends IService<User> {

    /**
     * @Description 用户列表
     * @param userVo 查询条件
     * @return
     */
    Page<User> findUserVoPage(UserVo userVo, int pageNum, int pageSize);

    /**
     * @Description 创建用户
     * @param userVo 对象信息
     * @return
     */
    User createUser(UserVo userVo);

    /**
     * @Description 修改用户
     * @param userVo 对象信息
     * @return
     */
    Boolean updateUser(UserVo userVo);

    /**
     * @Description 删除用户
     * @param checkedIds 选择的用户ID
     * @return
     */
    Boolean deleteUser(String[] checkedIds);

    /**
     * @Description 查找用户list
     * @return
     */
    List<User> findUserVoList();

    /**
     * @Description 启用禁用用户
     * @param userVo 用户信息
     * @return
     */
    Boolean updateUserEnableFlag(UserVo userVo);
}
