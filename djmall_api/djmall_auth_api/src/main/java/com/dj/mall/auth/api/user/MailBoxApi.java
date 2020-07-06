/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_auth_api
 * 类名：MailBoxApi
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.auth.api.user;
/**
 * @author chengf
 * @date 2020/6/5 23:59
 * 邮箱api接口
 */
public interface MailBoxApi {
    /**
     * 发送普通邮件
     *
     * @param to       收件人
     * @param subject  主题
     * @param mailText 邮件内容
     * @return
     * @throws Exception
     */
    boolean sendMail(String to, String subject, String mailText) throws Exception;

    /**
     * 发送HTML邮件
     *
     * @param to       收件人
     * @param subject  主题
     * @param mailHTML 邮件内容(带HTML)
     * @return
     * @throws Exception
     */
    boolean sendMailHTML(String to, String subject, String mailHTML) throws Exception;

    /**
     * 发送带附件的邮件
     *
     * @param to       收件人
     * @param subject  主题
     * @param mailText 邮件内容
     * @param file     附件
     * @return
     * @throws Exception
     */
    boolean sentMailFile(String to, String subject, String mailText, byte[] file) throws Exception;
}
