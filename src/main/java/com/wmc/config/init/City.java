package com.wmc.config.init;

import com.wmc.common.domain.IdDomain;
import io.ebean.annotation.Index;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * 市
 *
 * @author 王敏聪
 * @date 2019/12/13 13:40
 */
@Getter
@Setter
@Entity
public class City extends IdDomain {

    /**
     * 名称
     */
    @Index(unique = true)
    @Column(length = 8, nullable = false)
    String name;

    /**
     * 所属的省
     */
    @ManyToOne(optional = false)
    Province province;

    /**
     * 包含的区
     */
    @OneToMany(mappedBy = "city")
    List<County> counties;

}
