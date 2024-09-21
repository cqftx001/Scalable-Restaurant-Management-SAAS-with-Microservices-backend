package com.itheima.restkeeper.binding;

import com.itheima.restkeeper.sink.LogSink;
import com.itheima.restkeeper.sink.SmsSink;
import org.springframework.cloud.stream.annotation.EnableBinding;

/**
 * @ClassName Binding.java
 * @Description 绑定声明
 */
@EnableBinding({LogSink.class, SmsSink.class})
public class SinkBinding {
}
