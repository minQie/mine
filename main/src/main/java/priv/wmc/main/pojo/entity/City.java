package priv.wmc.main.pojo.entity;

import lombok.Getter;
import lombok.Setter;
import priv.wmc.main.base.entity.BaseEntity;

/**
 * 市
 *
 * @author Wang Mincong
 * @date 2020-09-18 11:12:35
 */
@Getter
@Setter
public class City extends BaseEntity<City> {

    /**
     * 名称
     */
    String name;

    /** 省id */
    Long provinceId;

}
