package com.itheima.restkeeper.sink;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @ClassName LogSink.java
 * @Description 购物车过期监听接收消息
 */
public interface CartSink {

    public static String CART_INPUT="cart-input";

    @Input(CART_INPUT)
    SubscribableChannel cartInput();

}
