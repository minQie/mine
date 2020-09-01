package priv.wmc.main.module.notify;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import priv.wmc.main.module.exception.ApiBasicException;
import priv.wmc.common.util.ExceptionStackTraceUtils;

/**
 * 当出现ApiException以外类型的异常，邮件提醒配置的后台开发人员
 *
 * @author 王敏聪
 * @date 2019/12/6 15:56
 */
@ConditionalOnBean(MailNotifier.class)
@RestControllerAdvice(annotations = RestController.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class EmailNotifyExceptionHandler {

    private final MailNotifier mailNotifier;

    @ExceptionHandler(Exception.class)
    public Object handle(Exception e) {
        String stackTrace = ExceptionStackTraceUtils.getStackTrace(e);
        if (!(e instanceof ApiBasicException)) {
            mailNotifier.exceptionNotify(e, stackTrace);
        }
        return stackTrace;
    }

}