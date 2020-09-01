package priv.wmc.main.module.exception.result;

import priv.wmc.main.module.exception.ApiError;
import lombok.Getter;

/**
 * 错误信息字段：message、extraMessage
 *
 * @author 王敏聪
 * @date 2019/11/18 9:32
 */
@Getter
public class ApiExceptionResult extends ApiBasicExceptionResult {

    private final String extraMessage;

    public ApiExceptionResult(ApiError apiError, String extraMessage) {
        super(apiError);
        this.extraMessage = extraMessage;
    }

}
