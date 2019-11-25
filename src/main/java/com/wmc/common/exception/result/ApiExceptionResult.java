package com.wmc.common.exception.result;

import com.wmc.common.exception.core.ApiError;
import lombok.Getter;

/**
 * 带有额外信息的错误结果集
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
