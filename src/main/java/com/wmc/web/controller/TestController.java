package com.wmc.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 王敏聪
 * @date 2020-01-16 11:50:33
 */
@RequestMapping("/test")
@RestController
public class TestController {

    @GetMapping
    public boolean test(TestForm testForm) {
        return true;
    }

}
