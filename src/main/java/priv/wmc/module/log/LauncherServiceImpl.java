package priv.wmc.module.log;

import com.google.auto.service.AutoService;
import java.util.Properties;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.util.StringUtils;

/**
 * 是否开启ELK模块的关键
 *
 * @author Wang Mincong
 * @date 2020-08-11 15:52:20
 */
@AutoService(LauncherService.class)
public class LauncherServiceImpl implements LauncherService {

    @Override
    public void launcher(SpringApplicationBuilder builder, String profile) {
        Properties props = System.getProperties();

        String key = "blade.log.elk.destination";
        String value = "127.0.0.1:8080";

        if (StringUtils.isEmpty(props.getProperty(key))) {
            props.setProperty(key, value);
        }
    }

}
