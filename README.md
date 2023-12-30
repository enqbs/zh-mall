# 项目简介

zh-mall 是本人曾经的毕业设计加以优化、改进而来的电商类项目，包含了商城、搜索、后台管理系统。现如今作为本人的开源项目长期维护、升级。经过不断打磨，项目核心功能完善。

doc/Postman 文件夹中的 json 文件导入 Postman 能得到项目的所有接口。前端写的不好暂不开源。

项目技术包括
- MySQL 主从同步
- 数据源读写分离
- MySQL Redis Elasticsearch 数据同步
- RabbitMQ 消息持久化
- RabbitMQ 消息可靠性投递
- RabbitMQ 消息幂等性
- RabbitMQ 延迟插件交换机
- RBAC 权限模型
- 单点登录、无感知刷新 Token
- 接口幂等性、高并发数据原子性
- Elasticsearch 全文检索
- 分布式日志收集

项目业务包括
- 用户管理
- 商品管理
- 优惠券管理
- 订单管理
- 支付信息管理
- 系统用户管理
- 系统角色管理
- 系统权限管理
- 商品列表、新商品推荐、搜索、展示、评论
- 用户第三方登录、优惠券、地址、购物车
- 订单下单流程、超时自动取消
- 支付发起、关闭

项目达到上线标准。解决 MySQL 事务原子性、线程安全、缓存同步等问题。后续会新增项目微服务版本及其他功能。主（main）分支现以 JDK 21、Spring Boot 3进行维护升级。低版本[点击此处](https://github.com/enqbs/zh-mall/tree/2.x)选择2.x 分支。

# JDK 选择

| 项目分支                                             | JDK 版本  | Spring Boot 版本  |
|--------------------------------------------------|---------|-----------------|
| main                                             | JDK 21  | 3.2.1           |
| [2.x](https://github.com/enqbs/zh-mall/tree/2.x) | JDK 1.8 | 2.7.18          |

# 技术选择

| 技术                      | 说明             | 版本                |
|-------------------------|----------------|-------------------|
| Spring Boot             | 容器管理、MVC       | 3.2.1             |
| Spring Security         | 认证、授权          | 6.2.1             |
| MyBatis                 | ORM            | 3.5.14            |
| MyBatis-Generator       | 代码生成插件         | 1.3.7             |
| MySQL                   | 数据库            | 8.0.31            |
| Redis                   | 缓存             | 6.2               |
| RabbitMQ                | 消息队列           | 3.12.7-management |
| Canal                   | 数据库增量日志解析工具    | deployer-1.1.7    |
| ~~ShardingSphere-JDBC~~ | ~~读写分离、数据分片~~  | ~~5.1.2~~         |
| AliPay-SDK-V2           | 支付宝开源开发工具      | 4.38.170.ALL      |
| AliPay-SDK-V3           | 支付宝开源开发工具      | 2.8.0.ALL         |
| AliYun-OSS              | 阿里云对象存储        | 3.17.4            |
| JWT                     | Json Web Token | -                 |
| Elasticsearch           | 全文检索           | 8.10.4            |
| Logstash                | 日志收集工具         | 8.10.4            |
| Kibana                  | ES 可视化工具       | 8.10.4            |

PS：ShardingSphere-JDBC 5.4.1（当前最新版）与 JDK 21部分包名不兼容，项目暂时舍弃该功能。

# 运行时持久层、中间件选择

| 技术            | 说明                             | 是否必选*  |
|---------------|--------------------------------|--------|
| MySQL         | 数据库                            | *      |
| Redis         | 缓存                             | *      |
| RabbitMQ      | 消息队列                           | *      |
| Canal         | MySQL、Redis、Elasticsearch 数据同步 | -      |
| Elasticsearch | 全文检索、日志收集                      | -      |
| Logstash      | 日志收集插件                         | -      |
| Kibana        | ES 可视化工具                       | -      |

PS：如无需读写分离，注释 ShardingSphere-JDBC 依赖，修改配置文件为单数据源。

# 目录结构

```text
zh-mall
├─admin         // 后台管理系统
├─app           // 商城系统
├─common        // 公共模块
├─doc
│  ├─Canal      // Canal 配置文件
│  ├─Logstash   // Logstash 配置文件
│  ├─MySQL      // SQL 文件
│  └─Postman    // 测试接口
├─file          // 文件上传模块、阿里云 OSS 配置
├─generator     // MyBatis-Generator 插件生成的代码
├─pay           // 支付模块
├─search        // 全文检索系统
└─security      // Spring Security 配置、处理
```
