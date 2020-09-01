package priv.wmc.main.pojo.entity;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import priv.wmc.main.base.entity.BaseEntity;

/**
 * 市
 *
 * @author 王敏聪
 * @date 2019/12/13 13:40
 */
@Getter
@Setter
//@Entity
public class City extends BaseEntity<Province> {

    /**
     * 名称
     */
//    @Index(unique = true)
//    @Column(length = 8, nullable = false)
    String name;

    /**
     * 所属的省
     */
//    @ManyToOne(optional = false)
    Province province;

    /**
     * 包含的区
     */
//    @OneToMany(mappedBy = "city")
    List<County> counties;

}
