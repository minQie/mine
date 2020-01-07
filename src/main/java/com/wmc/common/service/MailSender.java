package com.wmc.common.service;

import javax.validation.constraints.Email;

/**
 * 邮件发送接口
 *
 * @author 王敏聪
 * @date 2019年11月29日16:33:32
 */
public interface MailSender {

    /**
     * 向指定的邮箱发送指定主题和内容的邮件
     *
     * @param to      邮箱
     * @param subject 主题
     * @param content 内容
     */
    void sendSimpleText(@Email String to, String subject, String content);

}
