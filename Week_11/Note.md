# 缓存

数据分类

- 静态数据
- 准静态数据
- 中间状态数据


热数据：使用的多；

读写比大 =》 适合缓存

延迟异步加载

- 异步：查不到直接返回空，启动异步线程负责加载数据
- 解耦：异步线程维护缓存，定期或根据条件触发更新


### Redis

- 字符串 Append 会使用更多的内存
- 整数精度：大概保证 16 位； 大于 16 位会丢失精度

```shell
# 查看 Redis 统计信息
info stat
# 查看所有 Key 
keys *
#设置从库
slaveof [host] [port]
```

- 脑裂 =》 通过设置少于 n 个从库，主库则不能写来避免；



### 参考资料

Redis 基础手册：https://www.runoob.com/redis/redis-tutorial.html
Redis 基础系列（推荐）：https://www.cnblogs.com/itzhouq/p/redis1.html
Redis常用参数：https://www.cnblogs.com/liufukui/p/10448827.html
Spring Cache整合ehcache和Redis：https://www.cnblogs.com/xiang--liu/p/9720344.html
Spring Data Redis：https://blog.csdn.net/lydms/article/details/105224210
Redis命中率：https://www.cnblogs.com/junlinqunxia/p/11244230.html
Redis知识与场景集合（推荐）：http://c.biancheng.net/view/4560.html
Redis内存优化：https://redis.io/topics/memory-optimization
Redis数据类型原理与优化（推荐）：https://www.cnblogs.com/williamjie/p/11288062.html
Redis优化的几个tips：
\- https://zhuanlan.zhihu.com/p/55068567
\- http://www.jwsblog.com/archives/50.html

Lettuce详解：
\- https://www.cnblogs.com/throwable/p/11601538.html
\- https://blog.csdn.net/moonpure/article/details/82658788

Redis设计与实现：http://redisbook.com/