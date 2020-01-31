package com.wmc.common.domain;

import io.ebean.Model;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * 带有id的模型基类
 *
 * @author 王敏聪
 * @date 2019/11/18 10:26
 */
@Getter
@Setter
@MappedSuperclass
public abstract class BaseIdDomain extends Model {

    @Id
    long id;

}
