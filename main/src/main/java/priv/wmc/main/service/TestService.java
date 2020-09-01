package priv.wmc.main.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import priv.wmc.main.test.aop.Aop;

/**
 * @author Wang Mincong
 * @date 2020-08-13 20:53:05
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class TestService {

    @Aop
    public String aopTest(String s1, String s2) {
        log.info("目标方法执行");
        return "3";
    }

}
