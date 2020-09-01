package priv.wmc.main.pojo.dto.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 邮件参数
 *
 * @author 王敏聪
 * @date 2019/12/10 13:43
 */
@Data
public class MailFormDTO {

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
