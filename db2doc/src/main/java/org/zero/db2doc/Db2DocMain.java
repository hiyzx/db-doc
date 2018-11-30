package org.zero.db2doc;

import lombok.extern.slf4j.Slf4j;
import org.zero.db2doc.db.DbConfig;
import org.zero.db2doc.util.Db2DocUtil;

import java.sql.DatabaseMetaData;

/**
 * @author yezhaoxing
 * @since 2018/07/30
 */
@Slf4j
public class Db2DocMain {

    public static void main(String[] arg) throws Exception {
        String EXPORT_FILE_PATH = "C:\\Users\\Administrator\\Desktop\\%s-数据库文档-%s.doc";
        String schemaPattern = "ZX_BHNS";
        String tableNamePattern = "HSJ_%";
        // DatabaseMetaData databaseMetaData = getMySQLDatabaseMetaData();
        DatabaseMetaData databaseMetaData = getOracleDatabaseMetaData();
        Db2DocUtil.db2Doc(databaseMetaData, schemaPattern, tableNamePattern);
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
