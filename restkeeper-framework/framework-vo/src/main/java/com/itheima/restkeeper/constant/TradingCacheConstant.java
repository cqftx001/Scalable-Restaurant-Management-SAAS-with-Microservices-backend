package com.itheima.restkeeper.constant;

/**
 * @ClassName TradingCacheConstant.java
 * @Description 交易缓存维护
 */
public class TradingCacheConstant {

    //默认redis等待时间
    public static final int REDIS_WAIT_TIME = 5;

    //默认redis自动释放时间
    public static final int REDIS_LEASETIME = 4;

    //安全组前缀
    public static final String REDIS_GROUP = "trading:";

    //创建交易加锁
    public static final String CREATE_PAY = REDIS_GROUP+ "create_pay:";

    //创建退款加锁
    public static final String REFUND_PAY = REDIS_GROUP+ "refund_pay:";

}
