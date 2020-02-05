package priv.wmc.config.notify;

import priv.wmc.common.exception.ApiBasicException;
import priv.wmc.common.util.ExceptionStackTraceUtils;
import priv.wmc.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 当出现ApiException以外类型的异常，邮件提醒配置的后台开发人员
 *
 * @author 王敏聪
 * @date 2019/12/6 15:56
 */
@ConditionalOnProperty(value = "app.exception-mail-notify.on", havingValue = "true")
@RestControllerAdvice(annotations = RestController.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class EmailNotifyWhenExceptionOccur {

    private final MailService mailService;

    @ExceptionHandler(Exception.class)
    public Object handle(Exception e) {
        String stackTrace = ExceptionStackTraceUtils.getStackTrace(e);
        if (!(e instanceof ApiBasicException)) {
            mailService.exceptionNotify(e, stackTrace);
        }
        return stackTrace;
    }

}
