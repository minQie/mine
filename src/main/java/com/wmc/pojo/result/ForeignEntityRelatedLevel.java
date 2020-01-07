package com.wmc.pojo.result;

/**
 * 决定返回的结果集内容的策略
 *
 * @Author 王敏聪
 * @Date 2019/11/5 15:10
 */
public enum ForeignEntityRelatedLevel {
    /**
     * 返回关联实体的基本信息
     */
    BASIC,
    /**
     * 返回关联实体的id
     */
    ID,
    /**
     * 不返回
     */
    NONE;
}
