package priv.wmc.launch.basic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.StandardEnvironment;

/**
 * @author Wang Mincong
 * @date 2020-07-07 21:51:03
 */
public class LogPathConfig {

    /**
     * 目的：
     * 想要让Spring启动时根据默认定义的目录规则找到Logback的日志配置文件，像BladeX那样
     *
     * 框架涉及整理：
     * 虽然直观看上去在BladeX的[log]模板，但实际关联了[auto]模块和[launch]模块，最主要的时[launch]模块的BladeApplication
     *
     * 核心：
     * 1、在
     */
    static {


        Properties props = System.getProperties();
        props.setProperty("logging.config", "classpath:log/logback-" + profile + ".xml");
    }
}
