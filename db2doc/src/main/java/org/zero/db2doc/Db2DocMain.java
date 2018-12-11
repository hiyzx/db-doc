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
        String schemaPattern = "";// ORCALE就写数据库的schema,MYSQL为空
        String tableNamePattern = "%";// 要生成的表的通用符,比如: sys_%
        // DatabaseMetaData databaseMetaData = getMySQLDatabaseMetaData();
        DatabaseMetaData databaseMetaData = getMySQLDatabaseMetaData();
        Db2DocUtil.db2Doc(databaseMetaData, schemaPattern, tableNamePattern);
    }

    // ORACLE修改以下配置
    private static DatabaseMetaData getOracleDatabaseMetaData() {
        String url = "jdbc:oracle:thin:@ip:port/SID";
        String driverName = "oracle.jdbc.driver.OracleDriver";
        String user = "账号";
        String password = "密码";
        return DbConfig.getDatabaseMetaData(driverName, url, user, password);
    }

    // MYSQL修改以下配置
    private static DatabaseMetaData getMySQLDatabaseMetaData() {
        String url = "jdbc:mysql://ip:port/数据库名?useUnicode=true&characterEncoding=UTF-8";
        String driverName = "com.mysql.jdbc.Driver";
        String user = "账号";
        String password = "密码";
        return DbConfig.getDatabaseMetaData(driverName, url, user, password);
    }

}
