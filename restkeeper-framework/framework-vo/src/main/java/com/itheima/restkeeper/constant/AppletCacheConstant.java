package com.itheima.restkeeper.constant;

/**
 * @ClassName AppletCacheConstant.java
 * @Description 小程序锁
 */
public class AppletCacheConstant {

    //默认redis等待时间
    public static final int REDIS_WAIT_TIME = 5;

    //默认redis自动释放时间
    public static final int REDIS_LEASETIME = 4;

    //分布式锁前缀
    public static final String PREFIX= "applet:";

    //开台加锁
    public static final String OPEN_TABLE_LOCK= PREFIX+"openTable:lock:";

    //菜品库存
    public static final String REPERTORY_DISH = PREFIX+"repertoryDish:";

    //菜品库存锁
    public static final String REPERTORY_DISH_LOCK = PREFIX+"repertoryDish:lock:";

    //添加订单项锁
    public static final String ADD_TO_ORDERITEM_LOCK = PREFIX+"addToOrderItem:lock:";

    //购物车中订单项
    public static final String ORDERITEMVO_STATISTICS = PREFIX+"orderItemVoStatistics:";
}
