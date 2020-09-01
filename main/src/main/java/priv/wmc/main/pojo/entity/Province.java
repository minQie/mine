package priv.wmc.main.pojo.entity;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import priv.wmc.main.base.entity.BaseEntity;

/**
 * 省
 *
 * @author 王敏聪
 * @date 2019/12/13 13:40
 */
@Getter
@Setter
//@Entity
public class Province extends BaseEntity<Province> {

    /**
     * 名称
     */
//    @Index(unique = true)
//    @Column(length = 7, nullable = false)
    String name;

    /**
     * 包含的市
     */
//    @OneToMany(mappedBy = "province")
    List<City> cities;

}
