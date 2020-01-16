package com.wmc.common.exception.result;

import com.wmc.common.exception.core.ApiError;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 错误结果集
 *
 * @author 王敏聪
 * @date 2019/11/18 9:32
 */
@Getter
@RequiredArgsConstructor
public class ApiBasicExceptionResult implements ApiError {

    private final String message;

    public ApiBasicExceptionResult(ApiError apiError) {
        this(apiError.getMessage());
    }

}
