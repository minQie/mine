package priv.wmc.main.module.exception.result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import priv.wmc.main.module.exception.ApiErrorCodes;
import priv.wmc.common.util.HashCollectionUtils;

/**
 * @author Wang Mincong
 * @date 2020-08-11 08:43:30
 */
@Getter
public class MethodArgumentNotValidExceptionResult extends ApiBasicExceptionResult {

    private final Map<String, String> fields;

    public MethodArgumentNotValidExceptionResult(BindingResult result) {
        super(ApiErrorCodes.PARAM_NOT_VALID);

        List<FieldError> fieldErrors = result.getFieldErrors();

        fields = new HashMap<>(HashCollectionUtils.getAppropriateSize(fieldErrors));
        fieldErrors.forEach(f -> fields.put(f.getField(), f.getDefaultMessage()));
    }

}
