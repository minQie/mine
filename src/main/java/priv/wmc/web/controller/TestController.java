package priv.wmc.web.controller;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 王敏聪
 * @date 2020-01-16 11:50:33
 */
@RequestMapping("/test")
@RestController
@Validated
public class TestController {

    @GetMapping("/get/{id}")
    public void getTest(@PathVariable @Min(10) Long id, @Min(10) Integer index) {

    }

    @PostMapping("/post")
    public void postTest(@Valid @RequestBody List<TestForm> forms) {

    }

}

@Getter
@Setter
class TestForm {

    @NotNull
    String text;

}
