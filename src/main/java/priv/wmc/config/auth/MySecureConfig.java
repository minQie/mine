package priv.wmc.config.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.PrintWriter;

/**
 * Spring Security 自定义配置
 *
 * @author 王敏聪
 * @date 2019/11/19 9:58
 */
@EnableWebSecurity
public class MySecureConfig extends WebSecurityConfigurerAdapter {

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * 在内存中配置登录的用户 - 这里应该从数据库中查出来吧
     * 1、默认 - 用户名：user、密码：启动日志会输出 Using generated security password
     * 2、自定义方式1 - 在配置文件中配置spring.security.user.name、spring.security.user.password
     * 3、自定义方式2 - 如下
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("xiaoming").roles("admin").password(passwordEncoder.encode("123"))
                .and()
                .withUser("xiaohong").roles("user").password(passwordEncoder.encode("123"));
    }

    /**
     * 指定加密器：没有指定，按照上面的用户名和密码登录就会报错
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.addFilterBefore(verifyCodeFilter, UsernamePasswordAuthenticationFilter.class);
        http
            // 一、接口权限拦截相关
            .authorizeRequests()
            // 1、访问特定路径的接口，需要特定的角色
            .antMatchers("/xxx").hasRole("xxx")
            // 2、剩余的其他接口，登录之后就能访问
            .anyRequest().authenticated()
            .and()
            // 二、表单登录相关
            .formLogin()
            // 1、定义登录页面，未登录时，访问一个需要登录才能访问的接口，会自动跳转到该页面
//            .loginPage("/login.html")
            // 2、定义登录接口
//            .loginProcessingUrl("/login")
            // 3、定义登录时，用户名的字段名，默认为 username
            .usernameParameter("uname")
            // 4、定义登录时，用户密码字段名，默认为 password
            .passwordParameter("passwd")
            // 定义登录成功的处理器
            .successHandler((req, resp, authentication) -> {
                resp.setContentType("application/json;charset=utf-8");
                PrintWriter out = resp.getWriter();
                out.write("success");
                out.flush();
            })
            // 定义登录失败的处理器
            .failureHandler((req, resp, exception) -> {
                resp.setContentType("application/json;charset=utf-8");
                PrintWriter out = resp.getWriter();
                out.write("fail");
                out.flush();
            })
            .permitAll()
            .and()
            // 三、登出相关
            .logout()
            // 定义登出接口
            .logoutUrl("/logout")
            // 定义登出失败的处理器
            .logoutSuccessHandler((req, resp, authentication) -> {
                resp.setContentType("application/json;charset=utf-8");
                PrintWriter out = resp.getWriter();
                out.write("logout success");
                out.flush();
            })
            .permitAll()
            .and()
            // 四、允许跨域请求
            .httpBasic()
            .and()
            .csrf().disable();
    }

    /**
     * 如果一个地址不需要拦截，可以使用如下防水
     */
    @Override
    public void configure(WebSecurity webSecurity) {
        webSecurity.ignoring().mvcMatchers("/xxx");
    }

}
