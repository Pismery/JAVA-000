- 学号：G20200579010556
- 姓名：刘流流
- 班级：4 班

## 习题

1. 使用 GCLogAnalysis.java 自己演练一遍串行 / 并行 /CMS/G1 的案例。

```shell
# 并行 GC
java -Xmx256m -Xms256m -XX:+PrintGCDetails -Xloggc:./log/ParallelGC-256-OOM-%t.log GCLogAnalysis
java -Xmx512m -Xms512m -XX:+PrintGCDetails -Xloggc:./log/ParallelGC-512-%t.log GCLogAnalysis
java -Xmx2g -Xms2g -XX:+PrintGCDetails -Xloggc:./log/ParallelGC-2G-%t.log GCLogAnalysis
```

![](https://gitee.com/pismery/imageshack/raw/master/img/20201026203224.png)

```shell
# 串行 GC
java -Xmx256m -Xms256m -XX:+UseSerialGC -XX:+PrintGCDetails -Xloggc:./log/SerialGC-256-OOM-%t.log GCLogAnalysis
java -Xmx512m -Xms512m -XX:+UseSerialGC -XX:+PrintGCDetails -Xloggc:./log/SerialGC-512-%t.log GCLogAnalysis
java -Xmx2g -Xms2g -XX:+UseSerialGC -XX:+PrintGCDetails -Xloggc:./log/SerialGC-2G-%t.log GCLogAnalysis
```

![](https://gitee.com/pismery/imageshack/raw/master/img/20201026203812.png)

```shell
# CMS GC
java -Xmx256m -Xms256m -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -Xloggc:./log/UseConcMarkSweepGC-256-OOM-%t.log GCLogAnalysis
java -Xmx512m -Xms512m -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -Xloggc:./log/UseConcMarkSweepGC-512-%t.log GCLogAnalysis
java -Xmx2g -Xms2g -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -Xloggc:./log/UseConcMarkSweepGC-2G-%t.log GCLogAnalysis
```

![](https://gitee.com/pismery/imageshack/raw/master/img/20201026205633.png)

```shell
# G1 GC
java -Xmx256m -Xms256m -XX:+UseG1GC -XX:+PrintGC -Xloggc:./log/UseG1GC-256-OOM-%t.log GCLogAnalysis
java -Xmx512m -Xms512m -XX:+UseG1GC -XX:+PrintGC -Xloggc:./log/UseG1GC-512-%t.log GCLogAnalysis
java -Xmx2g -Xms2g -XX:+UseG1GC -XX:+PrintGC -Xloggc:./log/UseG1GC-2G-%t.log GCLogAnalysis
java -Xmx4g -Xms4g -XX:+UseG1GC -XX:MaxGCPauseMillis=20 -XX:+PrintGC -Xloggc:./log/UseG1GC-4G-20m-%t.log GCLogAnalysis
```

![](https://gitee.com/pismery/imageshack/raw/master/img/20201026211709.png)



2. 使用压测工具（wrk 或 sb），演练 gateway-server-0.0.1-SNAPSHOT.jar 示例。

   ```shell
   java -XX:+UseParallelGC -jar gateway-server-0.0.1-SNAPSHOT.jar
   java -XX:+UseSerialGC -jar gateway-server-0.0.1-SNAPSHOT.jar
   java -XX:+UseConcMarkSweepGC -jar gateway-server-0.0.1-SNAPSHOT.jar
   java -XX:+UseG1GC -jar gateway-server-0.0.1-SNAPSHOT.jar
   java -XX:+UseG1GC -Xmx4g -Xms4g -XX:MaxGCPauseMillis=20 -jar gateway-server-0.0.1-SNAPSHOT.jar
   
   sb -u http://localhost:8088/api/hello -c 20 -N 30
   ```

   

   ![](https://gitee.com/pismery/imageshack/raw/master/img/20201026214933.png)

   

   ![](https://gitee.com/pismery/imageshack/raw/master/img/20201026214933.png)

   

3. **（必做）** 根据上述自己对于 1 和 2 的演示，写一段对于不同 GC 的总结，提交到 Github。

串行 GC：GC 的延迟很高，服务器多核的情况下不建议使用；

并行 GC：当内存足够时，其吞吐量表现最好，但 GC 延迟相对于 CMS 较高；

并发 GC(CMS)：延迟相对于并行 GC 更低，但是吞吐量有所下降；由于老年代使用的标记清除算法，容易导致内存碎片

G1 GC：吞吐量与 CMS GC 差不多，但可通过参数 -XX:MaxGCPauseMillis，设置 GC 暂停时间，GC 过程中会尽量按设置标准完成，从而使得 GC 延迟波动更可控；内存越大，其吞吐量相对于 CMS 表现更为优异，延迟更可控；

4. **（选做）** 如果自己本地有可以运行的项目，可以按照 2 的方式进行演练。



5. **（选做）**运行课上的例子，以及 Netty 的例子，分析相关现象。



6. **（必做）**写一段代码，使用 HttpClient 或 OkHttp 访问 [http://localhost:8808 ](http://localhost:880/)，代码提交到

```java
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class PisHttpClient {

    public static void main(String[] args) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();

        HttpRequest build = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:8808/test"))
                .build();
        HttpResponse<String> response = client.send(build, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.statusCode());
        System.out.println(response.body());
    }
}
```



## JVM 日志分析

```shell
#打印 GC 日志相关参数；
java -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:./log/gc-%p-%t.log GCLogAnalysis
# 查看使用垃圾收集器
jps -lmv
jmap -heap <pid>

```



​	