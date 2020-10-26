# JVM 核心技术

- 学号：G20200579010556
- 姓名：刘流流
- 班级：4 班

## 习题

1. 自己写一个简单的 Hello.java，里面需要涉及基本类型,四则运行，if 和for，然后自己分析一下对应的字节码，有问题群里讨论。



2. 自定义一个Classloader,加载一个Hello.xlass文件,执行hello方法,此文件内容是一个Hello.class文件所有字节(x=255-x)处理后的文件。文件群里提供。

   实现代码请参考 HelloClassLoader.java 文件；

   下图为演示结果

![](https://gitee.com/pismery/imageshack/raw/master/img/20201018211139.png)



3. 画一张图,展示Xmx,Xms, Xmn,Meta, DirectMemory, Xss这些内存参数的
   关系。

![](https://gitee.com/pismery/imageshack/raw/master/img/20201020212239.png)

参考链接：

- [JVM调优总结 -Xms -Xmx -Xmn -Xss](https://www.cnblogs.com/likehua/p/3369823.html)

- [Java8内存模型—永久代(PermGen)和元空间(Metaspace)](https://www.cnblogs.com/paddix/p/5309550.html)
- [JVM系列三:JVM参数设置、分析](https://www.cnblogs.com/redcreen/archive/2011/05/04/2037057.html)



4. 检查一下自己维护的业务系统的JVM参数配置,用jstat和jstack,jmap查看一下
   详情,并且自己独立分析一下大概情况,思考有没有不合理的地方,如何改进。
   注意:如果没有线上系统,可以自己run一个web/java项目。

## JDK 内置命令 

![](https://gitee.com/pismery/imageshack/raw/master/img/20201017191308.png)

1. jps -lmv 查看 java 进程
2. jstat -gc <pid> 1000 1000 : 查看 JVM 内存和 GC 情况
3. jstat -gcutil <pid> 1000 1000 : 查看 JVM 内存和 GC 内存使用率
4. jmap -histo <pid>
5. jmap -heap <pid>
6. jcmd <pid> help

## JDK 图形化工具

- jconsole
- jvisualvm
- jmc

