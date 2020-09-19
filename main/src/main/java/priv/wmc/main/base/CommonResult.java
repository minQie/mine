package priv.wmc.main.base;

import lombok.Getter;
import priv.wmc.main.module.result.ApiErrorInterface;
import priv.wmc.main.module.result.StatusEnum;

/**
 * 所有 restful 接口返回返回的数据，最终应该该被包装成当前类型，再交由 Jackson 序列化
 *
 * @author Wang Mincong
 * @date 2020-09-18 22:05:36
 */
@Getter
public class CommonResult<T> {

    private final StatusEnum status;

    private final ApiErrorInterface apiError;

    private final T resultBody;

    /**
     * normal
     */
    public CommonResult(T resultBody) {
        this(StatusEnum.SUCCESS, null, resultBody);
    }

    /**
     * exception
     */
    public CommonResult(ApiErrorInterface apiError) {
        this(StatusEnum.FAIL, apiError, null);
    }

    /**
     * exception
     */
    public CommonResult(ApiErrorInterface apiError, T extraMessage) {
        this(StatusEnum.FAIL, apiError, extraMessage);
    }

    /**
     * basic
     */
    public CommonResult(StatusEnum status, ApiErrorInterface apiError, T resultBody) {
        this.status = status;
        this.apiError = apiError;
        this.resultBody = resultBody;
    }

}
