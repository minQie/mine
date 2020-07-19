package priv.wmc.config.schedule;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * 是否开启定时任务<br>
 * Spring默认提供的定时任务开关是配置类的@EnableScheduling，这里希望能够通过配置文件中的配置来决定是否开启定时任务
 *
 * @author Wang Mincong
 * @date 2020-06-17 10:47:33
 */
public class SchedulingCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return Boolean.parseBoolean(context.getEnvironment().getProperty("app.schedule-enable"));
    }

}
