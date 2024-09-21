package com.itheima.restkeeper.binding;

import com.itheima.restkeeper.source.CartSource;
import org.springframework.cloud.stream.annotation.EnableBinding;

/**
 * @ClassName CartSourceBinding.java
 * @Description CartSource发送
 */
@EnableBinding(CartSource.class)
public class CartSourceBinding {
}
