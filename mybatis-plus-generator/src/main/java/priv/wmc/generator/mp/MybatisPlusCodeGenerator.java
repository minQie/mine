package priv.wmc.generator.mp;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import java.util.Arrays;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;
import priv.wmc.base.entity.BaseEntity;

/**
 * Mybatis Plus code generator apply
 *
 * @author Wang Mincong
 * @date 2020-08-17 17:21:41
 */
@Slf4j
public class MybatisPlusCodeGenerator {

    public static final String PROJECT_PATH = System.getProperty("user.dir");

    /**
     * <p>
     * 读取控制台内容 → 去除空格 → 以英文逗号为分隔符切分返回字符串数组
     * </p>
     */
    public static String[] scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        log.info("请输入" + tip + "：");
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (ipt != null && ipt.length() != 0) {
                return ipt.trim().split(",");
            }
        }
        throw new MybatisPlusException("请输入正确的数据表名！");
    }

    private static GlobalConfig globalConfig() {
        return new GlobalConfig()
            .setOutputDir(PROJECT_PATH + "/mybatis-plus-generator/src/main/java")
            .setAuthor("Wang Mincong")
            .setOpen(false)
            .setFileOverride(false)
            .setServiceName("%sService")
            .setDateType(DateType.TIME_PACK)
            .setSwagger2(true);
    }

    private static DataSourceConfig dataSourceConfig() {
        return new DataSourceConfig()
            .setUrl("jdbc:mysql://localhost:8090/mybatis_plus?useUnicode=true&useSSL=false&characterEncoding=utf8")
            .setDriverName("com.mysql.cj.jdbc.Driver")
            .setUsername("root")
            .setPassword("123")
            .setDbType(DbType.MYSQL);
    }

    private static PackageConfig packageConfig() {
        return new PackageConfig()
            // 下面两个配置结合，实际生成的父报名就是 priv.wmc.generate
            .setParent("priv.wmc")
            .setModuleName("generate")
            // controller、service、entity、mapper的报名都是可以指定的
            .setEntity("pojo.entity");
    }

    private static StrategyConfig strategyConfig() {
        // 自动填充策略
        TableFill gmtCreate = new TableFill("gmt_create", FieldFill.INSERT);
        TableFill gmtModified = new TableFill("gmt_modified", FieldFill.UPDATE);

        String[] tableNames = scanner("请输入数据库的表名：（多个英文逗号分割）");

        return new StrategyConfig()
            .setNaming(NamingStrategy.underline_to_camel)
            .setColumnNaming(NamingStrategy.underline_to_camel)
            .setEntityBooleanColumnRemoveIsPrefix(true)

            .setEntityLombokModel(true)

            .setRestControllerStyle(true)

            .setSuperEntityClass(BaseEntity.class)
            .setSuperEntityColumns("id", "version", "gmt_create", "gmt_modified", "deleted")
            .setVersionFieldName("version")
            .setLogicDeleteFieldName("deleted")

            // 代码生成的依据：数据库表名
            .setInclude(tableNames)
            .setTableFillList(Arrays.asList(gmtCreate, gmtModified));
    }

    public static void main(String[] args) {
        new AutoGenerator()
            /* 全局配置 */
            .setGlobalConfig(globalConfig())
            /* 数据源配置 */
            .setDataSource(dataSourceConfig())
            /* 包配置 */
            .setPackageInfo(packageConfig())
            /* 策略配置 */
            .setStrategy(strategyConfig())
            /* 模板引擎 */
            .setTemplateEngine(new FreemarkerTemplateEngine())
            .execute();
    }

}
