package priv.wmc.main.test.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 将自定义切点织入当前项目被{@link Aop @Aop}修饰service方法的切面定义
 * 
 * @author Wang Mincong
 * @date 2020-08-14 10:36:45
 */
@Slf4j
@Aspect
@Component
public class AopAnnotationServiceMethodAspect {

    public static final String TWO_ARGUMENT_EXPRESSION = "param1, param2";
    /** 定义一个必须包含两个参数的切点 */
    public static final String TWO_ARGUMENT_POINTCUT_EXPRESSION = "execution(* priv.wmc.main.service.*Service.*(..)) && args(" + TWO_ARGUMENT_EXPRESSION + ")";
    /** 再定义一个不限参数个数的切点 */
    public static final String POINTCUT_EXPRESSION = "execution(* priv.wmc.main.service.*Service.*(..))";

    /** 一个切面定义的类中，切点可以是不同规则的 */
    @Pointcut(POINTCUT_EXPRESSION)
    public void pc2() {
        // DO NOTHING
    }
    @Before(value = "pc2()")
    public void before() {
        log.info("不限参数个数的前置通知!");
    }

    /**
     * 指定一个空方法体的方法为切入点，定义切点表达式
     *
     * <p>以下面表达式为例，argsNames不能省略，且值只能从前面的参数表达式中的参数命名来，不能有缺少（顺序不作要求），否则编译不通过
     *
     * <p>注解内部是按照名称传值的，其他都是按照顺序传值的：<a>https://www.cnblogs.com/dand/p/10283247.html</a>
     * <p>（下面命名风格不统一只是为了演示里边的原理，如果注解中定义的参数名和方法实际形参名相同，是不需要为下面相关的注解指定argNames属性的）
     */
     @Pointcut(value = TWO_ARGUMENT_POINTCUT_EXPRESSION, argNames = TWO_ARGUMENT_EXPRESSION)
     public void pc(Object obj1, Object obj2) {
         // DO NOTHING
     }

    /** 前置通知 */
    @Before(value = "@annotation(aop) && pc(p1 ,p2)", argNames = "aop, p1, p2")
    public void before(JoinPoint jp, Aop aop, Object obj1, Object obj2) {
        log.info("前置通知!");
    }

    /** 环绕通知 */
    @Around(value = "@annotation(aop) && pc(p1 ,p2)", argNames = "aop, p1, p2")
    public Object around(ProceedingJoinPoint pjp, Aop aop, Object obj1, Object obj2) throws Throwable {
        log.info("环绕通知之前");
        Object proceed = pjp.proceed();
        log.info("环绕通知之后");
        return proceed;
    }

    /**
     * 异常通知（获取抛出的异常）
     *
     * 好好理解类上注释说的内部按名称传值，层次间按下标传值
     * 1、argNames属性不能缺省
     * 2、会做类型判断，比如下面的argNames写成 e, p1, p2，编译就会报错
     * （下面这样是对的，是idea报的错）
     */
    @AfterThrowing(value = "@annotation(aop) && pc(p1 ,p2)", throwing = "e", argNames = "aop, p1, p2, e")
    public void afterException(JoinPoint jp, Aop aop, Object obj1, Object obj2, Exception exception) {
        log.info("异常通知!");
    }

    /// 那上面的为例子，如果注解中定义的参数名和方法形参名相同的话，可以不用定义argNames属性
//    @AfterThrowing(value = "pc(p1, p2)", throwing = "e")
//    public void afterException(Object p1, Object p2, Exception e) {
//        log.info("异常通知!");
//    }

    /** 后置通知 出现异常不调用（获取方法的返回值） */
    @AfterReturning(value = "@annotation(aop) && pc(p1 ,p2)", returning = "r", argNames = "aop, p1, p2, r")
    public void afterRunning(JoinPoint jp, Aop aop, Object obj1, Object obj2, Object result) {
        log.info("后置通知!(出现异常不调用)");
    }

    /** 后置通知 出现异常也调用 */
    @After(value = "@annotation(aop) && pc(p1 ,p2)", argNames = "aop, p1, p2")
    public void after(JoinPoint jp, Aop aop, Object obj1, Object obj2) {
        log.info("后置通知!(出现异常也调用)");
    }

}
