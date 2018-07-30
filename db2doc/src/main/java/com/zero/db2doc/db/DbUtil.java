package com.zero.db2doc.db;

import com.mysql.jdbc.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Slf4j
public class DbUtil {

    public static LinkedHashMap<String, String> tableRelation = new // 表结构信息
    LinkedHashMap<String, String>() {
        {
            this.put("COLUMN_NAME", "字段名称");
            this.put("TYPE_NAME", "字段类型");
            this.put("COLUMN_DEF", "默认值");
            this.put("COLUMN_SIZE", "字段长度");
            this.put("REMARKS", "备注信息");
        }
    };

    public static int fieldCount = tableRelation.keySet().size();// 当前字段数量

    public static List<DbTable> initTableInfos(DatabaseMetaData dbmd, String schemaPattern, String tableNamePattern) {
        log.info("开始获取表格结构");
        List<DbTable> tableList = new ArrayList<>();
        try {
            ResultSet tableResultSet = dbmd.getTables(null, schemaPattern, tableNamePattern, new String[] { "TABLE" });// 表数据集
            DbTable dbTable = null;
            ArrayList<String> tables = new ArrayList<>();
            while (tableResultSet.next()) {
                tables.add(tableResultSet.getString("TABLE_NAME"));
            }
            consoleInfo(tableList);// 输出所有表信息
            for (String tableName : tables) {
                try {
                    if (!StringUtils.isNullOrEmpty(tableName)) {
                        ResultSet rs = dbmd.getColumns(null, "%", tableName, "%");
                        dbTable = new DbTable(tableName, tableName);
                        while (rs.next()) {
                            for (String fieldName : tableRelation.keySet()) {
                                try {
                                    String fieldValue = rs.getString(fieldName);
                                    dbTable.addSingleField(fieldValue);
                                } catch (Exception e) {
                                    dbTable.addSingleField("");
                                }
                            }

                        }
                        tableList.add(dbTable);
                    }
                } catch (Exception e) {
                    log.error("获取数据库表信息失败>>> + " + tableName, e);
                }
            }
        } catch (Exception e) {
            log.error("获取数据库表信息失败>>> + ", e);
        }
        log.info("获取表格结构成功...");
        return tableList;
    }

    private static void consoleInfo(List<DbTable> tableList) {
        for (DbTable table : tableList) {
            log.debug("表名：" + table.getTitle());
        }
    }
}
