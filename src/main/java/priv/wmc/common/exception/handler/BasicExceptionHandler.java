package priv.wmc.common.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import priv.wmc.common.exception.ApiErrorCodes;
import priv.wmc.common.exception.result.ApiExceptionResult;
import priv.wmc.common.exception.result.HttpRequestMethodNotSupportedExceptionResult;
import priv.wmc.common.exception.result.MethodArgumentTypeMismatchExceptionResult;
import priv.wmc.common.exception.result.MissingServletRequestParameterExceptionResult;

/**
 * @author Wang Mincong
 * @date 2020-08-11 09:26:18
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
@RestControllerAdvice
public class BasicExceptionHandler {

    /**
     * 需要请求体参数，但是请求体为空会匹配到该异常
     */
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public Object handle(HttpMessageNotReadableException ex) {
        return new ApiExceptionResult(ApiErrorCodes.MESSAGE_NOT_READABLE, ex.getMessage());
    }

    /**
     * 处理请求参数缺失错误
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Object handle(MissingServletRequestParameterException ex) {
        return new MissingServletRequestParameterExceptionResult(ex);
    }

    /**
     * 请求方式不支持
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Object handle(HttpRequestMethodNotSupportedException ex) {
        return new HttpRequestMethodNotSupportedExceptionResult(ex);
    }

    /**
     * 方法参数类型错误
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Object handle(MethodArgumentTypeMismatchException ex) {
        return new MethodArgumentTypeMismatchExceptionResult(ex);
    }

}
