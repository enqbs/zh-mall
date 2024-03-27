# 项目简介

zh-mall 是本人曾经的毕业设计加以优化、改进而来的电商项目。包含了商城、搜索、后台管理系统。现在作为个人开源项目长期维护、升级。

doc/Postman 文件夹中的 json 文件导入 Postman 能得到项目的所有接口。前端写的不好暂不开源。

项目技术包括
- MySQL 主从同步
- 数据源读写分离
- MySQL Redis Elasticsearch 数据同步
- RabbitMQ 消息持久化
- RabbitMQ 消息可靠性投递
- RabbitMQ 消息幂等性
- RabbitMQ 延迟交换机
- RBAC 权限模型
- 单点登录、无感知刷新 Token
- 接口幂等性、高并发数据原子性
- Elasticsearch 全文检索
- Elasticsearch 分布式日志收集

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

项目达到上线标准。已解决 MySQL 事务原子性、线程安全问题。生产环境（双核2GB内存VPS）写操作压测 QPS 1000 吞吐量 200+/sec。

PS：项目 Spring Boot 2.0稳定版已完成。当前分支后续可能会停止更新，直接升级 Spring Boot 3.0。极力推荐主（[main](https://github.com/enqbs/zh-mall/tree/main)）分支，第一时间更新功能。

# 技术选择

| 技术                  | 说明             | 版本                |
|---------------------|----------------|-------------------|
| Spring Boot         | 容器管理、MVC       | 2.7.18            |
| Spring Security     | 认证、授权          | 5.7.11            |
| MyBatis             | ORM            | 3.5.13            |
| MyBatis-Generator   | 代码生成插件         | 1.3.7             |
| MySQL               | 数据库            | 8.0.31            |
| Redis               | 缓存             | 6.2               |
| RabbitMQ            | 消息队列           | 3.12.7-management |
| Canal               | 数据库增量日志解析工具    | deployer-1.1.7    |
| ShardingSphere-JDBC | 读写分离、数据分片      | 5.2.0             |
| AliPay-SDK          | 支付宝开源开发工具      | 4.39.2.ALL        |
| AliYun-OSS          | 阿里云对象存储        | 3.17.2            |
| JWT                 | Json Web Token | -                 |
| Elasticsearch       | 全文检索、日志收集      | 7.17.15           |
| Logstash            | 日志收集插件         | 7.17.15           |
| Kibana              | ES 可视化工具       | 7.17.15           |

# 运行时持久层、中间件选择

| 技术            | 说明                               | 是否必选(*)  |
|---------------|----------------------------------|----------|
| MySQL         | 数据库                              | *        |
| Redis         | 缓存                               | *        |
| RabbitMQ      | 消息队列                             | *        |
| Canal         | MySQL、Redis、Elasticsearch 数据同步工具 | -        |
| Elasticsearch | 全文检索、日志收集                        | -        |
| Logstash      | 日志收集插件                           | -        |
| Kibana        | ES 可视化工具                         | -        |

PS：如无需读写分离，注释 ShardingSphere-JDBC 依赖，修改配置文件为单数据源。

# 目录结构

```text
zh-mall
├─admin         // 后台管理系统
├─app           // 前台商城系统
├─common        // 封装的常用工具类、常量、异常处理
├─doc           // SQL、Postman 等相关文件
├─file          // 文件上传、阿里云 OSS 配置
├─generator     // MyBatis-Generator 插件生成的代码
├─pay           // 支付功能实现
├─search        // 搜索功能实现
└─security      // Spring Security 相关配置、处理
```
