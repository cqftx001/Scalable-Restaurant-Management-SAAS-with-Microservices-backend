package com.itheima.restkeeper.service;

import com.itheima.restkeeper.pojo.Resource;
import com.itheima.restkeeper.pojo.Role;
import com.itheima.restkeeper.pojo.User;

import java.util.List;


/**
 * @Description 后台登陆用户适配器接口
 */

public interface IUserAdapterService {

	/**
	 * @Description 按客户手机查找客户
	 * @param mobil 手机号码
	 * @return
	 */
	User findUserByMobilAndEnterpriseId(String mobil, Long enterpriseId);

	/**
	 * @Description 按用户名查找用户
	 * @param username 登录名
	 * @return
	 */
	User findUserByUsernameAndEnterpriseId(String username, Long enterpriseId);

	/**
	 * @Description 查找用户所有角色
	 * @param userId 用户Id
	 * @return
	 */
	List<Role> findRoleByUserId(Long userId);

	/**
	 * @Description 查询用户有那些资源
	 * @param userId 用户Id
	 * @return
	 */
	List<Resource> findResourceByUserId(Long userId);

}
