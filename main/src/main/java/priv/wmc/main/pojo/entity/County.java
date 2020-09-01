package priv.wmc.main.pojo.entity;

import lombok.Getter;
import lombok.Setter;
import priv.wmc.main.base.entity.BaseEntity;

/**
 * 区
 *
 * @author 王敏聪
 * @date 2019/12/13 13:40
 */
@Getter
@Setter
//@Entity
public class County extends BaseEntity<Province> {

    /**
     * 名称
     */
//    @Index(unique = true)
//    @Column(length = 16, nullable = false)
    String name;

    /**
     * 所属的市
     */
//    @ManyToOne(optional = false)
    City city;

}
