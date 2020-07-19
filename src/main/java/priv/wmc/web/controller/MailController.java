package priv.wmc.web.controller;

import priv.wmc.pojo.form.MailForm;
import priv.wmc.modules.notify.EmailSender;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.File;

/**
 * 邮件服务
 *
 * @author 王敏聪
 * @date 2019/11/27 18:39
 */
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
    public boolean sendEmail(@RequestBody @Valid MailForm form, @RequestPart(value = "attachment", required = false) File attachment) {
        try {
            emailSender.sendSimpleTextWithAttachment(form.getSendTo(), form.getSubject(), form.getContent(), attachment);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
