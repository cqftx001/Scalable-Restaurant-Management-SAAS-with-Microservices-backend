package com.itheima.restkeeper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.req.UserVo;

import java.util.List;

/**
 * @Description：用户dubbo接口
 */
public interface UserFace {

    /**
     * @param userVo   查询条件
     * @param pageNum  当前页
     * @param pageSize 每页条数
     * @return Page<UserVo>
     * @Description 用户列表
     */
    Page<UserVo> findUserVoPage(UserVo userVo,
                                int pageNum,
                                int pageSize)throws ProjectException;

    /**
     * @param userVo 对象信息
     * @return UserVo
     * @Description 创建用户
     */
    UserVo createUser(UserVo userVo)throws ProjectException;

    /**
     * @param userVo 对象信息
     * @return Boolean
     * @Description 修改用户
     */
    Boolean updateUser(UserVo userVo)throws ProjectException;

    /**
     * @param checkedIds 选择对象信息Id
     * @return Boolean
     * @Description 删除用户
     */
    Boolean deleteUser(String[] checkedIds)throws ProjectException;


    /**
     * @param userId 选择对象信息Id
     * @return
     * @Description 查找用户
     */
    UserVo findUserByUserId(Long userId)throws ProjectException;

    /**
     * @return List<UserVo>
     * @Description 查找用户list
     */
    List<UserVo> findUserVoList()throws ProjectException;
}
