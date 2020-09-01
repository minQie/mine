package priv.wmc.generator.custom;

import java.io.IOException;

/**
 * @author Wang Mincong
 * @date 2020-08-20 17:31:34
 */
public class App {

    public static void main(String[] args) throws IOException {
        // 生成 target/area.sql 或者 target/sql/xxx.sql
        new AreaSqlGenerator().generate(false);
    }

}
