package priv.wmc.launch.init;

import priv.wmc.common.result.IdResult;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

/**
 * 区
 *
 * @author 王敏聪
 * @date 2019/12/16 17:01
 */
@Getter
@Setter
public class CountyResult extends IdResult {

    /**
     * 名称
     */
    String name;

    /**
     * 构造方法
     *
     * @param county county
     */
    public CountyResult(County county) {
        BeanUtils.copyProperties(county, this);
        this.name = county.getName();
    }

}
