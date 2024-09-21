package com.itheima.restkeeper.constant;

/**
 * @Description: 缓存常量类
 */
public class SecurityCacheConstant {

    //安全组前缀
    public static final String REDIS_GROUP = "security:";

    //运营商后台站点
    public static final String WEBSITE = REDIS_GROUP+ "webSite:";

    //运营商app站点
    public static final String APP_WEBSITE = REDIS_GROUP+ "appWebSite:";

}
