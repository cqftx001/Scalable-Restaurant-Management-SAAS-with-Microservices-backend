package com.itheima.restkeeper.log.binding;

import com.itheima.restkeeper.source.LogSource;
import org.springframework.cloud.stream.annotation.EnableBinding;

/**
 * @ClassName Binding.java
 * @Description 绑定声明
 */
@EnableBinding({LogSource.class})
public class SourceBinding {
}
