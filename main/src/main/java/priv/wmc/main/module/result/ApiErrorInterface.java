package priv.wmc.main.module.result;

/**
 * 错误相关的枚举都要实现的接口
 *
 * @author 王敏聪
 * @date 2020-01-15 23:24:22
 */
public interface ApiErrorInterface {

    /**
     * 返回错误码
     *
     * @return 错误码
     */
    int getCode();

    /**
     * 返回错误信息
     *
     * @return 错误信息
     */
    String getMessage();

}
