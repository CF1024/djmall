package com.dj.mall.task.rabbitmq.order.consumer;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.model.contant.DictConstant;
import com.dj.mall.order.api.OrderApi;
import com.dj.mall.order.dto.OrderDTO;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * @author chengf
 * @date 2020/8/9 21:25
 * 待支付超过限定时间自动取消
 */
@Component
public class OrderConsumer {

    @Reference
    private OrderApi orderApi;

    /**
     * 消费者
     *
     * @param message 消息体
     * @throws Exception
     */
    @RabbitHandler
    @RabbitListener(queues = "orderDlxQueue")
    public void process(Message message) throws Exception {
        try {
            String orderNo = new String(message.getBody(), StandardCharsets.UTF_8);
            OrderDTO order = orderApi.findOrderByOrderNo(orderNo);
            if (null!= order.getOrderStatus() && DictConstant.PENDING_PAYMENT.equals(order.getOrderStatus())) {
                orderApi.updateStatus(orderNo, DictConstant.CANCELLED, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
