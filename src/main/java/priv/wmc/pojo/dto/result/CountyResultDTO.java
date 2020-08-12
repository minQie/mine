package priv.wmc.pojo.dto.result;

import priv.wmc.common.result.IdResult;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import priv.wmc.pojo.entity.County;

/**
 * 区
 *
 * @author 王敏聪
 * @date 2019/12/16 17:01
 */
@Getter
@Setter
public class CountyResultDTO extends IdResult {

    /**
     * 名称
     */
    String name;

    /**
     * 构造方法
     *
     * @param county county
     */
    public CountyResultDTO(County county) {
        BeanUtils.copyProperties(county, this);
        this.name = county.getName();
    }

}
