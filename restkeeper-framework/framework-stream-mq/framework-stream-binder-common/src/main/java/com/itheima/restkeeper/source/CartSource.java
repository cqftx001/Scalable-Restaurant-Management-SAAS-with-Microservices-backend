package com.itheima.restkeeper.source;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @ClassName LogSource.java
 * @Description 发送购物车延迟消息
 */
public interface CartSource {

    public static String CART_OUTPUT = "cart-output";

    /***
     * @description TODO
     * 这里，我们通过 @Output 注解，声明了一个名字为 elog-output的 Output Binding。
     * 注意，这个名字要和我们配置文件中的 spring.cloud.stream.bindings 配置项对应上。
     * 同时，@Output 注解的方法的返回结果为 MessageChannel 类型，可以使用它发送消息。
     */
    @Output(CART_OUTPUT)
    MessageChannel cartOutput();
}
