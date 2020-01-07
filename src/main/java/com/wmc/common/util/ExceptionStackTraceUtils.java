package com.wmc.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 获取封装异常的相关信息
 *
 * @author 王敏聪
 * @date 2019/12/10 11:54
 */
public class ExceptionStackTraceUtils {

    /**
     * 获取异常的堆栈信息
     *
     * @param e 异常
     * @return 堆栈信息
     */
    public static String getStackTrace(Exception e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw, true));
        return sw.getBuffer().toString();
    }

    /**
     * 获取异常堆栈信息(自定义<br>换行符)
     *
     * @param e 异常
     * @return 堆栈信息
     */
    public static String getDecorateStackTrace(Exception e) {
        StringBuilder builder = new StringBuilder();
        builder.append(e.toString()).append("<br>");
        StackTraceElement[] stackTrace = e.getStackTrace();
        for (StackTraceElement s : stackTrace) {
            builder.append("at ").append(s.toString()).append("<br>");
        }
        return builder.toString();
    }

}
