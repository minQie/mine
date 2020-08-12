package priv.wmc.pojo.dto.result;

import priv.wmc.common.result.IdResult;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;
import priv.wmc.pojo.entity.City;

/**
 * @author 王敏聪
 * @date 2019/12/16 16:58
 */
@Setter
@Getter
public class CityResultDTO extends IdResult {

    /**
     * 名称
     */
    String name;

    /**
     * 包含的区
     */
    List<CountyResultDTO> counties;

    /**
     * 构造方法
     *
     * @param city city
     */
    public CityResultDTO(City city) {
        BeanUtils.copyProperties(city, this);
        this.name = city.getName();
        if (city.getCounties() != null) {
            this.counties = city.getCounties().stream().map(CountyResultDTO::new).collect(Collectors.toList());
        }
    }

}
