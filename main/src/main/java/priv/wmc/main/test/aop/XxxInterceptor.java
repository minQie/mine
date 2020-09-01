package priv.wmc.main.test.aop;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author Wang Mincong
 * @date 2020-08-14 11:07:55
 */
@Slf4j
public class XxxInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        log.info("拦截器方法执行了");
        return invocation.proceed();
    }
}
