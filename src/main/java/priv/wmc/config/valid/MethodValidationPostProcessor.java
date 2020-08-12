package priv.wmc.config.valid;

import java.lang.annotation.Annotation;
import javax.validation.Validator;
import lombok.AllArgsConstructor;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.autoproxy.AbstractBeanFactoryAwareAdvisingPostProcessor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.beanvalidation.MethodValidationInterceptor;

/**
 * @author Wang Mincong
 * @date 2020-08-10 10:40:40
 */
@AllArgsConstructor(onConstructor_ = @Autowired)
public class MethodValidationPostProcessor extends AbstractBeanFactoryAwareAdvisingPostProcessor implements InitializingBean {

    /** 要拦截具有特定注解修饰的方法 */
    private static final Class<? extends Annotation> VALIDATED_ANNOTATION_TYPE = Validated.class;

    /** 校验器 */
    private final Validator validator;

    @Override
    public void afterPropertiesSet() {
        // 为所有 @Validated 标注的Bean创建切面
        Pointcut pointcut = new AnnotationMatchingPointcut(VALIDATED_ANNOTATION_TYPE, true);
        // 创建Advisor进行增强
        this.advisor = new DefaultPointcutAdvisor(pointcut, createMethodValidationAdvice(this.validator));
    }

    /**
     * 创建Advice
     */
    protected Advice createMethodValidationAdvice(@Nullable Validator validator) {
        return (validator != null ? new MethodValidationInterceptor(validator) : new MethodValidationInterceptor());
    }

}
