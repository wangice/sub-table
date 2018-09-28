# sub-library-table

### sharding-proxy
使用中间层方式来替代程序中进行分库分表的操作。
sharding-proxy是中间层代理，可以将其他当做成mysql，通过网络工具直接连接，
程序也是操作mysql操作一样，具体操作可参考官网进行配置。该程序的分库分表的规则如下：

分库分表策略
中间变量 = 关键字%（数据库数量*单库数据表数量）
库 = 取整（中间变量/单库数据表数量）
表 = （中间变量%单库数据表数量）

### 分库分表的实际操作
线上程序，进行分库分表的时候，又两种操作方式：  
1. 晚上停机维护，在一晚上的时间将数据搬移到新的数据库和表中，这样做的好处是并不需要
特别多的操作，只需要写好程序，将现有数据库搬移。缺点就是需要程序员熬夜进行数据的搬
移，但如果一晚上还没有解决完成，第二天一早就必须要回滚数据，第二天进行进行该操作。
2. 在程序中加入双写，现记录现在数据库中的数据的最后一条，即之前的数据都有程序搬到其
他数据库和表中，线上程序除了将数据写入到旧数据库中，还会写入到新的数据库中，并且还记录
更新和删除操作，写入到消息中间件中，当程序将所有数据库搬移到新数据库和表中，那么接下
启动消费消息的程序，将对数据库的修改和删除操作对现有数据进行操作一遍。最后在启动验证
数据完整性程序。

### bin-log日志读取
需要线上的程序开启binlog，并设置SET binlog_format=STATEMENT,本程序使用STATEMENT,直接获取sql语句
并分析之后，将程序


### mongodb发送update insert更新语句



