package priv.wmc.main.module.exception;

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
    PARAM_NULL(0, "后台错误"),

    /**
     * 枚举反序列化失败
     */
    ENUM_DESERIALIZE_FAIL(1, "枚举反序列化失败"),
    MESSAGE_NOT_READABLE(2, "无法读取提交内容"),
    PARAM_MISSING(3, "参数缺失"),
    PARAM_ERROR(4, "参数错误"),
    PARAM_NOT_VALID(5, "参数非法"),
    METHOD_NOT_SUPPORTED(6, "方法不支持"),

    /**
     * jsr303拓展，自定义正则校验
     */
    VERIFY_NOT_BLANK(101, "不能为空"),
    VERIFY_NAME_ILLEGAL(102, "名称格式错误"),
    VERIFY_CODE_ILLEGAL(103, "编号格式错误"),
    VERIFY_EMAIL_ILLEGAL(104, "邮箱格式错误"),
    VERIFY_PHONE_ILLEGAL(105, "号码格式错误"),
    ;

    private final int code;
    private final String message;

}
