package com.wmc.config.init;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

/**
 * 项目初始化
 *
 * @author 王敏聪
 * @date 2019/12/5 9:19
 */
@Configuration
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class InitializeApplicationRunner implements ApplicationRunner {

    /**
     * 项目的初始化方法
     *
     * @param args args
     * @throws Exception Exception
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 指定项目初始化好要做的事情
    }

}
