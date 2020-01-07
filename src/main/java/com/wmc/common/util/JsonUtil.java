package com.wmc.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.PascalNameFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * fastjson工具类
 *
 * @author 王敏聪
 * @date 2019年12月17日11:11:59
 */
@Slf4j
public class JsonUtil {

    /**
     * 读取json文件转成指定的类型
     *
     * @param resource 资源
     * @return JSONObject
     * @throws IOException 指定路径下文件不存在
     */
    public static JSONObject readJsonFromClassPath(Resource resource) throws IOException {
        if (resource.exists()) {
            String string = IOUtils.toString(resource.getInputStream(), StandardCharsets.UTF_8);
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
            throw new IOException();
        }
    }

}
