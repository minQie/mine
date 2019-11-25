package com.wmc.config.auth;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Spring Security 自定义配置（先关闭）
 *
 * @author 王敏聪
 * @date 2019/11/19 9:58
 */
@EnableWebSecurity
public class MySecureConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
                .anyRequest().permitAll()
                .and()
                .logout().permitAll();
    }

}
