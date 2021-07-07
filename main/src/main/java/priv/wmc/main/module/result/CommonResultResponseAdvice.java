package priv.wmc.main.module.result;

import java.util.Arrays;
import java.util.List;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import priv.wmc.main.base.CommonResult;

/**
 * 所有接口方法的调用的返回值都应该按照统一的格式
 *
 * @author Wang Mincong
 * @date 2020-09-18 22:10:46
 */
@RestControllerAdvice
@SuppressWarnings("NullableProblems")
public class CommonResultResponseAdvice implements ResponseBodyAdvice<Object> {



    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(
        Object body,
        MethodParameter returnType,
        MediaType selectedContentType,
        Class<? extends HttpMessageConverter<?>> selectedConverterType,
        ServerHttpRequest request,
        ServerHttpResponse response) {

        // 当 body 为 null，判断的结果为 false
        if (body instanceof CommonResult) {
            return body;
        }
        // 当为 Swagger 请求时，不能进行统一结果的包装处理（也是遇到坑，查询官方文档了解到的）
        List<String> swaggerUris = Arrays.asList("/swagger-resources", "/swagger-resources/configuration/ui", "/v2/api-docs");
        if (swaggerUris.contains(request.getURI().getPath())) {
            return body;
        }

        return new CommonResult<>(body);
    }
}
