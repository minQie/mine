package priv.wmc.web.controller;

import priv.wmc.common.enums.MyEnumInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

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

@AllArgsConstructor
enum TestEnum implements MyEnumInterface {

    /** 默认 */
    DEFAULT(0, "默认");

    @Getter
    int value;

    @Getter
    String description;

}

@Getter
@Setter
class TestForm {

    TestEnum testEnum;

    Collection<TestEnum> collection;

}
