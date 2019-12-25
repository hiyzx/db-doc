# db-doc(数据库转化为文档)

## 如何使用

1. 查看类Db2DocUtil，有三个重载方法都可以调用 db2Doc（）可以调用
2. 如果是下载源码使用，可以参照Db2DocMain类修改对应的数据库配置即可
````
mysql数据库的连接池,需要添加配置:
?useInformationSchema=true
````
3. 如果是已经发布jar包，在spring工程引用，配置DataSource了，建议使用第一个方法：
```
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:spring-config.xml"})
public class DbTest {

    @Resource
    private DataSource dataSource;

    @Test
    public void db2Doc() throws SQLException {
        Db2DocUtil.db2Doc(dataSource, "数据库名", "需要生成的表(支持通配符如:sys_%)");
    }
}
```

    