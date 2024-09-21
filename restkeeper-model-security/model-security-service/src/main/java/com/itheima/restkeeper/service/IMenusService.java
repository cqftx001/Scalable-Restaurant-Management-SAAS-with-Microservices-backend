/*
 * <b>文件名</b>：MenusService.java
 *
 * 文件描述：
 *
 *
 * 2017-4-6  下午10:23:08
 */

package com.itheima.restkeeper.service;


import com.itheima.restkeeper.req.MenuVo;

import java.util.List;


/**
 * @Description 菜单服务
 */

public interface IMenusService {

    /**
     * @Description 查询系统菜单
     * @param systemCode 系统编码
     * @return
     */
    List<MenuVo> findMenusBySystemCode(String systemCode);

}
