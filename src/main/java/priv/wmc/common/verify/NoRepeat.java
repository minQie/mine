package priv.wmc.common.verify;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import priv.wmc.common.verify.validator.ListValidator;

/**
 * 修饰请求参数类型为List，对包含的元素是否有重复进行校验
 *
 * @author 王敏聪
 * @date 2019/11/6 11:43
 */
@Constraint(validatedBy = ListValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
public @interface NoRepeat {

    String message() default "列表中含有重复元素";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
