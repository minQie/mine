package priv.wmc.common.exception;

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

    /**
     * 后台代码层面
     */
    PARAM_NULL(100, "后台人员错误"),

    /**
     * 文件异常
     */
    FILE_UPLOAD_FAIL(10001, "文件上传失败"),
    FILE_NOT_EXIST(10002, "文件不存在"),
    FILE_ALREADY_DELETE(10003, "文件已被删除"),
    CONFIG_SERVER_DOMAIN_CHANGED(10004, "无法获取下载文件的url，文件上传时的服务器域名和现在的不匹配"),
    ;

    private int code;
    private String message;

}
