package priv.wmc.common.exception.result;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.validation.ConstraintViolation;
import lombok.Getter;
import priv.wmc.common.exception.ApiErrorCodes;
import priv.wmc.common.utils.HashCollectionUtils;

/**
 * @author Wang Mincong
 * @date 2020-08-11 09:00:56
 */
@Getter
public class ConstraintViolationExceptionResult extends ApiBasicExceptionResult {

    private final Map<String, String> fields;

    public ConstraintViolationExceptionResult(Set<ConstraintViolation<?>> constraintViolationSet) {
        super(ApiErrorCodes.PARAM_NOT_VALID);
        fields = new HashMap<>(HashCollectionUtils.getAppropriateSize(constraintViolationSet));
        constraintViolationSet.forEach(violation -> fields.put(violation.getPropertyPath().toString(), violation.getMessage()));
    }

}
