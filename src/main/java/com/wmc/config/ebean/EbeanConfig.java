package com.wmc.config.ebean;

import io.ebean.config.ServerConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 配置文件配置Ebean行为支持
 *
 * @author 王敏聪
 * @date 2019/11/20 11:16
 */
@Component
@ConfigurationProperties("ebean")
public class EbeanConfig extends ServerConfig {

}
