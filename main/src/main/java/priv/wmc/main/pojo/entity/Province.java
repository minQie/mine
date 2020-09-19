package priv.wmc.main.pojo.entity;

import lombok.Getter;
import lombok.Setter;
import priv.wmc.main.base.entity.BaseEntity;

/**
 * 省
 *
 * @author Wang Mincong
 * @date 2020-09-18 11:12:54
 */
@Getter
@Setter
public class Province extends BaseEntity<Province> {

    /**
     * 名称
     */
    String name;

}
