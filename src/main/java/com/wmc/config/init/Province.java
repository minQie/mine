package com.wmc.config.init;

import cn.firstouch.web.data.IdDomain;
import io.ebean.annotation.Index;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * 省
 *
 * @author 王敏聪
 * @date 2019/12/13 13:40
 */
@Getter
@Setter
@Entity
public class Province extends IdDomain {

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
