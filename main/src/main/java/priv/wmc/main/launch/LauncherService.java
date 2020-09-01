package priv.wmc.main.launch;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.core.Ordered;

/**
 * 定义了一个普通的接口，不过获取该接口的实现类是通过 spi 机制
 *
 * @author Wang Mincong
 * @date 2020-08-11 15:50:44
 */
public interface LauncherService extends Ordered, Comparable<LauncherService> {

    /**
     * 核心方法：对应用容器进行设置
     *
     * @param builder 容器构建器
     * @param profile 运行环境
     */
    void launcher(SpringApplicationBuilder builder, String profile);

    /**
     * 决定实现类之间的执行顺序
     *
     * @return order
     */
    @Override
    default int getOrder() {
        return 0;
    }

    /**
     * Comparable 的接口默认实现方法
     *
     * @param o 要比较的对象
     * @return result
     */
    @Override
    default int compareTo(LauncherService o) {
        return Integer.compare(this.getOrder(), o.getOrder());
    }

}

