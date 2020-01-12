package com.wmc.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 项目核心配置类
 *
 * @author 王敏聪
 * @date 2019/11/17 20:31
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties("app")
public class AppConfig {

    /**
     * 项目运行端口
     */
    @Value("${server.port}")
    public String port;

    /**
     * 自定义域名
     */
    public String domain;

    /**
     * 是否生成区域sql
     */
    public Boolean areaSqlGenerate;

    /**
     * 项目实际部署运行的服务器域名
     */
    public static final String SERVER_DOMAIN = "http://127.0.0.1:8081";

    /**
     * 从服务器域名开始的，后面的代理上传文件夹的相对路径（在本项目中就是“下载文件接口”的uri的前段）
     */
    public static final String RELATIVE_PATH = "/upload/";

    /**
     * 项目接受的上传文件的请求，将上传的文件保存到本地的文件目录
     */
    public static final String ABSOLUTE_PATH = "D:/upload/";

    /**
     * 是否开启swagger
     */
    public Boolean swaggerEnable;

    /**
     * api version
     */
    public String apiVersion;

}
