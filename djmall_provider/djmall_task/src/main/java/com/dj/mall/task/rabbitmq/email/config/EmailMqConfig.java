package com.dj.mall.task.rabbitmq.email.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 单播-配置
 */
@Configuration
public class EmailMqConfig {

    /**
     * 创建队列-队列
     *
     * @return
     */
    @Bean
    public Queue emailQueue() {
        // name=队列的名称
        // durable 是否持久化
        // exclusive 是否独占队列
        // autoDelete 是否自动删除
        return new Queue("emailQueue", true);
    }

    /**
     * 创建单播路由
     *
     * @return
     */
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("emailExchange");
    }

    /**
     * 队列绑定
     *
     * @return
     */
    @Bean
    public Binding bindingEmailQueue() {
        // Message的routingKey与其一致 单播建议与队列名称一致
        return BindingBuilder.bind(emailQueue()).to(directExchange()).with("emailQueue");
    }

}
