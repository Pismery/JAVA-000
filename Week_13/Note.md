### MQ

- Queue:
- Topic:


#### Rabbit MQ

Publisher ==> Exchange ==> Queue ==> Channel ==> Consumer

最大的不同：路由策略在 MQ 端；其他大多数在消费端

#### Rocket MQ

与 Kafka 没有本质区别；

1. 纯 Java 开发；
2. 支持延迟投递，消息追溯；
3. 多队列使用同一个日志文件，不存在 Kafka 过多 topic 性能下降的问题；
4. 单机效率低于 Kafaka, 因为单个日志文件，处理策略比 Kafka 复杂；

