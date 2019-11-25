package com.wmc.common.exception.core;

/**
 * 错误相关的枚举都要实现的接口
 *
 * @author 王敏聪
 * @date 2019年11月18日 09:25:12
 */
public interface ApiError {

    /**
     * 返回
     * @return 错误码
     */
    int getCode();

    /**
     * 返回
     * @return 错误信息
     */
    String getMessage();

}
