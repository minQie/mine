package priv.wmc.main.pojo.dto.result;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;
import priv.wmc.main.pojo.entity.City;

/**
 * 区
 *
 * @author 王敏聪
 * @date 2019/12/16 16:58
 */
@Setter
@Getter
@ToString
public class CityResultDTO {

    /**
     * 名称
     */
    String name;

    public CityResultDTO(City city) {
        BeanUtils.copyProperties(city, this);
    }

}
