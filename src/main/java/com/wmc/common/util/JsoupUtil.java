package com.wmc.common.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

/**
 * 过滤文本中xss脚本的工具类
 *
 * @author 王敏聪
 * @date 2019年5月6日09:23:30
 */
public class JsoupUtil {

    /** 白名单：使用Jsoup内置的几种常见的白名单 */
    private static final Whitelist WHITE_LIST = Whitelist.simpleText();
    private static final Document.OutputSettings OUTPUT_SETTINGS = new Document.OutputSettings().prettyPrint(false);

    static {
        // 给所有标签添加style属性
        WHITE_LIST.addAttributes(":all", "style");

        // 当<script>标签只有一个时，会<script>标签后面的数据全部过滤，配置的意思是如果找不到成对的标签，就只过滤单个标签，而不用把后面所有的文本都进行过滤。
        WHITE_LIST.preserveRelativeLinks(true);
    }

    /**
     * 核心方法，过滤html标签和js脚本
     *
     * @param content 要过滤的文本
     * @return 过滤后的文本
     */
    public static String clean(String content) {
        return Jsoup.clean(content, "", WHITE_LIST, OUTPUT_SETTINGS);
    }

}
