# 项目简介

zh-mall 是本人曾经的毕业设计加以优化、改进而来的电商项目，包含了前台商城、搜索、后台管理系统，现在作为本人的开源项目长期维护、升级。

doc 文件夹中的 json 文件导入 postman 得到项目所有接口，前端写的不好暂不开源。

项目技术包括（完成度：90%）
- MySQL 主从同步、读写分离
- 分布式日志收集
- 单点登录、无感知刷新 Token
- 缓存、缓存同步
- 消息持久化、可靠性投递、延迟队列

项目业务包括（完成度：70%）
- 用户管理
- 商品管理
- 优惠券管理
- 订单管理
- 支付退款、支付信息管理
- 系统用户管理
- 角色管理
- 权限管理
- 商品分类列表、新商品推荐、商品搜索、商品展示、商品评论
- 用户第三方登录、优惠券、地址管理、购物车
- 订单下单流程、超时自动取消
- 支付、关闭支付

项目达到上线标准，压测 QPS 500以内无感知，QPS 1000以上稍显吃力，QPS 10000请求存活率80%，已解决 MySQL 事务原子性、线程安全问题。后续会新增微服务版本。

# 技术选择

| 技术       | 说明                  | 版本                            |
| ---------- | --------------------- | ---------------------------- |
| Spring Boot           | 容器管理、MVC          | 2.7.16            |
| Spring Security       | 认证、授权             | 2.7.16            |
| MyBatis               | ORM                   | 2.3.1             |
| MyBatis-Generator     | 代码生成插件            | 1.3.7             |
| MySQL                 | 数据库               | 8.0.31            |
| Redis                 | 缓存                | 6.2               |
| RabbitMQ              | 消息队列              | 3.10-management   |
| Canal                 | 数据库增量日志解析工具   | 1.1.5             |
| ShardingSphere-JDBC   | 读写分离、数据分片     | 5.1.2             |
| AliPay-SDK            | 支付宝开源开发工具     | 4.38.105.ALL      |
| JWT                   | Json Web Token        | -             |

# 目录结构

```shell
zh-mall
├─doc               --	SQL、postman 文件
├─zh-mall-admin	    --	后台管理系统
├─zh-mall-app	    --	前台商城系统
├─zh-mall-common    --	封装的常用工具类、常量、异常处理
├─zh-mall-generator --	MyBatis-Generator 插件生成的代码
├─zh-mall-pay	    --	支付功能实现
└─zh-mall-security  --	Spring Security 相关配置、处理
```
