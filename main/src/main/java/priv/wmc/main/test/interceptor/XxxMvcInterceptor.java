package priv.wmc.main.test.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import priv.wmc.main.web.controller.TestController;

/**
 * 测试拦截器
 *
 * @author Wang Mincong
 * @date 2020-08-12 16:58:20
 */
@Slf4j
@Component
public class XxxMvcInterceptor implements HandlerInterceptor {

    @Autowired
    public TestController testController;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof HandlerMethod) {
            log.debug("MVC拦截器方法执行了");
//            // 从接口方法上获取指定注解
//            Xxx xxx = ((HandlerMethod) handler).getMethodAnnotation(Xxx.class);
//            // 从接口方法所在的Controller类获取指定注解
//            if (xxx == null) {
//                xxx = handler.getClass().getAnnotation(Xxx.class);
//            }
//
//            // 要调用的方法上，或者要调用方法所在的类上有指定注解才需要做的事
//            if (xxx != null) {
//
//            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        //
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        //
    }

}
