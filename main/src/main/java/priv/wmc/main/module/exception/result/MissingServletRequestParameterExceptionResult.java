package priv.wmc.main.module.exception.result;

import org.springframework.web.bind.MissingServletRequestParameterException;
import priv.wmc.main.module.exception.ApiErrorCodes;

/**
 * @author Wang Mincong
 * @date 2020-08-11 11:30:54
 */
public class MissingServletRequestParameterExceptionResult extends ApiBasicExceptionResult {

    private final String param;

    public MissingServletRequestParameterExceptionResult(MissingServletRequestParameterException e) {
        super(ApiErrorCodes.PARAM_MISSING);
        param = e.getParameterName();
    }
}
