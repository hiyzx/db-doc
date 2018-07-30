package com.zero.db2doc.db;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.util.Properties;

@Slf4j
public abstract class DbConfig {

    public static DatabaseMetaData getDatabaseMetaData(String driverName, String url, String user, String password) {
        DatabaseMetaData databaseMetaData = null;
        try {
            Class.forName(driverName).newInstance();
            Properties props = new Properties();
            props.put("user", user);
            props.put("password", password);
            props.put("remarksReporting", "true");
            Connection dbConnection = DriverManager.getConnection(url, props);
            databaseMetaData = dbConnection.getMetaData();
            log.info("url is >>> {}", url);
        } catch (Exception e) {
            log.error("获取数据库连接失败>>>", e);
        }
        return databaseMetaData;
    }
}
