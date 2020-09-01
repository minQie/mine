package priv.wmc.main.pojo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import priv.wmc.main.base.enums.EnumDefine;

/**
 * @author Wang Mincong
 * @date 2020-08-18 18:04:45
 */
@Getter
@AllArgsConstructor
public enum GenderEnum implements EnumDefine {

    UNKNOWN(0, "未知"),
    MALE(1, "男"),
    FEMALE(2, "女");

    int value;
    String description;
}
