package priv.wmc.config.valid;

import java.lang.reflect.Method;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.validation.executable.ExecutableValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

/**
 * @author Wang Mincong
 * @date 2020-08-11 11:39:19
 */
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MethodValidationInterceptor implements MethodInterceptor {

    private final Validator validator;

//    @Override
//    public Object invoke(MethodInvocation invocation) throws Throwable {
//        // 无需增强的方法，直接跳过
//        if (isFactoryBeanMetadataMethod(invocation.getMethod())) {
//            return invocation.proceed();
//        }
//        // 获取分组信息
//        Class<?>[] groups = determineValidationGroups(invocation);
//        ExecutableValidator execVal = this.validator.forExecutables();
//        Method methodToValidate = invocation.getMethod();
//        Set<ConstraintViolation<Object>> result = null;
//        try {
//            // 方法入参校验，最终还是委托给Hibernate Validator来校验
//            result = execVal.validateParameters(invocation.getThis(), methodToValidate, invocation.getArguments(), groups);
//        }
//        catch (IllegalArgumentException ex) {
//            log.error("", ex);
//        }
//        //有异常直接抛出
//        if (!result.isEmpty()) {
//            throw new ConstraintViolationException(result);
//        }
//        // 真正的方法调用
//        Object returnValue = invocation.proceed();
//        // 对返回值做校验，最终还是委托给Hibernate Validator来校验
//        result = execVal.validateReturnValue(invocation.getThis(), methodToValidate, returnValue, groups);
//        // 有异常直接抛出
//        if (!result.isEmpty()) {
//            throw new ConstraintViolationException(result);
//        }
//        return returnValue;
//    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        return null;
    }
}
