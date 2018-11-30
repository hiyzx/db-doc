package org.zero.db2doc.util;

import lombok.extern.slf4j.Slf4j;
import org.zero.db2doc.db.DbConfig;
import org.zero.db2doc.db.DbTable;
import org.zero.db2doc.db.DbUtil;
import org.zero.db2doc.enums.DbTypeEnum;
import org.zero.db2doc.word.WordUtil;

import javax.sql.DataSource;
import java.sql.DatabaseMetaData;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author yezhaoxing
 * @since 2018/10/25
 */
@Slf4j
public class Db2DocUtil {

    /**
     *
     * @param dataSource
     *            数据库连接dataSource
     * @param schemaPattern
     *            oracle->用户名 mysql->数据库名
     * @param tableNamePattern
     *            要生成的表(支持通配符,比如征信表 ZX_%)
     */
    public static void db2Doc(DataSource dataSource, String schemaPattern, String tableNamePattern) throws Exception {
        DatabaseMetaData databaseMetaData = dataSource.getConnection().getMetaData();
        db2Doc(databaseMetaData, schemaPattern, tableNamePattern);
    }

    public static void db2Doc(String dbUrl, DbTypeEnum typeEnum, String dbUser, String dbPassword, String schemaPattern,
            String tableNamePattern) throws Exception {
        DatabaseMetaData databaseMetaData = DbConfig.geDatabaseMetaData(dbUrl, typeEnum, dbUser, dbPassword);
        db2Doc(databaseMetaData, schemaPattern, tableNamePattern);
    }

    public static void db2Doc(DatabaseMetaData databaseMetaData, String schemaPattern, String tableNamePattern)
            throws Exception {
        String path = "C:\\Users\\Administrator\\Desktop\\%s-数据库文档-%s.doc";

        long startTime = System.currentTimeMillis();
        log.info("starting to export mysql table info....");
        List<DbTable> tableList = DbUtil.initTableInfos(databaseMetaData, schemaPattern, tableNamePattern);
        WordUtil wordKit = new WordUtil();
        wordKit.writeTableToWord(tableList,
                String.format(path, schemaPattern, new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())));
        log.info("本次导出总耗时：{} s", (System.currentTimeMillis() - startTime) / 1000);
    }
}
