package priv.wmc.module.log;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.core.Ordered;

/**
 * 用于组件发现
 *
 * @author Wang Mincong
 * @date 2020-08-11 15:50:44
 */
public interface LauncherService extends Ordered, Comparable<LauncherService> {

    /**
     * 启动时 处理 SpringApplicationBuilder
     *
     * @param builder    SpringApplicationBuilder
     * @param profile    SpringApplicationProfile
     */
    void launcher(SpringApplicationBuilder builder, String profile);

    /**
     * 获取排列顺序
     *
     * @return order
     */
    @Override
    default int getOrder() {
        return 0;
    }

    /**
     * 对比排序
     *
     * @param o LauncherService
     * @return compare
     */
    @Override
    default int compareTo(LauncherService o) {
        return Integer.compare(this.getOrder(), o.getOrder());
    }

}

