package priv.wmc.modules.notify;

/**
 * @author 王敏聪
 * @date 2020-02-07 09:48:46
 */
public interface MailNotifier {

    /**
     * 解析发生的异常，通过邮件将信息发送给开发者
     * @param e 异常
     * @param stackTrace 异常堆栈信息
     */
    void exceptionNotify(Exception e, String stackTrace);

}
