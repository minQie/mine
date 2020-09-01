package priv.wmc.main.service;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import priv.wmc.main.pojo.enums.GenderEnum;

/**
 * @author Wang Mincong
 * @date 2020-08-13 20:54:07
 */
@Getter
@Setter
@ToString
@ApiModel("测试枚举参数")
public class TestForm {

    @NotNull
    @ApiModelProperty("性别")
    GenderEnum gender;

}
