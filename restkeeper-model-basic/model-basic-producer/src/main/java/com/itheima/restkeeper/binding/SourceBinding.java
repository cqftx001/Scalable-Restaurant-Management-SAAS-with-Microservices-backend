package com.itheima.restkeeper.binding;

import com.itheima.restkeeper.source.SmsSource;
import org.springframework.cloud.stream.annotation.EnableBinding;

/**
 * @ClassName Binding.java
 * @Description 绑定声明
 */
@EnableBinding({SmsSource.class})
public class SourceBinding {
}
