package priv.wmc.pojo.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * 邮件参数
 *
 * @author 王敏聪
 * @date 2019/12/10 13:43
 */
@Getter
@Setter
public class MailForm {

    /**
     * 发送邮件
     */
    @Email
    private String sendTo;

    /**
     * 邮件主题
     */
    @NotBlank
    private String subject;

    /**
     * 邮件内容
     */
    @NotBlank
    private String content;

}
