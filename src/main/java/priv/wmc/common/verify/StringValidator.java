package priv.wmc.common.verify;

import io.micrometer.core.instrument.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * "Verify" annotation core validator with custom regex
 *
 * @author 王敏聪
 * @date 2020-01-15 23:00:39
 *
 * @see CoreRegexRegulation
 */
public class StringValidator implements ConstraintValidator<Verify, String> {

    private boolean allowBlank;
    private VerifyType verifyType;

    @Override
    public void initialize(Verify constraintAnnotation) {
        verifyType = constraintAnnotation.verifyType();
        allowBlank = constraintAnnotation.allowBlank();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // 非空校验
        if (StringUtils.isBlank(value)) {
            if (allowBlank) {
                return true;
            } else {
                changeTipMessage(context, VerifyType.NOT_BLANK);
                return false;
            }
        }

        // 实际规则的校验
        changeTipMessage(context, verifyType);
        return verifyType.getPattern().matcher(value).matches();
    }

    /**
     * 将校验类型的错误提示应用到注解属性“message”
     */
    private void changeTipMessage(ConstraintValidatorContext context, VerifyType verifyType) {
        // 禁用默认提示
        context.disableDefaultConstraintViolation();
        // 修改错误提示
        context.buildConstraintViolationWithTemplate(verifyType.getErrorCodes().getMessage()).addConstraintViolation();
    }

}
