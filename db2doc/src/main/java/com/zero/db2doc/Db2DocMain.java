package com.zero.db2doc;

import com.zero.db2doc.db.DbConfig;
import com.zero.db2doc.db.DbTable;
import com.zero.db2doc.db.DbUtil;
import com.zero.db2doc.word.WordUtil;
import lombok.extern.slf4j.Slf4j;

import java.sql.DatabaseMetaData;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author yezhaoxing
 * @since 2018/07/30
 */
@Slf4j
public class Db2DocMain {

    public static void main(String[] arg) throws Exception {
        String EXPORT_FILE_PATH = "C:\\Users\\Administrator\\Desktop\\数据库文档-%s.doc";
        String schemaPattern = "%";
        String tableNamePattern = "%";
        DatabaseMetaData databaseMetaData = getMySQLDatabaseMetaData();
        // DatabaseMetaData databaseMetaData = getOracleDatabaseMetaData();

        long startTime = System.currentTimeMillis();
        log.info("starting to export mysql table info....");
        List<DbTable> tableList = DbUtil.initTableInfos(databaseMetaData, schemaPattern, tableNamePattern);
        WordUtil wordKit = new WordUtil();
        wordKit.writeTableToWord(tableList,
                String.format(EXPORT_FILE_PATH, new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())));
        log.info("本次导出总耗时：{} s", (System.currentTimeMillis() - startTime) / 1000);
    }

    private static DatabaseMetaData getOracleDatabaseMetaData() {
        String url = "jdbc:oracle:thin:@ip:port/service name";
        String driverName = "oracle.jdbc.driver.OracleDriver";
        String user = "user";
        String password = "password";
        return DbConfig.getDatabaseMetaData(driverName, url, user, password);
    }

    private static DatabaseMetaData getMySQLDatabaseMetaData() {
        String url = "jdbc:mysql://ip:port/数据库名?useUnicode=true&characterEncoding=UTF-8";
        String driverName = "com.mysql.jdbc.Driver";
        String user = "user";
        String password = "password";
        return DbConfig.getDatabaseMetaData(driverName, url, user, password);
    }

}
