package priv.wmc.main.pojo.dto.result;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;
import priv.wmc.main.pojo.entity.Province;

/**
 * 省
 *
 * @author 王敏聪
 * @date 2019/12/16 16:54
 */
@Getter
@Setter
@ToString
public class ProvinceResultDTO {

    /**
     * 名称
     */
    String name;

    public ProvinceResultDTO(Province province) {
        BeanUtils.copyProperties(province, this);
    }

}
