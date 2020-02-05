package priv.wmc.common.exception;

import priv.wmc.common.exception.core.ApiError;
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
     * 枚举反序列化失败
     */
    ENUM_DESERIALIZE_FAIL("枚举反序列化失败"),

    /**
     * jsr303拓展，自定义正则校验
     */
    VERIFY_NOT_BLANK("不能为空"),
    VERIFY_NAME_ILLEGAL("名称格式错误"),
    VERIFY_CODE_ILLEGAL("编号格式错误"),
    VERIFY_EMAIL_ILLEGAL("邮箱格式错误"),
    VERIFY_PHONE_ILLEGAL("号码格式错误"),

    /**
     * 后台代码层面
     */
    PARAM_NULL("后台人员错误"),

    /**
     * 文件异常
     */
    FILE_UPLOAD_FAIL("文件上传失败"),
    FILE_NOT_EXIST("文件不存在"),
    FILE_ALREADY_DELETE("文件已被删除"),
    CONFIG_SERVER_DOMAIN_CHANGED("无法获取下载文件的url，文件上传时的服务器域名和现在的不匹配"),
    ;

    private String message;

}
