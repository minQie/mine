package priv.wmc.main.web.controller;

import io.swagger.annotations.Api;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.wmc.main.service.TestService;
import priv.wmc.main.service.TestForm;

/**
 * @author 王敏聪
 * @date 2020-01-16 11:50:33
 */
@Api(tags = "测试模块")
@Slf4j
@Validated
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class TestController {

    private final TestService testService;

    @GetMapping("/getEnum")
    public void getTest(@Valid TestForm testForm) {
        log.info("get 方法执行...");
    }

    @PostMapping("/postEnum")
    public void postTest(@Valid @RequestBody TestForm testForm) {
        log.info("post 方法执行...");
    }

    @GetMapping("/aopTest")
    public void test() {
        testService.aopTest("1", "2");
    }

}
