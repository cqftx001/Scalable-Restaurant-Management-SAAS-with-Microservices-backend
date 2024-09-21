package com.itheima.restkeeper;

import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.req.MenuVo;

import java.util.List;

/**
 * @Description：菜单服务
 */
public interface MenusFace {

    /**
     * @Description 查询系统菜单
     * @param systemCode 系统编码
     * @return
     */
    List<MenuVo> findMenusBySystemCode(String systemCode)throws ProjectException;
}
