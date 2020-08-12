package priv.wmc.common.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import priv.wmc.common.exception.ApiBasicException;
import priv.wmc.common.exception.ApiException;
import priv.wmc.common.exception.result.ApiBasicExceptionResult;
import priv.wmc.common.exception.result.ApiExceptionResult;

/**
 * <p>所有“@RestController”注解修饰的Controller的方法被调用时，抛出指定类型的异常时，将被以指定的方式处理
 *
 * <p>RestControllerAdvice：Spring指定apo的意见织入目标
 * <p>ResponseStatus：指定被当前类处理后，返回的请求响应
 *
 * <p>不需要在类上添加 @ResponseBody ，详情见{@link RestControllerAdvice}的注释
 *
 * @author 王敏聪
 * @date 2019/11/18 9:53
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
@RestControllerAdvice(annotations = RestController.class)
public class ApiExceptionHandler {

    @ExceptionHandler(ApiBasicException.class)
    public Object handle(ApiBasicException e) {
        return new ApiBasicExceptionResult(e);
    }

    @ExceptionHandler(ApiException.class)
    public Object handle(ApiException e) {
        if (e.getExtraMessage() == null) {
            return new ApiBasicExceptionResult(e);
        }
        return new ApiExceptionResult(e, e.getExtraMessage());
    }

}
