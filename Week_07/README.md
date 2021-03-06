- 学号：G20200579010556
- 姓名：刘流流
- 班级：4 班


Week07 作业题目（周四）：

1.（选做）用今天课上学习的知识，分析自己系统的 SQL 和表结构

2.（必做）按自己设计的表结构，插入 100 万订单模拟数据，测试不同方式的插入效率

```Java
public int execute(Connection conn, String sql, Object... args) throws SQLException {
    try (PreparedStatement statement = conn.prepareStatement(sql)) {
        for (int i = 1; i <= args.length; i++) {
            statement.setObject(i, args[i - 1]);
        }

        return statement.executeUpdate();
    }
}

public int[] executeBatch(Connection conn, String sql, List<List<Object>> argsList) throws SQLException {
    try (PreparedStatement statement = conn.prepareStatement(sql)) {
        for (List<Object> objects : argsList) {
            for (int i = 0; i < objects.size(); i++) {
                statement.setObject(i + 1, objects.get(i));
            }
            statement.addBatch();
        }

        return statement.executeBatch();
    }
}
```

3.（选做）按自己设计的表结构，插入 1000 万订单模拟数据，测试不同方式的插入效

4.（选做）使用不同的索引或组合，测试不同方式查询效率

5.（选做）调整测试数据，使得数据尽量均匀，模拟 1 年时间内的交易，计算一年的销售报表：销售总额，订单数，客单价，每月销售量，前十的商品等等（可以自己设计更多指标）

6.（选做）尝试自己做一个 ID 生成器（可以模拟 Seq 或 Snowflake）

7.（选做）尝试实现或改造一个非精确分页的程序


Week07 作业题目（周六）：

1.（选做）配置一遍异步复制，半同步复制、组复制
2.（必做）读写分离 - 动态切换数据源版本 1.0

请参看 [database-homework](https://github.com/Pismery/JAVA-000/tree/main/homework/database-homework) 
类 DynamicDataSourceConfigure

3.（必做）读写分离 - 数据库框架版本 2.0

请参看 [database-homework](https://github.com/Pismery/JAVA-000/tree/main/homework/database-homework) 
类 ShardingSphereDataSourceConfigure

4.（选做）读写分离 - 数据库中间件版本 3.0

5.（选做）配置 MHA，模拟 master 宕机

6.（选做）配置 MGR，模拟 master 宕机

7.（选做）配置 Orchestrator，模拟 master 宕机，演练 UI 调整拓扑结构