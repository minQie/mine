CREATE TABLE `province` (
    `id` BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT '主键id',

    `name` varchar(64) NOT NULL COMMENT '名称',

    `version` BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '数据版本号',
    `gmt_create`DATETIME NOT NULL COMMENT '创建时间',
    `gmt_modified` DATETIME COMMENT '更新时间',
    `deleted` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '删除标识'
) COMMENT = '省表';

CREATE TABLE `county` (
    `id` BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT '主键id',

    `name` varchar(64) NOT NULL COMMENT '名称',
    `province_id` BIGINT UNSIGNED NOT NULL COMMENT '省份id',

    `version` BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '数据版本号',
    `gmt_create`DATETIME NOT NULL COMMENT '创建时间',
    `gmt_modified` DATETIME COMMENT '更新时间',
    `deleted` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '删除标识'
) COMMENT = '市表';

CREATE TABLE `city` (
    `id` BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT '主键id',

    `name` varchar(64) NOT NULL COMMENT '名称',
    `county_id` BIGINT UNSIGNED NOT NULL COMMENT '市区id',

    `version` BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '数据版本号',
    `gmt_create`DATETIME NOT NULL COMMENT '创建时间',
    `gmt_modified` DATETIME COMMENT '更新时间',
    `deleted` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '删除标识'
) COMMENT = '城市表';