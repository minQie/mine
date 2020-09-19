package priv.wmc.main.module.result.handler;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import priv.wmc.common.util.HashCollectionUtils;
import priv.wmc.main.base.CommonResult;
import priv.wmc.main.module.result.ApiErrorCodes;

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
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Object handle(HttpMessageNotReadableException ex) {
        return new CommonResult<>(ApiErrorCodes.MESSAGE_NOT_READABLE, ex.getMessage());
    }

    /**
     * 处理请求参数缺失错误
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Object handle(MissingServletRequestParameterException ex) {
        return new CommonResult<>(ApiErrorCodes.PARAM_MISSING, ex.getParameterName());
    }

    /**
     * 请求方式不支持
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Object handle(HttpRequestMethodNotSupportedException ex) {
        Map<String, Object> map = new HashMap<>(HashCollectionUtils.getAppropriateSize(2));
        map.put("method", ex.getMethod());
        map.put("supportedMethods", ex.getSupportedMethods());

        return new CommonResult<>(ApiErrorCodes.METHOD_NOT_SUPPORTED, map);
    }

    /**
     * 方法参数类型错误
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Object handle(MethodArgumentTypeMismatchException ex) {
        Map<String, Object> map = new HashMap<>(HashCollectionUtils.getAppropriateSize(2));
        map.put("param", ex.getName());
        map.put("cause", ex.getRootCause().getMessage());

        return new CommonResult<>(ApiErrorCodes.PARAM_ERROR, map);
    }

}
