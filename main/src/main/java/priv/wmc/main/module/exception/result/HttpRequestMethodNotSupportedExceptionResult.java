package priv.wmc.main.module.exception.result;

import lombok.Getter;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import priv.wmc.main.module.exception.ApiErrorCodes;

/**
 * @author Wang Mincong
 * @date 2020-08-11 11:19:48
 */
@Getter
public class HttpRequestMethodNotSupportedExceptionResult extends ApiBasicExceptionResult {

    private final String method;

    private final String[] supportedMethods;

    public HttpRequestMethodNotSupportedExceptionResult(HttpRequestMethodNotSupportedException ex) {
        super(ApiErrorCodes.METHOD_NOT_SUPPORTED);
        method = ex.getMethod();
        supportedMethods = ex.getSupportedMethods();
    }

}
