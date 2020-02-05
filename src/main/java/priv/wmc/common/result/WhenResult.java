package priv.wmc.common.result;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

/**
 * 基础结果集 - id、whenCreated、whenModified
 *
 * @author 王敏聪
 * @date 2019/12/22 21:44
 */
@Getter
@Setter
public class WhenResult extends IdResult {

    Instant whenCreated;

    Instant whenModified;

}
