package com.wmc.service;

import com.wmc.common.util.DateUtils;
import com.wmc.config.StaticConfig;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.lang.Nullable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.constraints.Email;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 邮件Service
 *
 * @author 王敏聪
 * @date 2019/11/29 16:23
 */
@Getter
@Setter
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MailService {

    /**
     * 邮件发送者的邮箱
     */
    @Value("${spring.mail.username}")
    private String username;

    /**
     * 邮件发送者的昵称
     */
    @Value("${app.exception-mail-notify.nickname}")
    private String nickname;

    /**
     * 发生异常时，要通知的邮箱
     */
    @Value("${app.exception-mail-notify.send-to}")
    private List<String> sendTo;

    private final JavaMailSender mailSender;

    /**
     * 向指定的邮箱发送指定主题和内容的邮件
     *
     * @param to      邮箱
     * @param subject 主题
     * @param content 内容
     */
    public void sendSimpleText(@Email String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(nickname + "<" + username + ">");
        message.setSubject(subject);
        message.setText(content);
        message.setTo(to);

        mailSender.send(message);
    }

    /**
     * 向指定的邮箱发送指定主题和内容的邮件
     *
     * @param to                 邮箱
     * @param subject            主题
     * @param content            内容
     * @param attachmentInfoList 附件内容
     */
    @SafeVarargs
    public final void sendSimpleTextWithAttachment(String to, String subject, String content, Pair<String, Object>... attachmentInfoList) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setFrom(nickname + "<" + username + ">");
        helper.setSubject(subject);
        helper.setText(content);
        helper.setTo(to);

        // 添加附件
        if (attachmentInfoList != null) {
            for (Pair<String, Object> attachmentInfo : attachmentInfoList) {
                helper.addAttachment(attachmentInfo.getKey(), new ByteArrayResource(attachmentInfo.getValue().toString().getBytes(StandardCharsets.UTF_8)));
            }
        }

        mailSender.send(mimeMessage);
    }

    /**
     * 向指定的邮箱发送指定主题和内容的邮件
     *
     * @param to         邮箱
     * @param subject    主题
     * @param content    内容
     * @param attachment 附件
     */
    public final void sendSimpleTextWithAttachment(String to, String subject, @Nullable String content, File attachment) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setFrom(nickname + "<" + username + ">");
        helper.setSubject(subject);
        helper.setText(content == null ? "" : content);
        helper.setTo(to);

        // 添加附件
        if (attachment != null) {
            helper.addAttachment("attachment", attachment);
        }

        mailSender.send(mimeMessage);
    }

    /**
     * 发生了非ApiBasicException的异常，提示配置的邮箱
     *
     * @param stackTrace 发生的非ApiBasicException的堆栈信息
     */
    @Async
    public void exceptionNotify(Exception e, String stackTrace) {
        if (sendTo != null && sendTo.size() != 0) {
            String attachmentName = DateUtils.getDateString(StaticConfig.DATE_TIME_PATTERN) + ".log";
            try {
                for (String email : sendTo) {
                    this.sendSimpleTextWithAttachment(email, "有bug咯", e.getMessage(), Pair.of(attachmentName, stackTrace));
                }
            } catch (Exception ignore) {
            }
        }
    }

}
