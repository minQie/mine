package priv.wmc.main.module.exception.handler;

import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import priv.wmc.main.module.exception.result.ConstraintViolationExceptionResult;
import priv.wmc.main.module.exception.result.MethodArgumentNotValidExceptionResult;

/**
 * @author Wang Mincong
 * @date 2020-08-10 18:11:59
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
@RestControllerAdvice
public class ValidExceptionHandler {

    /**
     * 字段平铺（无参数实体）校验不通过
     */
    @ExceptionHandler({ConstraintViolationException.class})
    public Object handleConstraintViolationException(ConstraintViolationException ex) {
        return new ConstraintViolationExceptionResult(ex.getConstraintViolations());
    }

    /**
     * 参数实体校验不通过
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public Object handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return new MethodArgumentNotValidExceptionResult(ex.getBindingResult());
    }

}
