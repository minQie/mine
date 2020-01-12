package com.wmc.common.exception;

import com.wmc.common.exception.core.ApiError;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 定义项目中实际的错误类型
 *
 * @author 王敏聪
 * @date 2019/11/18 10:48
 */
@Getter
@AllArgsConstructor
public enum ApiErrorCodes implements ApiError {

    /**
     * 后台代码层面
     */
    PARAM_NULL(1, "后台人员错误"),

    /**
     * 文件异常
     */
    FILE_UPLOAD_FAIL(1, "文件上传失败"),
    FILE_NOT_EXIST(2, "文件不存在"),
    FILE_ALREADY_DELETE(3, "文件已被删除"),
    CONFIG_SERVER_DOMAIN_CHANGED(4, "无法获取下载文件的url，文件上传时的服务器域名和现在的不匹配"),
    ;

    private int code;

    private String message;

}
