package priv.wmc.main.module.result.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import priv.wmc.common.util.HashCollectionUtils;
import priv.wmc.main.base.CommonResult;
import priv.wmc.main.module.result.ApiErrorCodes;

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
    @ExceptionHandler(ConstraintViolationException.class)
    public Object handleConstraintViolationException(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> constraintViolationSet = ex.getConstraintViolations();
        Map<String, String> fields = new HashMap<>(HashCollectionUtils.getAppropriateSize(constraintViolationSet));
        constraintViolationSet.forEach(violation -> fields.put(violation.getPropertyPath().toString(), violation.getMessage()));

        return new CommonResult<>(ApiErrorCodes.PARAM_NOT_VALID, fields);
    }

    /**
     * 参数实体校验不通过
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        Map<String, String> fields = new HashMap<>(HashCollectionUtils.getAppropriateSize(fieldErrors));
        fieldErrors.forEach(f -> fields.put(f.getField(), f.getDefaultMessage()));

        return new CommonResult<>(ApiErrorCodes.PARAM_NOT_VALID, fields);
    }

}
