package priv.wmc.main.pojo.entity;

import lombok.Getter;
import lombok.Setter;
import priv.wmc.main.base.entity.BaseEntity;

/**
 * 区
 *
 * @author Wang Mincong
 * @date 2020-09-18 11:12:29
 */
@Getter
@Setter
public class County extends BaseEntity<County> {

    /**
     * 名称
     */
    String name;

    /** 市id */
    Long cityId;

}
