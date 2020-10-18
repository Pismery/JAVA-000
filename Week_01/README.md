# JVM 核心技术

\#学号:G20200579010556
\#姓名:刘流流
\#班级:4班



1、自己写一个简单的Hello.java,里面需要涉及基本类型,四则运行, if和for,然后
自己分析一下对应的字节码,有问题群里讨论。
2、自定义一个Classloader,加载一个Hello.xlass文件,执行hello方法,此文件内
容是一个Hello.class文件所有字节(x=255-x)处理后的文件。文件群里提供。
3、画一张图,展示Xmx,Xms, Xmn,Meta, DirectMemory, Xss这些内存参数的
关系。
4、检查一下自己维护的业务系统的JVM参数配置,用jstat和jstack,jmap查看一下
详情,并且自己独立分析一下大概情况,思考有没有不合理的地方,如何改进。
注意:如果没有线上系统,可以自己run一个web/java项目。

## JDK 内置命令 

![](https://gitee.com/pismery/imageshack/raw/master/img/20201017191308.png)

1. jps -lmv 查看 java 进程
2. jstat -gc <pid> 1000 1000 : 查看 JVM 内存和 GC 情况
3. jstat -gcutil <pid> 1000 1000 : 查看 JVM 内存和 GC 内存使用率
4. jmap -histo <pid>
5. jmap -heap
6. jcmd <pid> help

## JDK 图形化工具

- jconsole
- jvisualvm
- jmc

