package priv.wmc.main.test.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Wang Mincong
 * @date 2020-09-05 17:58:06
 */
@Configuration
public class TestConfiguration {

    @Bean(initMethod = "initMethod")
    public TestBean testBean() {
        // 源码中不推荐使用initMethod，更推荐直接在 Bean 注解修饰的方法中直接调用 initMethod 方法
        return new TestBean();
    }

}
