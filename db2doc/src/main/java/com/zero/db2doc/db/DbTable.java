package com.zero.db2doc.db;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DbTable {

    private String title;// 表名称

    private String tableName;// 数据库表名

    private int fieldCount = DbUtil.fieldCount;

    private String[] fieldValues = null;// 表字段值

    private List<String[]> fieldList = new ArrayList<>();

    DbTable(String title, String tableName) {
        this.title = title;
        this.tableName = tableName;
    }

    // 添加单个字段内容
    private int cursor = 0;

    public void addSingleField(String fieldValue) {
        if (cursor == fieldCount) {
            addRowField(fieldValues);
            fieldValues = new String[fieldCount];
            cursor = 0;
        }
        if (fieldValues == null || fieldValues.length <= 0) {
            fieldValues = new String[fieldCount];
            fieldValues[cursor] = fieldValue;
            cursor += 1;
        } else {
            fieldValues[cursor] = fieldValue;
            cursor += 1;
        }
    }

    // 添加一行字段的内容
    private void addRowField(String[] rowField) {
        fieldList.add(rowField);
    }
}
