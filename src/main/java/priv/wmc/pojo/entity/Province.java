package priv.wmc.pojo.entity;

import priv.wmc.common.domain.BaseIdDomain;
import io.ebean.annotation.Index;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;
import priv.wmc.pojo.entity.City;

/**
 * 省
 *
 * @author 王敏聪
 * @date 2019/12/13 13:40
 */
@Getter
@Setter
@Entity
public class Province extends BaseIdDomain {

    /**
     * 名称
     */
    @Index(unique = true)
    @Column(length = 7, nullable = false)
    String name;

    /**
     * 包含的市
     */
    @OneToMany(mappedBy = "province")
    List<City> cities;

}
