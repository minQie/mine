package priv.wmc.main.module.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import priv.wmc.main.base.enums.EnumDefine;

/**
 * @author Wang Mincong
 * @date 2020-09-18 22:19:26
 */
@Getter
@AllArgsConstructor
public enum StatusEnum implements EnumDefine {

    SUCCESS(0, "成功"),
    FAIL(1, "失败");

    int value;

    String description;

}
