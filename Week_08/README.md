DBA 不建议分表建议分库

1. 单纯分表不能分担容量和 IO 的问题
2. 分表可以通过分库解决





作业

Week08 作业题目（周四）：

1.（选做）分析前面作业设计的表，是否可以做垂直拆分。

2.（必做）设计对前面的订单表数据进行水平分库分表，拆分 2 个库，每个库 16 张表。并在新结构在演示常见的增删改查操作。代码、sql 和配置文件，上传到 Github。

执行步骤：

- 初始化 DB
- 启动 SS Proxy
- 启动测试作业

```
mysql -uroot --default-character-set=utf8mb4
source {path}\JAVA-000\Week_08\initSSDemo.sql
```

ss proxy 配置

注意事项，Proxy 中的配置字段均要采用小写或者下划线分割，不能使用驼峰

```yaml
schemaName: sharding_db

dataSourceCommon:
  username: root
  password:
  connectionTimeoutMilliseconds: 30000
  idleTimeoutMilliseconds: 60000
  maxLifetimeMilliseconds: 1800000
  maxPoolSize: 50
  minPoolSize: 1
  maintenanceIntervalMilliseconds: 30000

dataSources:
  ds_0:
    url: jdbc:mysql://127.0.0.1:3306/demo_ds_0?serverTimezone=UTC&useSSL=false&useUnicode=true&characterEncoding=UTF-8
  ds_1:
    url: jdbc:mysql://127.0.0.1:3306/demo_ds_1?serverTimezone=UTC&useSSL=false&useUnicode=true&characterEncoding=UTF-8

rules:
  - !SHARDING
    tables:
      t_order:
        actualDataNodes: ds_${0..1}.t_order_${0..1}
        tableStrategy:
          standard:
            shardingColumn: id
            shardingAlgorithmName: t_order_inline
        keyGenerateStrategy:
          column: id
          keyGeneratorName: snowflake
    bindingTables:
      - t_order
    defaultDatabaseStrategy:
      standard:
        shardingColumn: userid
        shardingAlgorithmName: database_inline
    defaultTableStrategy:
      none:

    shardingAlgorithms:
      database_inline:
        type: INLINE
        props:
          algorithm-expression: ds_${userid % 2}
      t_order_inline:
        type: INLINE
        props:
          algorithm-expression: t_order_${id % 2}

    keyGenerators:
      snowflake:
        type: SNOWFLAKE
        props:
          worker-id: 123

```







3.（选做）模拟 1000 万的订单单表数据，迁移到上面作业 2 的分库分表中。

4.（选做）重新搭建一套 4 个库各 64 个表的分库分表，将作业 2 中的数据迁移到新分库。

Week08 作业题目（周六）：

1.（选做）列举常见的分布式事务，简单分析其使用场景和优缺点。

2.（必做）基于 hmily TCC 或 ShardingSphere 的 Atomikos XA 实现一个简单的分布式事务应用 demo（二选一），提交到 Github。

3.（选做）基于 ShardingSphere narayana XA 实现一个简单的分布式事务 demo。

4.（选做）基于 seata 框架实现 TCC 或 AT 模式的分布式事务 demo。

5.（选做☆）设计实现一个简单的 XA 分布式事务框架 demo，只需要能管理和调用 2 个 MySQL 的本地事务即可，不需要考虑全局事务的持久化和恢复、高可用等。

6.（选做☆）设计实现一个 TCC 分布式事务框架的简单 Demo，需要实现事务管理器，不需要实现全局事务的持久化和恢复、高可用等。

7.（选做☆）设计实现一个 AT 分布式事务框架的简单 Demo，仅需要支持根据主键 id 进行的单个删改操作的 SQL 或插入操作的事务。



