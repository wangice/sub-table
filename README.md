# sub-library-com.ice.sub.library.table.table

### sharding-proxy
使用中间层方式来替代程序中进行分库分表的操作。
sharding-proxy是中间层代理，可以将其他当做成mysql，通过网络工具直接连接，
程序也是操作mysql操作一样，具体操作可参考官网进行配置。该程序的分库分表的规则如下：

分库分表策略
中间变量 = 关键字%（数据库数量*单库数据表数量）
库 = 取整（中间变量/单库数据表数量）
表 = （中间变量%单库数据表数量）
#### sharding-proxy不推荐的场景
1.非分片字段查询(会扫描所有表)
2.分页排序(会扫描所有表)
3.任意表join(扫描所有关联表)
4.分布式事务

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




### 分库分表之后产生的问题
sharding-proxy支持的事务：
1.完全支持非跨库事务，例如：仅分表，或分库但是路由的结果在单库中。
2.完全支持因逻辑异常导致的跨库事务。例如：同一事务中，跨两个库更新。更新完毕后，抛出空指针，则两个库的内容都能回滚。
3.不支持因网络、硬件异常导致的跨库事务。例如：同一事务中，跨两个库更新，更新完毕后、未提交之前，第一个库宕机，则只有第二个库数据提交。

1. 事务  
由原来的数据库支持的ACID(原子性，一致性，隔离性，持久性)事务，CAP(一致性,可用性，分区分区容忍性)，
使用Base理论(基本可用，柔性状态，最终一致性),即保证最终一致性。  
sharding-proxy使用柔性事务，即保证最终一致性。这里能够保证一张表的数据一致性，但是不同表更新数据时
保证数据最终一致性。有几种方法：  
1.最大能力保证数据一致性，即前一条语句插入成功，后一条插入失败后进行重试，最终人为事务补偿方式(最后一道屏障)--正向补偿
对前一条语句进行回滚，即反向补偿。  
2.MQ事务，即执行完一个成功后，通知到下一条数据，使用消息中间件来保证消息的可用性。  
3.流水处理，

2. join问题  
数据库垂直拆分之后,需要进行关联查询，而分库分表查询时是不推荐join查询，因为查询时，根据分表字段进行查询。
如果不是，则查询所有分表的信息，如果在非常大的数据的情况，这会非常影响性能。
3. 分页问题

解决数据库关联和分页查询，则可以使用搜索引擎的方式






