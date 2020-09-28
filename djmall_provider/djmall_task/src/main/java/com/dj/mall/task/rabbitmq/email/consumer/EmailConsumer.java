package com.dj.mall.task.rabbitmq.email.consumer;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.dj.mall.auth.api.user.MailBoxApi;
import com.dj.mall.model.contant.AuthConstant;
import com.dj.mall.model.contant.DictConstant;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author chengf
 * @date 2020/8/9 19:09
 * 邮箱rabbitmq
 */
@Component
public class EmailConsumer {

    @Reference
    private MailBoxApi mailBoxApi;

    /**
     * 消费者1
     * @param message 消息体
     * @throws Exception
     */
    @RabbitHandler
    @RabbitListener(queues = "emailQueue")
    public void process(Message message) throws Exception {
        try {
            JSONObject jsonObject = JSONObject.parseObject(new String(message.getBody(), "UTF-8"));
            Integer userId = jsonObject.getInteger("userId");
            Integer userRole = jsonObject.getInteger("userRole");
            String userStatus = jsonObject.getString("userStatus");
            String email = jsonObject.getString("email");
            //如果激活状态为：未激活 并且是商家 则发送邮件
            if (DictConstant.NOT_ACTIVATE.equals(userStatus) && AuthConstant.BUSINESS.equals(userRole)) {
                //邮箱验证
                String mailHTML = "<html>"
                        + "<META http-equiv=Content-Type content='text/html; charset=UTF-8'>"
                        + "<body>"
                        + "<a href='http://127.0.0.1:8081/admin/auth/user/toValidate/"+userId+"'>马上验证邮箱</a><br />"
                        + "如果您无法点击以上链接，请复制以下网址到浏览器里直接打开：<br />"
                        + "http://127.0.0.1:8081/admin/auth/user/toValidate/"+userId+"<br />"
                        + "如果您没有注册，请忽略此邮件"
                        + "</body>"
                        + "</html>";
                mailBoxApi.sendMailHTML(email, AuthConstant.ACTIVATE, mailHTML);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
