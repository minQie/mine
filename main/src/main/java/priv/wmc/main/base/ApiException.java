package priv.wmc.main.base;

import lombok.Getter;
import priv.wmc.main.module.result.ApiErrorInterface;

/**
 * 基础异常类
 *
 * @author 王敏聪
 * @date 2020-09-18 23:09:42
 */
@Getter
public class ApiException extends RuntimeException implements ApiErrorInterface {

    private final int code;
    private final String message;
    private final Object extraMessage;

    public ApiException(int code, String message) {
        this.code = code;
        this.message = message;
        this.extraMessage = null;
    }

    public ApiException(int code, String message, Object extraMessage) {
        this.code = code;
        this.message = message;
        this.extraMessage = extraMessage;
    }

    public ApiException(ApiErrorInterface apiError) {
        this(apiError.getCode(), apiError.getMessage());
    }

    public ApiException(ApiErrorInterface apiError, Object extraMessage) {
        this(apiError.getCode(), apiError.getMessage(), extraMessage);
    }

}
