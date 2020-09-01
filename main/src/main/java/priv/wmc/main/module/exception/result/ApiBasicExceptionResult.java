package priv.wmc.main.module.exception.result;

import lombok.Getter;
import priv.wmc.main.module.exception.ApiError;

/**
 * 错误信息字段：message
 *
 * @author 王敏聪
 * @date 2019/11/18 9:32
 */
@Getter
public class ApiBasicExceptionResult implements ApiError {

    private final int code;
    private final String message;

    public ApiBasicExceptionResult(ApiError apiError) {
        this(apiError.getCode(), apiError.getMessage());
    }

    public ApiBasicExceptionResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
