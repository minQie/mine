package com.wmc;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 项目启动类
 * <p>
 * ServletComponentScan注解用于扫描@WebServlet、@WebFilter、@WebListener
 *
 * @author 王敏聪
 * @date 2019年11月15日11:41:09
 */
@EnableSwagger2
@EnableSwaggerBootstrapUI
@ServletComponentScan
@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}
