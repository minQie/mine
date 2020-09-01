package priv.wmc.main.web.controller;

import io.swagger.annotations.Api;
import java.io.File;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import priv.wmc.main.pojo.dto.form.MailFormDTO;
import priv.wmc.main.module.notify.EmailSender;

/**
 * 邮件服务
 *
 * @author 王敏聪
 * @date 2019/11/27 18:39
 */
@ConditionalOnBean(EmailSender.class)
@Api(tags = "邮件服务")
@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RequestMapping("/common/email")
public class MailController {

    private final EmailSender emailSender;

    /**
     * 发送邮件接口（支持附件）
     *
     * @return 是否成功
     */
    @PostMapping("/sendEmail")
    public boolean sendEmail(@RequestBody @Valid MailFormDTO form, @RequestPart(value = "attachment", required = false) File attachment) {
        try {
            emailSender.sendSimpleTextWithAttachment(form.getSendTo(), form.getSubject(), form.getContent(), attachment);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
