package priv.wmc;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.SimpleCommandLinePropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.util.StringUtils;
import priv.wmc.module.log.LauncherService;
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
        SpringApplication springApplication = getDefaultConfigBuilder(App.class, args).build();
        // 添加pid文件的生成
        springApplication.addListeners(new ApplicationPidFileWriter());
        springApplication.run(args);
    }

    public static SpringApplicationBuilder getDefaultConfigBuilder(Class<?> source, String... args) {
        ConfigurableEnvironment environment = new StandardEnvironment();
        MutablePropertySources propertySources = environment.getPropertySources();
        propertySources.addFirst(new SimpleCommandLinePropertySource(args));
        propertySources.addLast(new MapPropertySource(StandardEnvironment.SYSTEM_PROPERTIES_PROPERTY_SOURCE_NAME, environment.getSystemProperties()));
        propertySources.addLast(new SystemEnvironmentPropertySource(StandardEnvironment.SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME, environment.getSystemEnvironment()));

        // 根据参数以及自定义规则，决定实际的运行环境
        String[] actualProfileArray = environment.getActiveProfiles();
        List<String> actualProfiles = Arrays.asList(actualProfileArray);

        List<String> presetProfiles = new ArrayList<>(Arrays.asList("dev", "test", "prod"));
        presetProfiles.retainAll(actualProfiles);

        String profile;
        SpringApplicationBuilder builder = new SpringApplicationBuilder(source);
        if (presetProfiles.isEmpty()) {
            profile = "dev";
            presetProfiles.add(profile);
            builder.profiles(profile);
        } else if (presetProfiles.size() == 1) {
            profile = presetProfiles.get(0);
        } else {
            throw new RuntimeException("同时存在环境变量:[" + StringUtils.arrayToCommaDelimitedString(actualProfileArray) + "]");
        }

        Properties props = System.getProperties();
        props.setProperty("spring.profiles.active", profile);
        props.setProperty("file.encoding", StandardCharsets.UTF_8.name());
        props.setProperty("spring.main.allow-bean-definition-overriding", "true");

        // 加载自定义组件：LauncherServiceImpl、LogLauncherServiceImpl
        List<LauncherService> launcherList = new ArrayList<>();
        ServiceLoader.load(LauncherService.class).forEach(launcherList::add);

        launcherList.stream()
            .sorted(Comparator.comparing(LauncherService::getOrder))
            .collect(Collectors.toList())
            .forEach(launcherService -> launcherService.launcher(builder, profile));
        return builder;
    }

}
