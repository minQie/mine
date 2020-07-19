package priv.wmc.common.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * JSR303显示校验工具类
 *
 * @author Wang Mincong
 * @date 2020-06-12 14:29:51
 */
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public final class ValidateUtils {

    private final Validator validator;

    /**
     * 对指定POJO的JSR303校验规则定义的字段，进行校验，并返回违反校验规则结果集
     *
     * @param toBeValidPojo 要校验的实体
     * @return 校验结果 key：违反的字段名 value：校验的实体类型
     */
    public <T> Map<String, String> valid(T toBeValidPojo) {
        // 校验
        Set<ConstraintViolation<T>> constraintViolationSet = validator.validate(toBeValidPojo);

        // 封装校验结果
        Map<String, String> constraintViolationResultMap = new HashMap<>(Math.max((int) (constraintViolationSet.size()/.75f) + 1, 16));
        constraintViolationSet.forEach(violation -> constraintViolationResultMap.put(violation.getPropertyPath().toString(), violation.getMessage()));

        return constraintViolationResultMap;
    }

    /**
     * 对指定POJO的JSR303校验规则定义的字段，进行校验，并返回违反校验规则结果集
     *
     * @param toBeValidPojo 要校验的实体
     * @param validGroup 要校验的分组
     * @return 校验结果 key：违反的字段名 value：校验的实体类型
     */
    public <T> Map<String, String> valid(T toBeValidPojo, Class<?> validGroup) {
        // 校验
        Set<ConstraintViolation<T>> constraintViolationSet = validator.validate(toBeValidPojo, validGroup);

        // 封装校验结果
        Map<String, String> constraintViolationResultMap = new HashMap<>(Math.max((int) (constraintViolationSet.size()/.75f) + 1, 16));
        constraintViolationSet.forEach(violation -> constraintViolationResultMap.put(violation.getPropertyPath().toString(), violation.getMessage()));

        return constraintViolationResultMap;
    }

    /**
     * 对指定POJO的JSR303校验规则定义的字段，进行校验
     *
     * @param toBeValidPojo 要校验的实体
     * @param toBeValidClazz 要校验的分组类型
     * @param ifHaveConstraintViolation 如果存在违反规则的字段，要做的事
     * @param <T> 泛型
     */
    public <T> void valid(T toBeValidPojo, Class<T> toBeValidClazz, Runnable ifHaveConstraintViolation) {
        Set<ConstraintViolation<T>> constraintViolationSet = validator.validate(toBeValidPojo, toBeValidClazz);
        if (!CollectionUtils.isEmpty(constraintViolationSet)) {
            ifHaveConstraintViolation.run();
        }
    }

}
