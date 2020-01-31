package com.wmc.web.controller;

import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

/**
 * @author 王敏聪
 * @date 2020-01-16 11:52:08
 */
@Getter
@Setter
public class TestForm {

    TestEnum testEnum;

    Collection<TestEnum> collection;

}
