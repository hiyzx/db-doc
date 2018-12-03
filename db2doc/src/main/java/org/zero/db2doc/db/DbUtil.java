package org.zero.db2doc.db;

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
            this.put("IS_KEY", "是否为主键");
            this.put("TYPE_NAME", "字段类型");
            this.put("COLUMN_DEF", "默认值");
            this.put("COLUMN_SIZE", "字段长度");
            this.put("IS_NULLABLE", "能否为空");
            this.put("REMARKS", "备注信息");
        }
    };

    public static int fieldCount = tableRelation.keySet().size();// 当前字段数量

    public static List<DbTable> initTableInfos(DatabaseMetaData dbmd, String schemaPattern, String tableNamePattern) {
        log.info("开始获取表格结构");
        List<DbTable> tableList = new ArrayList<>();
        try {
            ResultSet tableResultSet = dbmd.getTables(null, schemaPattern, tableNamePattern, new String[] { "TABLE" });// 表数据集
            List<DbTable> tables = new ArrayList<>();
            while (tableResultSet.next()) {
                tables.add(new DbTable(tableResultSet.getString("REMARKS"), tableResultSet.getString("TABLE_NAME")));
            }
            // consoleInfo(tables);// 输出所有表信息
            for (DbTable dbTable : tables) {
                String tableName = dbTable.getTableName();
                log.info("开始解析表:{}", tableName);
                try {
                    if (!StringUtils.isNullOrEmpty(tableName)) {
                        String primaryKeyColumn = "";
                        ResultSet primaryKeyRs = dbmd.getPrimaryKeys(null, null, tableName);
                        while (primaryKeyRs.next()) {
                            // System.out.println("****** Comment ******");
                            // System.out.println("TABLE_CAT : " + primaryKeyRs.getObject(1));
                            // System.out.println("TABLE_SCHEM: " + primaryKeyRs.getObject(2));
                            // System.out.println("TABLE_NAME : " + primaryKeyRs.getObject(3));
                            // System.out.println("COLUMN_NAME: " + primaryKeyRs.getObject(4));
                            // System.out.println("KEY_SEQ : " + primaryKeyRs.getObject(5));
                            // System.out.println("PK_NAME : " + primaryKeyRs.getObject(6));
                            // System.out.println("****** ******* ******");
                            primaryKeyColumn = (String) primaryKeyRs.getObject(4);
                        }
                        ResultSet rs = dbmd.getColumns(null, "%", tableName, "%");
                        while (rs.next()) {
                            for (String fieldName : tableRelation.keySet()) {
                                if (!"IS_KEY".equals(fieldName)) {
                                    try {
                                        String fieldValue = rs.getString(fieldName);
                                        dbTable.addSingleField(fieldValue);
                                        if ("COLUMN_NAME".equals(fieldName)) {
                                            if (primaryKeyColumn.equals(fieldValue)) {
                                                dbTable.addSingleField("YES");
                                            } else {
                                                dbTable.addSingleField("");
                                            }
                                        }
                                    } catch (Exception e) {
                                        dbTable.addSingleField("");
                                    }
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
            log.error("获取数据库表信息失败", e);
        }
        log.info("获取表格结构成功...");
        return tableList;
    }

    private static void consoleInfo(List<String> tables) {
        for (String table : tables) {
            log.info("表名：{}", table);
        }
    }
}
