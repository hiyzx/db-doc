# db-doc(数据库转化为文档)
## 如何使用

1. 如果是MySQL,修改getMySQLDatabaseMetaData()中的url,user,password
2. 如果是MySQL,修改getOracleDatabaseMetaData()中的url,user,password
3. 修改main方法下DatabaseMetaData dbmd对应的获取
4. 修改schemaPattern,如果是Oracle需指定对应的user,注意大写
5. 修改tableNamePattern,可以使用%做通配
6. 执行main方法
    