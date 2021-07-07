package priv.wmc.main.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;

/**
 * 跨域请求允许
 *
 * @author Wang Mincong
 * @date 2021-03-04 17:44:20
 */
public final class CorsSupport {

    private CorsSupport() {}

    /**
     * 当前方法不限制在什么时间点执行，只要保证在响应之前设置头
     * @param request 请求
     * @param response 响应
     */
    public static boolean handle(HttpServletRequest request, HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        if (HttpMethod.OPTIONS.name().equals(request.getMethod())) {
            response.addHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, HEAD, OPTIONS");
            response.addHeader("Access-Control-Allow-Headers", request.getHeader("access-control-request-headers"));
            response.addHeader("Access-Control-Max-Age", "86400");
            return true;
        }
        return false;
    }
}
