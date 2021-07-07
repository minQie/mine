package priv.wmc.main.web.controller;

import io.swagger.annotations.Api;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import priv.wmc.main.service.TestForm;
import priv.wmc.main.service.TestService;

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

    /** enum serialize and deserialize test */
    @GetMapping("/getEnum")
    public void getTest(@Valid TestForm testForm) {
        log.info("get 方法执行...");
    }

    @PostMapping("/postEnum")
    public TestForm postTest(@Valid @RequestBody TestForm testForm) {
        log.info("post 方法执行...");
        return testForm;
    }

    /** aop test */
    @GetMapping("/aopTest")
    public void test() {
        testService.aopTest("1", "2");
    }

    /** 转发：原生 方式 */
    @PostMapping("/tomcatDispatcher")
    public void tomcatDispatcher(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("xxx").forward(request, response);
    }

    /** 转发：spring 方式 */
    @PostMapping("/springDispatcher")
    public String springDispatcher() {
        return "forward:xxx";
    }

    /** 重定向：原生 方式 */
    @GetMapping("/tomcatRedirect")
    public void tomcatRedirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("xxx");
    }

    /** 重定向：spring 方式 */
    @GetMapping("/springRedirect")
    public String springRedirect() {
        return "redirect:xxx";
    }
}
