- 学号：G20200579010556
- 姓名：刘流流
- 班级：4 班

# 作业

1.（必做）配置 redis 的主从复制，sentinel 高可用，Cluster 集群。

### 搭建 Redis 一主两从操作步骤：

1. 下载 Redis 压缩包解压 3 份；
2. 分别添加修改 3 个 Redis 实例的配置 redis.conf，配置如下：

```config
port 6379
```

```config
port 6380
slaveof 127.0.0.1 6379
```

```config
port 6381
slaveof 127.0.0.1 6379
```

3. 进入相应目录启动 Redis, 先启动主 Redis 再启动从 Redis

```shell
redis-server.exe redis.conf
```
4. 启动效果： 

![](https://gitee.com/pismery/imageshack/raw/master/img/20210208222720.png)

5. 验证结果：

![](https://gitee.com/pismery/imageshack/raw/master/img/20210207221652.png)

### 搭建 Redis sentinel 操作步骤：

1. 在 Redis 目录下配置 sentinel.conf 文件
```config
#当前Sentinel服务运行的端口
port 26379

#master
#Sentinel去监视一个名为mymaster的主redis实例，这个主实例的IP地址为本机地址127.0.0.1，端口号为6379，
#而将这个主实例判断为失效至少需要2个 Sentinel进程的同意，只要同意Sentinel的数量不达标，自动failover就不会执行
sentinel myid 28b6ba1b4f37dda1b87f559c5b728029d3adf8eb

#指定了Sentinel认为Redis实例已经失效所需的毫秒数。当 实例超过该时间没有返回PING，或者直接返回错误，那么Sentinel将这个实例标记为主观下线。
#只有一个 Sentinel进程将实例标记为主观下线并不一定会引起实例的自动故障迁移：只有在足够数量的Sentinel都将一个实例标记为主观下线之后，实例才会被标记为客观下线，这时自动故障迁移才会执行
sentinel monitor mymaster 127.0.0.1 6379 1

#指定了在执行故障转移时，最多可以有多少个从Redis实例在同步新的主实例，在从Redis实例较多的情况下这个数字越小，同步的时间越长，完成故障转移所需的时间就越长
sentinel down-after-milliseconds mymaster 5000

#如果在该时间（ms）内未能完成failover操作，则认为该failover失败
sentinel config-epoch mymaster 15
```

2. 启动 sentinel

```shell
redis-server.exe sentinel.conf --sentinel
```

### windows 搭建 Redis Cluster 操作步骤：

Redis 集群环境搭建主要有以下三步：

1. 下载和配置 Redis；
2. 准备 Ruby 运行环境；
3. 下载 Redis 集群脚本工具；

#### 下载和配置 Redis

- [下载](https://github.com/microsoftarchive/redis/releases/tag/win-3.2.100) redis-3.2.100 zip;
- 将 redis zip 解压 6 套实例(搭建三套一主一从的集群)；
- 分别修改 6 套 redis 的配置文件 redis.windows.conf， 修改如下配置

```config
cluster-enabled yes
cluster-config-file nodes-6379.conf
cluster-node-timeout 15000
appendonly yes
```

#### 准备 Ruby 运行环境；

由于 Redis 3.x, 4.x 版本的集群脚本是 Ruby 写的，所以我们需要安装 Ruby 环境；

- [下载](https://rubyinstaller.org/downloads/) Ruby;
- [下载](https://rubygems.org/pages/download) Redis 的 Ruby 驱动 redis-xxx.gem.zip
- 进入 ruby-gem 下载目录执行命令 ruby setup.rb
- 在 redis 安装目录执行命令 gem install redis

#### 下载 Redis 集群脚本工具；

- [下载](https://github.com/microsoftarchive/redis/releases/tag/win-3.2.100) source code;
- 从 source code 中的 src 目录拷贝脚本 redis-trib.rb 至其中一个 redis 实例中

#### 启动 redis 集群

- 启动 6 套 redis 实例
  
```shell
redis-server.exe redis.windows.conf 
```

- 创建集群

```shell
## --replicas 1 表示每一个主实例都有一个从实例
redis-trib.rb create --replicas 1 127.0.0.1:6379 127.0.0.1:6380 127.0.0.1:6381 127.0.0.1:6382 127.0.0.1:6383 127.0.0.1:6384
```

![](https://gitee.com/pismery/imageshack/raw/master/img/20210212160557.png)

- 连接集群，查看集群信息；

```shell
## -c 表示集群
redis-cli –c –h 127.0.0.1 –p 6379
## 查看集群信息
cluster info
## 创建主从关系
info replication
## 查看集群节点关系
cluster nodes
```

![](https://gitee.com/pismery/imageshack/raw/master/img/20210212160956.png)

### 参考文章

- [在windows上搭建redis集群（Redis-Cluster）](https://blog.csdn.net/weixin_41846320/article/details/83654766)

2.（选做）练习示例代码里下列类中的作业题:
08cache/redis/src/main/java/io/kimmking/cache/RedisApplication.java

3.（选做☆）练习 redission 的各种功能。

4.（选做☆☆）练习 hazelcast 的各种功能。

5.（选做☆☆☆）搭建 hazelcast 3 节点集群，写入 100 万数据到一个 map，模拟和演 示高可用。