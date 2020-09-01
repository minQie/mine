package priv.wmc.main.test.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 就是在{@link AopAnnotationServiceMethodAspect}的基础上，想办法将，注解定义在通用切点上边
 *
 * @author Wang Mincong
 * @date 2020-08-14 10:36:45
 */
@Slf4j
//@Aspect
@Component
public class AopAnnotationServiceMethodAspect2 {

    public static final String TWO_ARGUMENT_EXPRESSION = "param1, param2";

     @Pointcut(value = "@annotation(aop) && execution(* priv.wmc.main.service.*Service.*(..)) && args(param1, param2)",
         argNames = "param1, param2, aop")
     public void pc(Object obj1, Object obj2, Aop aop) {
         // DO NOTHING
     }

    @Before(value = "pc(p1 ,p2, aop)", argNames = "aop, p1 ,p2")
    public void before(JoinPoint jp, Aop aop, Object obj1, Object obj2) {
         // pc(p1 ,p2, aop) DemoAspect.class
        log.info("前置通知!");
    }

//    @Around(value = "pc(aop, p1 ,p2)", argNames = "aop, p1, p2")
//    public Object around(ProceedingJoinPoint pjp, Aop aop, Object obj1, Object obj2) throws Throwable {
//        log.info("环绕通知之前");
//        Object proceed = pjp.proceed();
//        log.info("环绕通知之后");
//        return proceed;
//    }
//
//    @AfterThrowing(value = "pc(aop, p1 ,p2)", throwing = "e", argNames = "aop, p1, p2, e")
//    public void afterException(JoinPoint jp, Aop aop, Object obj1, Object obj2, Exception exception) {
//        log.info("异常通知!");
//    }
//
//    @AfterReturning(value = "pc(aop, p1 ,p2)", returning = "r", argNames = "aop, p1, p2, r")
//    public void afterRunning(JoinPoint jp, Aop aop, Object obj1, Object obj2, Object result) {
//        log.info("后置通知!(出现异常不调用)");
//    }
//
//    @After(value = "pc(aop, p1 ,p2)", argNames = "aop, p1, p2")
//    public void after(JoinPoint jp, Aop aop, Object obj1, Object obj2) {
//        log.info("后置通知!(出现异常也调用)");
//    }

}
