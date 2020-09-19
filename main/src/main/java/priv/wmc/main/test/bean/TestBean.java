package priv.wmc.main.test.bean;

import javax.annotation.PostConstruct;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import priv.wmc.main.web.controller.TestController;

/**
 * 更多生命周期的拓展见：https://mp.weixin.qq.com/s/w50mW5WnT3Sy-lqe4VbENQ
 *
 * 测试结论：
 * 1、constructor
 * 2、dependency inject
 * 3、@PostConstruct
 * 4、InitializingBean.afterPropertiesSet
 * 5、@Bean(initMethod = "initMethod")
 *
 * @author Wang Mincong
 * @date 2020-09-05 17:24:17
 */
@Slf4j
public class TestBean implements InitializingBean {

    private static final String TEMPLATE = "dependency inject done:";

    @Setter(onMethod_ = @Autowired)
    private TestController testController;

    public TestBean() {
        // + 的优先级 大于 ==
        log.info(TEMPLATE + (testController == null));
        log.info("constructor");
    }

    @PostConstruct
    public void postConstruct() {
        log.info(TEMPLATE + (testController == null));
        log.info("postConstruct");
    }

    @Override
    public void afterPropertiesSet() {
        log.info(TEMPLATE + (testController == null));
        log.info("InitializingBean.afterPropertiesSet");
    }

    public void initMethod() {
        log.info(TEMPLATE + (testController == null));
        log.info("initMethod");
    }

}
