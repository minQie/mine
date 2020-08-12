package priv.wmc.pojo.entity;

import priv.wmc.common.domain.BaseIdDomain;
import io.ebean.annotation.Index;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import priv.wmc.pojo.entity.City;

/**
 * 区
 *
 * @author 王敏聪
 * @date 2019/12/13 13:40
 */
@Getter
@Setter
@Entity
public class County extends BaseIdDomain {

    /**
     * 名称
     */
    @Index(unique = true)
    @Column(length = 16, nullable = false)
    String name;

    /**
     * 所属的市
     */
    @ManyToOne(optional = false)
    City city;

}
