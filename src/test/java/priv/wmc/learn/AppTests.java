package priv.wmc.learn;

import priv.wmc.service.MailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AppTests {

    @Autowired
    private MailService mailService;

    @Test
    void contextLoads() {
        //        mailService.sendSimpleText("mincong.wang@firstouch.com.cn", "有bug咯", "throw xxxException...");
        //        mailService.sendSimpleText("jianqiu.tan@firstouch.com.cn", "有bug咯", "throw xxxException...");
        //        mailService.sendSimpleText("keqing.zhu@firstouch.com.cn", "朱大仙删库跑路啦", "律师函警告");

        //        try {
        //            mailService.sendSimpleTextWithAttachment(
        //                    "mincong.wang@firstouch.com.cn",
        //                    "Attachment Test",
        //                    "For Test...",
        //                    Pair.of("test.log", "aaaaaaaa"));
        //        } catch (MessagingException e) {
        //            e.printStackTrace();
        //        }
    }

}
