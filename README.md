# db-doc(数据库转化为文档)
## 如何使用

1. 如果是MySQL,修改getMySQLDatabaseMetaData()中的url,user,password
2. 如果是Oracle,修改getOracleDatabaseMetaData()中的url,user,password
3. 修改main方法下DatabaseMetaData databaseMetaData对应的获取
4. 修改schemaPattern,如果是Oracle需指定对应的user,注意要大写
5. 修改tableNamePattern,要生成的表,可以使用%做通配,例如(sys_%)
6. 执行main方法
    