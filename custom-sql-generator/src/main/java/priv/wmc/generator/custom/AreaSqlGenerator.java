package priv.wmc.generator.custom;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;
import priv.wmc.common.constant.SystemConstant;
import priv.wmc.common.util.FastJsonUtils;

/**
 * @author 王敏聪
 * @date 2020-08-20 18:15:52
 */
public class AreaSqlGenerator {

    private static final String PROVINCE_TEMPLATE = "INSERT INTO `province` (`id`, `name`) VALUES (%d, '%s');";
    private static final String CITY_TEMPLATE = "INSERT INTO `city` (`id`, `name`, `province_id`) VALUES (%d, '%s', %d);";
    private static final String COUNTY_TEMPLATE = "INSERT INTO `county` (`id`, `name`, `city_id`) VALUES (%d, '%s', %d);";

    private static final String RELATIVE_PATH = File.separator +
        "custom-sql-generator" +
        File.separator +
        "target" +
        File.separator +
        "classes" +
        File.separator;

    public void generate(boolean singleFile) throws IOException {
        try (InputStream inputAreaJson = new FileInputStream(SystemConstant.CURRENT_PATH + RELATIVE_PATH + "area.json")) {
            File outputDir =
                singleFile ?
                new File(SystemConstant.CURRENT_PATH + RELATIVE_PATH) :
                new File(SystemConstant.CURRENT_PATH + RELATIVE_PATH + "sql" + File.separator);

            if (!outputDir.exists() && !outputDir.mkdirs()) {
                System.err.println("创建输出目录失败");
                return;
            }

            System.out.println("start to generate sql file...");
            initAreaSql(singleFile, inputAreaJson, outputDir);
            System.out.println("generate sql file success");
        }
    }

    /**
     * 解析指定的区域json，按照省生成插入sql到不同目录下
     *
     * @param areaJson 区域json资源
     * @param sqlDir       sql生成到哪
     */
    public void initAreaSql(boolean singleFile, InputStream areaJson, File sqlDir) throws IOException {
        int provinceId = 0;
        int cityId = 0;
        int countyId = 0;

        JSONObject provinceJson = FastJsonUtils.readJsonFromClassPath(areaJson);
        Set<Map.Entry<String, Object>> rawProvinceList = provinceJson.entrySet();
        if (!singleFile) {
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
            // 区名（下面在瞎报错，直接忽略警告的注解压制）
            @SuppressWarnings("SuspiciousToArrayCall")
            String[] countyNameList = countyNameJsonArray.toArray(new String[0]);
            for (String countyName : countyNameList) {
                // 生成county sql
                writer.write(String.format(COUNTY_TEMPLATE, ++countyId, countyName, cityId));
                writer.newLine();
            }
        }
    }

}
