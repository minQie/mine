package priv.wmc.pojo.dto.result;

import priv.wmc.common.result.IdResult;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;
import priv.wmc.pojo.entity.Province;

/**
 * 省
 *
 * @author 王敏聪
 * @date 2019/12/16 16:54
 */
@Getter
@Setter
public class ProvinceResultDTO extends IdResult {

    /**
     * 名称
     */
    String name;

    /**
     * 包含的市
     */
    List<CityResultDTO> cities;

    /**
     * 构造方法
     *
     * @param province province
     */
    public ProvinceResultDTO(Province province) {
        BeanUtils.copyProperties(province, this);
        this.name = province.getName();
        if (province.getCities() != null) {
            this.cities = province.getCities().stream().map(CityResultDTO::new).collect(Collectors.toList());
        }
    }

}
