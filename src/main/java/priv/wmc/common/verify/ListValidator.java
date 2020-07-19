package priv.wmc.common.verify;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import priv.wmc.common.enums.EnumDefine;
import priv.wmc.common.utils.HashCollectionUtils;

/**
 * 自定义List校验器
 *
 * @author 王敏聪
 * @date 2019/11/6 14:53
 */
public class ListValidator implements ConstraintValidator<NoRepeat, List<?>> {

    @Override
    public void initialize(NoRepeat constraintAnnotation) {
        // do nothing
    }

    @Override
    public boolean isValid(List<?> list, ConstraintValidatorContext context) {
        // 为空不校验
        if (list == null) {
            return true;
        }

        // 利用HashSet的快速查找，实现查重
        Set<Object> set = new HashSet<>(HashCollectionUtils.getAppropriateSize(list));
        Set<Object> duplicateSet = new HashSet<>(16);

        for (Object local : list) {
            if (!set.add(local)) {
                duplicateSet.add(local);
            }
        }

        if (!duplicateSet.isEmpty()) {
            if (EnumDefine.class.isAssignableFrom(duplicateSet.iterator().next().getClass())) {
                duplicateSet = duplicateSet
                    .stream()
                    .map(duplicate -> ((EnumDefine)duplicate).getValue())
                    .collect(Collectors.toSet());
            }

            changeTipMessage(context, "列表中包含重复元素：" + duplicateSet);
            return false;
        }

        return true;
    }

    private void changeTipMessage(ConstraintValidatorContext context, String tip) {
        // 禁用默认提示
        context.disableDefaultConstraintViolation();
        // 修改错误提示
        context.buildConstraintViolationWithTemplate(tip).addConstraintViolation();
    }

}
