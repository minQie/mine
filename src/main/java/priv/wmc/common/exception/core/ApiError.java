package priv.wmc.common.exception.core;

/**
 * 错误相关的枚举都要实现的接口
 *
 * @author 王敏聪
 * @date 2020-01-15 23:24:22
 */
public interface ApiError {

    /**
     * 返回错误信息
     *
     * @return 错误信息
     */
    String getMessage();

}
