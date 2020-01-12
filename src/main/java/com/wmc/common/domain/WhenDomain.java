package com.wmc.common.domain;

import io.ebean.annotation.WhenCreated;
import io.ebean.annotation.WhenModified;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.time.Instant;

/**
 * 带有创建时间和修改时间的基类
 *
 * @author 王敏聪
 * @date 2019/11/18 10:30
 */
@Getter
@Setter
@MappedSuperclass
public abstract class WhenDomain extends IdDomain {

    @WhenCreated
    Instant whenCreated;

    @WhenModified
    Instant whenModified;

}
