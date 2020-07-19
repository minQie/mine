package priv.wmc.launch.init;

import priv.wmc.common.result.IdResult;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 省
 *
 * @author 王敏聪
 * @date 2019/12/16 16:54
 */
@Getter
@Setter
public class ProvinceResult extends IdResult {

    /**
     * 名称
     */
    String name;

    /**
     * 包含的市
     */
    List<CityResult> cities;

    /**
     * 构造方法
     *
     * @param province province
     */
    public ProvinceResult(Province province) {
        BeanUtils.copyProperties(province, this);
        this.name = province.getName();
        if (province.getCities() != null) {
            this.cities = province.getCities().stream().map(CityResult::new).collect(Collectors.toList());
        }
    }

}
