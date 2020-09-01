package priv.wmc.main.pojo.dto.result;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;
import priv.wmc.main.pojo.entity.County;

/**
 * 区
 *
 * @author 王敏聪
 * @date 2019/12/16 17:01
 */
@Getter
@Setter
@ToString
public class CountyResultDTO {

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
