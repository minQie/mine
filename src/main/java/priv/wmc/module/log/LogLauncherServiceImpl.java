package priv.wmc.module.log;

import com.google.auto.service.AutoService;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.core.Ordered;

/**
 * <h2>自定义logback日志文件路径关键</h2>
 *
 * <p>接下来的内容基于 Java Spi → Springboot Spi，可以参考：<a>https://www.jianshu.com/p/0d196ad23915</a>
 *
 * <p>从这个问题开始：{@link priv.wmc.App#getDefaultConfigBuilder}中调用的 ServiceLoader.load(LauncherService.class)没能加载到当前类以及LauncherServiceImpl
 *
 * <p>在BladeX中观察为什么里边的实现类就会被加载，看完上面的文章，都是因为遵守Spring Spi的机制，发现都在jar包中定义了 META-INF/services/xxx 的类会被类加载器加载
 * 通过断点{@link java.util.ServiceLoader.LazyIterator#hasNextService}，了解到非第三方jar包（当前项目下）的实现类也被加载的原因是，target/classes下边有services
 * 验证：删除该类的@AutoService注解，并删除target文件，重新运行项目，发现ServiceLoader.load(LaunchService.class)就没能够把该类加载出来
 *
 * <p>所以所以问题的矛头指向@AutoService注解的实现原理了:
 * <p>1、全局搜索 AutoService.class，定位到 AutoServiceProcessor （实际上最开始没了解Spi的时候，在瞎找的时候就怀疑了这个类，这个类也是基于spi机制的）
 * <p>2、很简单就能定位到生成文件的代码 - 可实际打断点，重启项目，断点并不命中，真心不知道什么鬼 - 那就是自己了解的太少咯
 * <p>3、该继续学习了解概念了，见：AutoServiceProcessor类上注释写的参照 google auto
 *     概念：<a>https://blog.csdn.net/jjxojm/article/details/90149138?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-1.channel_param&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-1.channel_param</a>
 * 断点无法命中的原因：我们直接在编译器上打的断点是运行时期的断点，而了解完上面的概念后就知道了
 * 一直打断点的代码时注解处理器，是属于编译时期的，参照下面的方法，就能够在编译时期命中断点了
 *     <a>https://www.jianshu.com/p/4d5917f719f1</a>
 *     1、运行项目的方式添加一个 Remote（不用做任何修改）
 *     2、Help → Edit Custom VM Options → 添加 -Dcompiler.process.debug.port=5005 → 重启idea
 *     3、双击shift → debug build process → 开启（重启后会默认关闭）
 *     4、在你想要断点的地方，添加断点
 *     5、构建项目：在状态栏你可以看到构建过程在等待调试器连接
 *     6、最后以 Debugger 的方式运行第“1”步创建的运行方式 → 命中断点
 * PS：关闭第3步、已经以正常模式启动项目
 *
 * 调试走完，验证第二步的思路是正确的，只是系统知识了解的太少，没有关注过代码执行时期的问题
 *
 * <p>4、引出：注解处理器<a>https://www.jianshu.com/p/d31ae166069e</a>（注解处理器只在编译时期处理注解）
 *
 * <p>5、所以基于Springboot的spi机制，自己也要使用 Google Auto Service，通过Annotation Processor，根据注解来自动生成 META-INF/services/...
 *     参考：<a>https://github.com/google/auto/tree/master/service</a>
 *
 * <p>6、最后还出现了问题：在pom.xml中按照 Github 上的 google auto service，
 * 为 Maven 编译插件指定了注解处理器，然后构建项目时 Ebean 和 Lombok 就开始报错了
 *
 * 这里先声明一个点：从 java spi → springboot spi → google auto service → java annotation processor
 * 我们希望通过 java annotation processor 的加载机制来实现自定义的 java spi 加载，
 * 但是不要忘了 java annotation processor 的自身也是通过 java spi 机制实现的
 *
 * 原因：<a>https://www.jianshu.com/p/c04619b37440</a>
 * 解释：默认加载所有jar包中的注解处理器，而显式为 Maven 插件指定注解处理器，那么其他处理器就不走了，
 * 很明显 Ebean 和 Lombok 也实现了基于 spi 机制的 自定义注解处理器，
 * 所以项目构建编译时，一些决定编译行为的定义框架结构构成的注解（编译时期的注解）没能起到作用，框架就无法正常加载和运行了
 *
 * 解决方式一：在 resources/META-INF/services 下边自己创建要通过 spi 机制实现的类
 *     给力的 google auto service 不用了么（×）
 * 解决方式二：在pom.xml中，将所有的第三方jar包的注解处理器都手动配置到编译插件中
 *     这当然没戏，完全不合理：工作量不合理、通过spi机制实现注解处理器概念都被废除了（×）
 *
 * 分析本质：我就就是希望能够给编译时期多添加一个插件，而不是单独制定一个插件，疑问：为什么不配置编译插件，google auto service就不能工作了
 * google auto service 虽说是用来辅助实现 java spi 的，但其本质也是自定义注解处理器啊，处理 @AutoService 注解的注解处理器
 *
 * 没错，问题提对了，发现引入的 google auto service 相关jar包就一个，并且没有 java spi 机制 - 这时同时想到官方说的另外一种引入方式，
 * 是说到用这种可能会导致引入过多的jar包，这时也想到BladeX为什么不直接引入 google auto service，而是选择单独抽取出核心类
 *
 * 不关注什么依赖多引入的问题，换一种引入 google auto service 的方式，问题解决（＜（＾－＾）＞）
 *
 * @author Wang Mincong
 * @date 2020-08-11 16:03:13
 */
@Slf4j
@AutoService(LauncherService.class)
public class LogLauncherServiceImpl implements LauncherService {

    @Override
    public void launcher(SpringApplicationBuilder builder, String profile) {
        Properties props = System.getProperties();

        String logConfig = "classpath:log/logback-" + profile + ".xml";

        log.info("指定日志配置文件：{}", logConfig);
        props.setProperty("logging.config", logConfig);
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

}
