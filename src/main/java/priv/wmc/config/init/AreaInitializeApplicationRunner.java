package priv.wmc.config.init;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import priv.wmc.common.util.FastJsonUtils;
import priv.wmc.config.AppConfig;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * 项目初始化
 *
 * @author 王敏聪
 * @date 2019/12/5 9:19
 */
@Slf4j
@Configuration
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AreaInitializeApplicationRunner implements ApplicationRunner {

    private final String PROVINCE_TEMPLATE = "INSERT INTO `province` (`id`, `name`) VALUES (%d, '%s');";
    private final String CITY_TEMPLATE = "INSERT INTO `city` (`id`, `name`, `province_id`) VALUES (%d, '%s', %d);";
    private final String COUNTY_TEMPLATE = "INSERT INTO `county` (`id`, `name`, `city_id`) VALUES (%d, '%s', %d);";

    @Value("classpath:sql/area.json")
    private Resource resource;

    private final AppConfig appConfig;

    /**
     * 项目的初始化方法
     *
     * @param args args
     */
    @Override
    public void run(ApplicationArguments args) {
        // 根据地区json生成sql（生成在“target/sql中”）
        try {
            log.info("start to generate sql file...");
            if (appConfig.getGenerate()) {
                initAreaSql(resource, new File(new ApplicationHome(getClass()).getSource().getParentFile().toString(), "/sql"));
            }
            log.info("generate sql file success");
        } catch (Exception e) {
            log.error("generate sql file fail");
            e.printStackTrace();
        }

    }

    /**
     * 解析指定的区域json，按照省生成插入sql到不同目录下
     *
     * @param jsonResource 区域json资源
     * @param sqlDir       sql生成到哪
     */
    public void initAreaSql(Resource jsonResource, File sqlDir) throws Exception {
        if (!sqlDir.exists() && !sqlDir.mkdirs()) {
            log.error("创建存储省市区sql的目录失败");
            return;
        }

        int provinceId = 0;
        int cityId = 0;
        int countyId = 0;

        JSONObject provinceJson = FastJsonUtils.readJsonFromClassPath(jsonResource);
        Set<Map.Entry<String, Object>> rawProvinceList = provinceJson.entrySet();
        if (!appConfig.singleFile) {
            for (Map.Entry<String, Object> rawProvince : rawProvinceList) {
                String provinceName = rawProvince.getKey();
                BufferedWriter writer = new BufferedWriter(new FileWriter(new File(sqlDir, provinceName + ".sql")));
                write(writer, provinceId, cityId, countyId, rawProvince);
                writer.close();
            }
        } else {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(sqlDir, "area.sql")));
            for (Map.Entry<String, Object> rawProvince : rawProvinceList) {
                write(writer, provinceId, cityId, countyId, rawProvince);
            }
            writer.close();
        }
    }

    private void write(BufferedWriter writer, int provinceId, int cityId, int countyId, Map.Entry<String, Object> rawProvince) throws IOException {
        // 省名
        String provinceName = rawProvince.getKey();

        // 生成province sql
        writer.write(String.format(PROVINCE_TEMPLATE, ++provinceId, provinceName));
        writer.newLine();

        JSONObject cityJson = (JSONObject) rawProvince.getValue();
        Set<Map.Entry<String, Object>> rawCityList = cityJson.entrySet();
        for (Map.Entry<String, Object> rawCity : rawCityList) {
            // 市名
            String cityName = rawCity.getKey();
            // 生成city sql
            writer.write(String.format(CITY_TEMPLATE, ++cityId, cityName, provinceId));
            writer.newLine();

            JSONArray countyNameJsonArray = (JSONArray) rawCity.getValue();
            // 区名
            String[] countyNameList = countyNameJsonArray.toArray(new String[0]);
            for (String countyName : countyNameList) {
                // 生成county sql
                writer.write(String.format(COUNTY_TEMPLATE, ++countyId, countyName, cityId));
                writer.newLine();
            }
        }
    }

}
