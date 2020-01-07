package com.wmc.config.init;

import com.wmc.common.result.IdResult;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 王敏聪
 * @date 2019/12/16 16:58
 */
@Setter
@Getter
public class CityResult extends IdResult {

    /**
     * 名称
     */
    String name;

    /**
     * 包含的区
     */
    List<CountyResult> counties;

    /**
     * 构造方法
     *
     * @param city city
     */
    public CityResult(City city) {
        BeanUtils.copyProperties(city, this);
        this.name = city.getName();
        if (city.getCounties() != null) {
            this.counties = city.getCounties().stream().map(CountyResult::new).collect(Collectors.toList());
        }
    }

}
