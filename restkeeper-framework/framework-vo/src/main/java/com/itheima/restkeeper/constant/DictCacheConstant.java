package com.itheima.restkeeper.constant;

/**
 * @ClassName DictCacheConstant.java
 * @Description 字典缓存常量
 */
public class DictCacheConstant {

    //默认redis等待时间
    public static final int REDIS_WAIT_TIME = 5;

    //默认redis自动释放时间
    public static final int REDIS_LEASETIME = 4;

    //分布式锁前缀
    public static final String PREFIX= "dict:";

    //分布式锁前缀
    public static final String LOCK_PREFIX = PREFIX+"lock:";
}
