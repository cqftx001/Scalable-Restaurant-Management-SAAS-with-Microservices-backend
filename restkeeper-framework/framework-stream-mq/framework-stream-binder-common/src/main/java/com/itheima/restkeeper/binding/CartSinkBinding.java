package com.itheima.restkeeper.binding;

import com.itheima.restkeeper.sink.CartSink;
import org.springframework.cloud.stream.annotation.EnableBinding;

/**
 * @ClassName CartSourceBinding.java
 * @Description CartSource接收
 */
@EnableBinding(CartSink.class)
public class CartSinkBinding {
}
