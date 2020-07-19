package priv.wmc;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;
import java.util.ServiceLoader;
import java.util.stream.Collectors;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.util.CollectionUtils;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 项目启动类
 * <p>
 * ServletComponentScan注解用于扫描@WebServlet、@WebFilter、@WebListener
 *
 * @author 王敏聪
 * @date 2019年11月15日11:41:09
 */
@EnableAsync
@EnableSwagger2
@EnableSwaggerBootstrapUI
@ServletComponentScan
@SpringBootApplication
public class App {

    public static void main(String[] args) {
///        SpringApplication.run(App.class, args);
        // 替代默认的启动方式，项目进程启动后生成pid文件
        SpringApplication springApplication = new SpringApplication(App.class);
        springApplication.addListeners(new ApplicationPidFileWriter());
        springApplication.run(args);
    }

    public static SpringApplicationBuilder getDefaultConfigBuilder(String appName, Class<?> source, String... args) {
        // ---------------项目环境配置
        ConfigurableEnvironment environment = new StandardEnvironment();
        // 获取配置的环境变量
        String[] activeProfiles = environment.getActiveProfiles();
        // 判断环境:dev、test、prod
        List<String> profiles = Arrays.asList(activeProfiles);
        // 预设的环境
        List<String> presetProfiles = new ArrayList<>(Arrays.asList("dev", "prod", "test"));
        // 交集
        presetProfiles.retainAll(profiles);

        String profile = "dev";
        SpringApplicationBuilder builder = new SpringApplicationBuilder(source);
        if (presetProfiles.size() == 1) {
            profile = presetProfiles.get(0);
        } else if (presetProfiles.size() > 1) {
            throw new IllegalStateException(String.format("读取到的多个环境变量:[%s]", profiles));
        }

        Properties props = System.getProperties();
        props.setProperty("spring.application.name", appName);
        props.setProperty("spring.profiles.active", profile);
        props.setProperty("file.encoding", StandardCharsets.UTF_8.name());
        props.setProperty("spring.main.allow-bean-definition-overriding", "true");

        // 加载自定义组件
        List<LauncherService> launcherList = new ArrayList<>();
        ServiceLoader.load(LauncherService.class).forEach(launcherList::add);
        launcherList.stream()
            .sorted(Comparator.comparing(LauncherService::getOrder))
            .collect(Collectors.toList())
            .forEach(launcherService -> launcherService.launcher(builder, appName, profile, isLocalDev()));
        return builder;
    }

}
