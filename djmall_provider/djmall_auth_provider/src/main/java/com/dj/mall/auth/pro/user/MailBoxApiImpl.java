/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_auth_provider
 * 类名：MailBoxApiImpl
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.auth.pro.user;

import com.alibaba.dubbo.config.annotation.Service;
import com.dj.mall.auth.api.user.MailBoxApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;

/**
 * @author chengf
 * @date 2020/6/5 23:59
 * 邮箱api实现类
 */
@Service
public class MailBoxApiImpl implements MailBoxApi {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.properties.from}")
    private String from;

    /**
     * 发送普通邮件
     *
     * @param to       收件人
     * @param subject  主题
     * @param mailText 邮件内容
     */
    @Override
    public boolean sendMail(String to, String subject, String mailText) {
        SimpleMailMessage mail = new SimpleMailMessage();
        //to 收件人
        mail.setTo(to);
        //发件人
        mail.setFrom(from);
        //邮件名称
        mail.setSubject(subject);
        //邮件主题
        mail.setText(mailText);
        //发送邮件
        mailSender.send(mail);
        return true;
    }

    /**
     * 发送HTML邮件
     *
     * @param to       收件人
     * @param subject  主题
     * @param mailHTML 邮件内容(带HTML)
     */
    @Override
    public boolean sendMailHTML(String to, String subject, String mailHTML) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        //to 收件人
        helper.setTo(to);
        //发件人
        helper.setFrom(from);
        //邮件名称
        helper.setSubject(subject);
        //邮件主体内容
        helper.setText(mailHTML, true);
        //发送邮件
        mailSender.send(message);
        return true;
    }

    /**
     * 发送带文件的邮件
     *
     * @param to       收件人
     * @param subject  主题
     * @param mailText 邮件内容
     * @param file     附件
     * @throws Exception
     */
    @Override
    public boolean sentMailFile(String to, String subject, String mailText, byte[] file) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        //to 收件人
        helper.setTo(to);
        //发件人
        helper.setFrom(from);
        //邮件名称
        helper.setSubject(subject);
        //邮件主题
        helper.setText(mailText);
        InputStreamSource inputStream = new ByteArrayResource(file);
        //将附件添加至邮件主体
        helper.addAttachment("附件", inputStream);
        //发送邮件
        mailSender.send(message);
        return true;
    }
}
