package com.wmc.config.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 好像没必要 - 弃用
 * （CommandLineRunner：允许Springboot在初始化完所有的Bean后，指定要执行的操作）
 * 为了文件上传、下载接口做好文件目录初始化的准备
 *     - 如果存在指定目录什么都不做
 *     - 如果不存在就创建，创建失败就抛出异常（项目启动失败）
 *
 * 有多个CommandLineRunner时，@Order可以指定他们的执行顺序
 *
 * @author 王敏聪
 * @date 2019/11/17 17:25
 */
@Order(1)
@Component
@Deprecated
public class UploadDirectoryInitCommandLineRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {

    }

}
