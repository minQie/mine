package com.wmc.web.controller;

import com.wmc.common.enums.MyEnumInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 王敏聪
 * @date 2020-01-16 11:52:18
 */
@AllArgsConstructor
public enum TestEnum implements MyEnumInterface {

    /** 默认 */
    DEFAULT(0, "默认");

    @Getter
    int value;

    @Getter
    String description;

}
