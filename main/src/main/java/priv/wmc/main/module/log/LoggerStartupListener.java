package priv.wmc.main.module.log;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.LoggerContextListener;
import ch.qos.logback.core.Context;
import ch.qos.logback.core.spi.ContextAwareBase;
import ch.qos.logback.core.spi.LifeCycle;

/**
 * <h2>LogBack的参数监听器（需要在相应的logback日志配置文件中引入才能够起作用）</h2>
 *
 * @author Wang Mincong
 * @date 2020-08-11 14:57:07
 */
public class LoggerStartupListener extends ContextAwareBase implements LoggerContextListener, LifeCycle {

    @Override
    public void start() {
        Context context = getContext();
        // 给context设置的键值对，在配置文件中可以直接通过像取变量值的方式取值
        context.putProperty("xxx", "xxx");
    }

    @Override
    public void stop() {
        // do nothing
    }

    @Override
    public boolean isStarted() {
        return false;
    }

    @Override
    public boolean isResetResistant() {
        return false;
    }

    @Override
    public void onStart(LoggerContext context) {
        // do nothing
    }

    @Override
    public void onReset(LoggerContext context) {
        // do nothing
    }

    @Override
    public void onStop(LoggerContext context) {
        // do nothing
    }

    @Override
    public void onLevelChange(Logger logger, Level level) {
        // do nothing
    }
}
