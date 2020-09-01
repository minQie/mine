package priv.wmc.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.IOUtils;

/**
 * fastjson工具类
 *
 * @author 王敏聪
 * @date 2019年12月17日11:11:59
 */
public final class FastJsonUtils {

    private FastJsonUtils() {}

    /**
     * 读取json文件转成指定的类型
     *
     * @param resource 资源
     * @return JSONObject
     * @throws IOException 指定路径下文件不存在
     */
    public static JSONObject readJsonFromClassPath(InputStream inputStream) throws IOException {
        if (inputStream.available() > 1) {
            String string = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            return JSON.parseObject(string,
                    // 自动关闭流
                    Feature.AutoCloseSource,
                    // 允许注释
                    Feature.AllowComment,
                    // 允许单引号
                    Feature.AllowSingleQuotes,
                    // 使用 Big decimal
                    Feature.UseBigDecimal);
        } else {
            throw new IOException("文件没有可以读取的内容");
        }
    }

}
