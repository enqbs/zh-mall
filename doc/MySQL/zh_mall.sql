/*
 Navicat Premium Data Transfer

 Source Server         : MySQL-02
 Source Server Type    : MySQL
 Source Server Version : 80031
 Source Host           : 192.168.43.2:3306
 Source Schema         : zh_mall

 Target Server Type    : MySQL
 Target Server Version : 80031
 File Encoding         : 65001

 Date: 20/11/2023 21:40:21
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `parent_id` int UNSIGNED NULL DEFAULT 0 COMMENT '父级ID,RootId:0',
  `title` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '菜单标题',
  `path` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '路由地址',
  `permissions_key` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限标识符',
  `sort` int UNSIGNED NULL DEFAULT NULL COMMENT '排序字段',
  `delete_status` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除,0:否、1:是',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '后台系统菜单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, 0, '系统用户管理', '', '', NULL, 0, '2023-11-01 16:57:05', '2023-11-01 16:57:05');
INSERT INTO `sys_menu` VALUES (2, 0, '系统角色管理', '', '', NULL, 0, '2023-11-01 16:58:00', '2023-11-01 16:58:00');
INSERT INTO `sys_menu` VALUES (3, 0, '系统菜单管理', '', '', NULL, 0, '2023-11-01 16:58:19', '2023-11-01 16:58:19');
INSERT INTO `sys_menu` VALUES (4, 0, '用户管理', '', '', NULL, 0, '2023-11-01 17:02:50', '2023-11-01 17:02:50');
INSERT INTO `sys_menu` VALUES (5, 0, '商品管理', '', '', NULL, 0, '2023-11-01 17:03:38', '2023-11-01 17:03:38');
INSERT INTO `sys_menu` VALUES (6, 0, '订单管理', '', '', NULL, 0, '2023-11-01 17:03:57', '2023-11-01 17:03:57');
INSERT INTO `sys_menu` VALUES (7, 0, '支付信息管理', '', '', NULL, 0, '2023-11-01 17:04:04', '2023-11-01 17:04:04');
INSERT INTO `sys_menu` VALUES (8, 0, '优惠券管理', '', '', NULL, 0, '2023-11-01 17:04:12', '2023-11-01 17:04:12');
INSERT INTO `sys_menu` VALUES (9, 1, '注册系统用户', '/register', 'SYS_USER:ADD', NULL, 0, '2023-11-01 17:07:29', '2023-11-01 17:07:29');
INSERT INTO `sys_menu` VALUES (10, 1, '修改系统用户', '/user/**', 'SYS_USER:UPDATE', NULL, 0, '2023-11-01 17:08:19', '2023-11-01 17:30:04');
INSERT INTO `sys_menu` VALUES (11, 1, '删除系统用户', '/user/**', 'SYS_USER:DELETE', NULL, 0, '2023-11-01 17:08:40', '2023-11-01 17:30:08');
INSERT INTO `sys_menu` VALUES (12, 2, '新增系统角色', '/role', 'SYS_ROLE:ADD', NULL, 0, '2023-11-01 17:28:33', '2023-11-01 17:28:33');
INSERT INTO `sys_menu` VALUES (13, 2, '修改系统角色', '/role/**', 'SYS_ROLE:UPDATE', NULL, 0, '2023-11-01 17:28:54', '2023-11-01 17:28:54');
INSERT INTO `sys_menu` VALUES (14, 2, '删除系统角色', '/role/**', 'SYS_ROLE:DELETE', NULL, 0, '2023-11-01 17:29:12', '2023-11-01 17:29:12');
INSERT INTO `sys_menu` VALUES (15, 3, '新增菜单', '/menu', 'SYS_MENU:ADD', NULL, 0, '2023-11-01 17:32:38', '2023-11-01 17:32:38');
INSERT INTO `sys_menu` VALUES (16, 3, '修改菜单', '/menu/**', 'SYS_MENU:UPDATE', NULL, 0, '2023-11-01 17:32:49', '2023-11-01 17:32:49');
INSERT INTO `sys_menu` VALUES (17, 3, '删除菜单', '/menu/**', 'SYS_MENU:DELETE', NULL, 0, '2023-11-01 17:33:00', '2023-11-01 17:33:00');
INSERT INTO `sys_menu` VALUES (18, 4, '修改会员信息', '/member/**', 'MEMBER:UPDATE', NULL, 0, '2023-11-01 17:35:37', '2023-11-01 17:35:37');
INSERT INTO `sys_menu` VALUES (19, 4, '删除会员信息', '/member/**', 'MEMBER:DELETE', NULL, 0, '2023-11-01 17:35:47', '2023-11-01 17:35:47');
INSERT INTO `sys_menu` VALUES (20, 5, '新增商品', '/product/**', 'PRODUCT:ADD', NULL, 0, '2023-11-01 17:37:36', '2023-11-01 17:37:36');
INSERT INTO `sys_menu` VALUES (21, 5, '修改商品', '/product/**', 'PRODUCT:UPDATE', NULL, 0, '2023-11-01 17:37:45', '2023-11-01 17:37:45');
INSERT INTO `sys_menu` VALUES (22, 5, '删除商品', '/product/**', 'PRODUCT:DELETE', NULL, 0, '2023-11-01 17:37:53', '2023-11-01 17:37:53');
INSERT INTO `sys_menu` VALUES (23, 6, '修改订单', '/order/**', 'ORDER:UPDATE', NULL, 0, '2023-11-01 17:39:11', '2023-11-01 17:39:11');
INSERT INTO `sys_menu` VALUES (24, 6, '删除订单', '/order/**', 'ORDER:DELETE', NULL, 0, '2023-11-01 17:39:21', '2023-11-01 17:39:21');
INSERT INTO `sys_menu` VALUES (25, 7, '修改支付信息', '/pay/**', 'PAY_INFO:UPDATE', NULL, 0, '2023-11-01 17:40:28', '2023-11-01 17:40:28');
INSERT INTO `sys_menu` VALUES (26, 7, '删除支付信息', '/pay/**', 'PAY_INFO:DELETE', NULL, 0, '2023-11-01 17:40:36', '2023-11-01 17:40:36');
INSERT INTO `sys_menu` VALUES (27, 8, '新增优惠券', '/coupon/**', 'COUPON:ADD', NULL, 0, '2023-11-20 13:33:34', '2023-11-20 13:34:41');
INSERT INTO `sys_menu` VALUES (28, 8, '修改优惠券', '/coupon/**', 'COUPON:UPDATE', NULL, 0, '2023-11-20 13:33:57', '2023-11-20 13:34:48');
INSERT INTO `sys_menu` VALUES (29, 8, '删除优惠券', '/coupon/**', 'COUPON:DELETE', NULL, 0, '2023-11-20 13:34:19', '2023-11-20 13:34:53');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色名',
  `role_key` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色标识符',
  `sort` int UNSIGNED NULL DEFAULT NULL COMMENT '排序字段',
  `delete_status` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除,0:否、1:是',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '后台系统角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'root', NULL, 0, '2023-11-01 16:49:29', '2023-11-01 16:49:29');
INSERT INTO `sys_role` VALUES (2, '系统管理员', 'admin', NULL, 0, '2023-11-01 16:49:49', '2023-11-01 16:49:49');
INSERT INTO `sys_role` VALUES (3, '系统用户', 'user', NULL, 0, '2023-11-01 16:49:58', '2023-11-01 16:49:58');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `role_id` int UNSIGNED NOT NULL COMMENT '角色ID',
  `menu_id` int UNSIGNED NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '后台系统角色菜单关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (1, 9);
INSERT INTO `sys_role_menu` VALUES (1, 10);
INSERT INTO `sys_role_menu` VALUES (1, 11);
INSERT INTO `sys_role_menu` VALUES (1, 12);
INSERT INTO `sys_role_menu` VALUES (1, 13);
INSERT INTO `sys_role_menu` VALUES (1, 14);
INSERT INTO `sys_role_menu` VALUES (1, 15);
INSERT INTO `sys_role_menu` VALUES (1, 16);
INSERT INTO `sys_role_menu` VALUES (1, 17);
INSERT INTO `sys_role_menu` VALUES (1, 18);
INSERT INTO `sys_role_menu` VALUES (1, 19);
INSERT INTO `sys_role_menu` VALUES (1, 20);
INSERT INTO `sys_role_menu` VALUES (1, 21);
INSERT INTO `sys_role_menu` VALUES (1, 22);
INSERT INTO `sys_role_menu` VALUES (1, 23);
INSERT INTO `sys_role_menu` VALUES (1, 24);
INSERT INTO `sys_role_menu` VALUES (1, 25);
INSERT INTO `sys_role_menu` VALUES (1, 26);
INSERT INTO `sys_role_menu` VALUES (1, 27);
INSERT INTO `sys_role_menu` VALUES (1, 28);
INSERT INTO `sys_role_menu` VALUES (1, 29);
INSERT INTO `sys_role_menu` VALUES (2, 18);
INSERT INTO `sys_role_menu` VALUES (2, 20);
INSERT INTO `sys_role_menu` VALUES (2, 21);
INSERT INTO `sys_role_menu` VALUES (2, 23);
INSERT INTO `sys_role_menu` VALUES (2, 25);
INSERT INTO `sys_role_menu` VALUES (2, 27);
INSERT INTO `sys_role_menu` VALUES (2, 28);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '密码',
  `nick_name` varchar(24) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `photo` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户头像',
  `delete_status` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除,0:否、1:是',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unq_username_create_time`(`username` ASC, `create_time` DESC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '后台系统用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'root', '$2a$10$54xVfEDj4NdVu0KqentMnuByINHrMP3fsZzJJ2mWJHZCq82ODVqIS', NULL, NULL, 0, '2023-10-28 10:31:54', '2023-10-28 10:31:54');
INSERT INTO `sys_user` VALUES (2, 'admin', '$2a$10$633GDlPwzGLnZ5XqHnnDi.Y9yynlFz3kXfMkIGsmS7QVg4Rm7ESrG', NULL, NULL, 0, '2023-11-01 17:53:23', '2023-11-01 17:53:23');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_id` int UNSIGNED NOT NULL COMMENT '用户ID',
  `role_id` int UNSIGNED NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '后台系统用户角色关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1);
INSERT INTO `sys_user_role` VALUES (2, 2);

-- ----------------------------
-- Table structure for tb_coupon
-- ----------------------------
DROP TABLE IF EXISTS `tb_coupon`;
CREATE TABLE `tb_coupon`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `product_id` int UNSIGNED NULL DEFAULT NULL COMMENT '商品ID',
  `denomination` decimal(10, 2) UNSIGNED NULL DEFAULT NULL COMMENT '优惠券面额',
  `condition` decimal(10, 2) UNSIGNED NULL DEFAULT NULL COMMENT '满足多少金额可使用',
  `quantity` int UNSIGNED NULL DEFAULT NULL COMMENT '优惠券数量',
  `start_date` date NULL DEFAULT NULL COMMENT '起始日期',
  `end_date` date NULL DEFAULT NULL COMMENT '结束日期',
  `status` tinyint UNSIGNED NOT NULL DEFAULT 1 COMMENT '是否有效,0:无效、1:有效',
  `delete_status` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除,0:否、1:是',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '优惠券表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_coupon
-- ----------------------------
INSERT INTO `tb_coupon` VALUES (1, NULL, 10.00, 100.00, 100, '2023-11-18', '2024-01-01', 1, 0, '2023-11-18 05:52:21', '2023-11-18 15:04:39');

-- ----------------------------
-- Table structure for tb_home_banner
-- ----------------------------
DROP TABLE IF EXISTS `tb_home_banner`;
CREATE TABLE `tb_home_banner`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `product_id` int UNSIGNED NULL DEFAULT NULL COMMENT '商品ID',
  `picture` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'banner图URL',
  `status` tinyint UNSIGNED NOT NULL DEFAULT 1 COMMENT '是否有效,0:否、1:是',
  `delete_status` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除,0:否、1:是',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '首页横幅表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_home_banner
-- ----------------------------

-- ----------------------------
-- Table structure for tb_home_recommend_advertise
-- ----------------------------
DROP TABLE IF EXISTS `tb_home_recommend_advertise`;
CREATE TABLE `tb_home_recommend_advertise`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `product_id` int UNSIGNED NULL DEFAULT NULL COMMENT '商品ID',
  `picture` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '推荐广告图URL',
  `status` tinyint UNSIGNED NOT NULL DEFAULT 1 COMMENT '是否有效,0:否、1:是',
  `delete_status` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除,0:否、1:是',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '首页推荐广告表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_home_recommend_advertise
-- ----------------------------

-- ----------------------------
-- Table structure for tb_home_slide
-- ----------------------------
DROP TABLE IF EXISTS `tb_home_slide`;
CREATE TABLE `tb_home_slide`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `product_id` int UNSIGNED NULL DEFAULT NULL COMMENT '商品ID',
  `picture` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '轮播图URL',
  `status` tinyint UNSIGNED NOT NULL DEFAULT 1 COMMENT '是否有效,0:否、1:是',
  `delete_status` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除,0:否、1:是',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '首页轮播图表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_home_slide
-- ----------------------------
INSERT INTO `tb_home_slide` VALUES (1, NULL, 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/slider-img/9c27cc37533141bd9b6002332d946fc4.jpg', 1, 0, '2023-10-11 18:32:14', '2023-10-11 18:32:14');
INSERT INTO `tb_home_slide` VALUES (2, NULL, 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/slider-img/15486dbce1d349359592d1b69d931718.jpg', 1, 0, '2023-10-11 18:32:14', '2023-10-11 18:32:14');
INSERT INTO `tb_home_slide` VALUES (3, NULL, 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/slider-img/916e8251bb2641c4b04ccc567472d138.jpg', 1, 0, '2023-10-11 18:32:14', '2023-10-11 18:32:14');
INSERT INTO `tb_home_slide` VALUES (4, NULL, 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/slider-img/ec293b20393f46beb929c0dfcf536550.jpg', 1, 0, '2023-10-11 18:32:14', '2023-10-11 18:32:14');
INSERT INTO `tb_home_slide` VALUES (5, NULL, 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/slider-img/4949d60daec54ba7a8a648e29737d39c.jpg', 1, 0, '2023-10-11 18:32:14', '2023-10-11 18:32:14');

-- ----------------------------
-- Table structure for tb_message_queue_log
-- ----------------------------
DROP TABLE IF EXISTS `tb_message_queue_log`;
CREATE TABLE `tb_message_queue_log`  (
  `message_id` bigint UNSIGNED NOT NULL COMMENT '主键、消息id',
  `exchange` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '消息队列交换机',
  `routing_key` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '路由key',
  `queue` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '队列名',
  `content` json NULL COMMENT '消息内容',
  `delay` int NULL DEFAULT NULL COMMENT '延迟时间',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '状态，-1错误发送、0新建、1已发送',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`message_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'MQ消息持久化表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_message_queue_log
-- ----------------------------

-- ----------------------------
-- Table structure for tb_order
-- ----------------------------
DROP TABLE IF EXISTS `tb_order`;
CREATE TABLE `tb_order`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `order_no` bigint UNSIGNED NOT NULL COMMENT '订单号',
  `order_sc` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '订单流水号(保留字段)',
  `user_id` int UNSIGNED NOT NULL COMMENT '用户ID',
  `coupon_id` int UNSIGNED NULL DEFAULT NULL COMMENT '优惠券ID',
  `postage` decimal(10, 2) UNSIGNED NULL DEFAULT NULL COMMENT '运费',
  `amount` decimal(10, 2) UNSIGNED NOT NULL COMMENT '订单金额',
  `coupon_amount` decimal(10, 2) UNSIGNED NULL DEFAULT NULL COMMENT '优惠券金额',
  `discount_amount` decimal(10, 2) UNSIGNED NULL DEFAULT NULL COMMENT '会员优惠(折扣)金额',
  `actual_amount` decimal(10, 2) UNSIGNED NOT NULL COMMENT '实付金额',
  `payment_type` tinyint UNSIGNED NULL DEFAULT NULL COMMENT '支付方式,1:支付宝、2:微信支付、3:其他',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '订单状态,-1:订单超时、0:未支付、1:已支付(待发货)、2:待收货、3:已签收',
  `delete_status` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除,0:否、1:是',
  `consume_version` int UNSIGNED NULL DEFAULT NULL COMMENT '消费版本号(乐观锁字段)',
  `sharding` int UNSIGNED NULL DEFAULT NULL COMMENT '数据分片保留字段',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `payment_time` datetime NULL DEFAULT NULL COMMENT '付款时间',
  `ship_time` datetime NULL DEFAULT NULL COMMENT '发货时间',
  `sign_receipt_time` datetime NULL DEFAULT NULL COMMENT '签收时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unq_order_no_sc_user_id_create_time`(`order_no` ASC, `order_sc` ASC, `user_id` ASC, `create_time` DESC) USING BTREE,
  INDEX `idx_user_id_create_time`(`user_id` ASC, `create_time` DESC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_order
-- ----------------------------

-- ----------------------------
-- Table structure for tb_order_item
-- ----------------------------
DROP TABLE IF EXISTS `tb_order_item`;
CREATE TABLE `tb_order_item`  (
  `order_no` bigint UNSIGNED NOT NULL COMMENT '订单号',
  `sku_id` int UNSIGNED NOT NULL COMMENT '商品规格ID',
  `product_id` int UNSIGNED NOT NULL COMMENT '商品ID',
  `sku_title` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品规格标题',
  `sku_param` json NULL COMMENT '商品规格参数',
  `sku_picture` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品规格图片',
  `num` int UNSIGNED NULL DEFAULT NULL COMMENT '购买数量',
  `price` decimal(10, 2) UNSIGNED NULL DEFAULT NULL COMMENT '商品单价',
  `discount_price` decimal(10, 2) UNSIGNED NULL DEFAULT NULL COMMENT '商品优惠价格',
  `actual_price` decimal(10, 2) UNSIGNED NULL DEFAULT NULL COMMENT '商品实付单价',
  `total_price` decimal(10, 2) UNSIGNED NULL DEFAULT NULL COMMENT '商品总价',
  `sharding` int UNSIGNED NULL DEFAULT NULL COMMENT '数据分片保留字段',
  PRIMARY KEY (`order_no`, `sku_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单项表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_order_item
-- ----------------------------

-- ----------------------------
-- Table structure for tb_order_logistics_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_order_logistics_info`;
CREATE TABLE `tb_order_logistics_info`  (
  `order_no` bigint UNSIGNED NOT NULL COMMENT '订单号',
  `logistics_no` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '物流单号',
  `logistics_title` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '物流公司名称',
  `delete_status` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除,0:否、1:是',
  `sharding` int UNSIGNED NULL DEFAULT NULL COMMENT '数据分片保留字段',
  PRIMARY KEY (`order_no`, `logistics_no`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单物流表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_order_logistics_info
-- ----------------------------

-- ----------------------------
-- Table structure for tb_order_refund
-- ----------------------------
DROP TABLE IF EXISTS `tb_order_refund`;
CREATE TABLE `tb_order_refund`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `order_no` bigint UNSIGNED NOT NULL COMMENT '订单号',
  `user_id` int UNSIGNED NOT NULL COMMENT '用户ID',
  `nick_name` varchar(24) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `photo` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户头像',
  `picture` json NULL COMMENT '图片',
  `content` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '申请退款理由',
  `reply` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '系统管理员回复',
  `refund_amount` decimal(10, 2) UNSIGNED NOT NULL COMMENT '退款金额',
  `status` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '退款状态,0:正在处理、1:已退款、2:退款失败',
  `delete_status` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除,0:否、1:是',
  `consume_version` int UNSIGNED NULL DEFAULT NULL COMMENT '消费版本号(乐观锁字段)',
  `sharding` int UNSIGNED NULL DEFAULT NULL COMMENT '数据分片保留字段',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `refund_time` datetime NULL DEFAULT NULL COMMENT '退款时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unq_order_no_user_id_create_time`(`order_no` ASC, `user_id` ASC, `create_time` DESC) USING BTREE,
  INDEX `idx_user_id_create_time`(`user_id` ASC, `create_time` DESC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单申请退款表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_order_refund
-- ----------------------------

-- ----------------------------
-- Table structure for tb_order_refund_item
-- ----------------------------
DROP TABLE IF EXISTS `tb_order_refund_item`;
CREATE TABLE `tb_order_refund_item`  (
  `order_no` bigint UNSIGNED NOT NULL COMMENT '订单号',
  `sku_id` int UNSIGNED NOT NULL COMMENT '商品规格ID',
  `product_id` int UNSIGNED NOT NULL COMMENT '商品ID',
  `sku_title` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品规格标题',
  `sku_param` json NULL COMMENT '商品规格参数',
  `sku_picture` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品规格图片',
  `num` int UNSIGNED NOT NULL COMMENT '购买数量',
  `price` decimal(10, 2) UNSIGNED NOT NULL COMMENT '商品单价',
  `discount_price` decimal(10, 2) UNSIGNED NULL DEFAULT NULL COMMENT '商品优惠价格',
  `actual_price` decimal(10, 2) UNSIGNED NULL DEFAULT NULL COMMENT '商品实付单价',
  `total_price` decimal(10, 2) UNSIGNED NULL DEFAULT NULL COMMENT '商品总价',
  `refund_amount` decimal(10, 2) UNSIGNED NULL DEFAULT NULL COMMENT '商品退款金额',
  `sharding` int UNSIGNED NULL DEFAULT NULL COMMENT '数据分片保留字段',
  PRIMARY KEY (`order_no`, `sku_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '退款订单项表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_order_refund_item
-- ----------------------------

-- ----------------------------
-- Table structure for tb_order_shipping_address
-- ----------------------------
DROP TABLE IF EXISTS `tb_order_shipping_address`;
CREATE TABLE `tb_order_shipping_address`  (
  `order_no` bigint UNSIGNED NOT NULL COMMENT '订单号',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '收件人名称',
  `tel_no` varchar(24) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '收件人电话',
  `address` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '收件人地址',
  `detail_address` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '收件人详细地址',
  `sharding` int UNSIGNED NULL DEFAULT NULL COMMENT '数据分片保留字段',
  PRIMARY KEY (`order_no`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单收货地址表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_order_shipping_address
-- ----------------------------

-- ----------------------------
-- Table structure for tb_pay_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_pay_info`;
CREATE TABLE `tb_pay_info`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `order_no` bigint UNSIGNED NOT NULL COMMENT '订单号',
  `user_id` int UNSIGNED NOT NULL COMMENT '用户ID',
  `nick_name` varchar(24) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `photo` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户头像',
  `pay_amount` decimal(10, 2) UNSIGNED NOT NULL COMMENT '支付金额',
  `status` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '支付状态,0:未支付、1:已支付、2:已超时',
  `delete_status` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除,0:否、1:是',
  `consume_version` int UNSIGNED NULL DEFAULT NULL COMMENT '消费版本号(乐观锁字段)',
  `sharding` int UNSIGNED NULL DEFAULT NULL COMMENT '数据分片保留字段',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `payment_time` datetime NULL DEFAULT NULL COMMENT '付款时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unq_order_no_user_id_create_time`(`order_no` ASC, `user_id` ASC, `create_time` DESC) USING BTREE,
  INDEX `idx_user_id_create_time`(`user_id` ASC, `create_time` DESC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '支付信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_pay_info
-- ----------------------------

-- ----------------------------
-- Table structure for tb_pay_platform
-- ----------------------------
DROP TABLE IF EXISTS `tb_pay_platform`;
CREATE TABLE `tb_pay_platform`  (
  `pay_info_id` bigint UNSIGNED NOT NULL COMMENT '支付信息ID',
  `order_no` bigint UNSIGNED NOT NULL COMMENT '订单号',
  `pay_type` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '支付方式',
  `platform` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '支付平台',
  `platform_no` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '支付平台流水号',
  `sharding` int UNSIGNED NULL DEFAULT NULL COMMENT '数据分片保留字段',
  PRIMARY KEY (`pay_info_id`, `order_no`) USING BTREE,
  UNIQUE INDEX `unq_order_platform_no`(`order_no` ASC, `platform_no` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '支付平台表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_pay_platform
-- ----------------------------

-- ----------------------------
-- Table structure for tb_pay_refund
-- ----------------------------
DROP TABLE IF EXISTS `tb_pay_refund`;
CREATE TABLE `tb_pay_refund`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `pay_info_id` bigint UNSIGNED NOT NULL COMMENT '支付信息ID',
  `order_no` bigint UNSIGNED NOT NULL COMMENT '订单号',
  `platform` tinyint UNSIGNED NULL DEFAULT NULL COMMENT '支付平台,1:支付宝、2:微信支付、3:其他',
  `platform_no` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '支付平台流水号',
  `pay_amount` decimal(10, 2) UNSIGNED NOT NULL COMMENT '支付金额',
  `refund_amount` decimal(10, 2) UNSIGNED NOT NULL COMMENT '退款金额',
  `delete_status` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除,0:否、1:是',
  `consume_version` int UNSIGNED NULL DEFAULT NULL COMMENT '消费版本号(乐观锁字段)',
  `sharding` int UNSIGNED NULL DEFAULT NULL COMMENT '数据分片保留字段',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `refund_time` datetime NULL DEFAULT NULL COMMENT '退款时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unq_order_no_create_time`(`order_no` ASC, `create_time` DESC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '支付退款记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_pay_refund
-- ----------------------------

-- ----------------------------
-- Table structure for tb_product
-- ----------------------------
DROP TABLE IF EXISTS `tb_product`;
CREATE TABLE `tb_product`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `product_category_id` int UNSIGNED NOT NULL COMMENT '分类ID',
  `title` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品标题',
  `sub_title` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品副标题',
  `picture` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品展示图片',
  `slider` json NULL COMMENT '商品轮播图',
  `lowest_price` decimal(10, 2) UNSIGNED NULL DEFAULT NULL COMMENT '商品最低价格',
  `virtual_price` decimal(10, 2) UNSIGNED NULL DEFAULT NULL COMMENT '商品虚拟价格(删除线)',
  `sale` int UNSIGNED NULL DEFAULT NULL COMMENT '商品销量',
  `saleable_status` tinyint UNSIGNED NULL DEFAULT 1 COMMENT '是否上架,0:否、1:是',
  `new_status` tinyint UNSIGNED NULL DEFAULT 0 COMMENT '是否新品,0:否、1:是',
  `recommend_status` tinyint UNSIGNED NULL DEFAULT 0 COMMENT '是否推荐,0:否、1:是',
  `delete_status` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除,0:否、1:是',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_product_category_id_create_time`(`product_category_id` ASC, `create_time` DESC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_product
-- ----------------------------
INSERT INTO `tb_product` VALUES (1, 1, 'Xiaomi MIX Fold 2', '超轻薄折叠机身｜小米自研微水滴形态转轴｜内外双旗舰屏幕｜徕卡专业光学镜头｜徕卡原生双画质', 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/show-img/b63bf6e853524d7c9fe515c7287d8b51.png', '[\"https://zh-product.oss-cn-shenzhen.aliyuncs.com/slider-img/78459a521d084739b0d34373f527d23e.jpg\", \"https://zh-product.oss-cn-shenzhen.aliyuncs.com/slider-img/6babd6ce1f314cfab32e1da8561df009.jpg\", \"https://zh-product.oss-cn-shenzhen.aliyuncs.com/slider-img/a759378a5932435c9e8452868023e3f7.jpg\"]', NULL, NULL, NULL, 1, 0, 0, 0, '2023-10-11 18:01:50', '2023-10-26 06:12:51');
INSERT INTO `tb_product` VALUES (2, 1, 'Redmi K50 至尊版', '骁龙8+「狂暴调校」｜ 定制 1.5K 旗舰直屏 ｜ 120W神仙秒充丨1 亿像素光学防抖相机｜ 电竞级 VC 散热 | 屏下指纹', 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/show-img/db31a83715ae41379b58877670215b4c.png', '[\"https://zh-product.oss-cn-shenzhen.aliyuncs.com/slider-img/4244c87bb1bc43c39da621938fb0d2a1.png\", \"https://zh-product.oss-cn-shenzhen.aliyuncs.com/slider-img/b08e21b130d34d1ebd756234832e7f14.png\", \"https://zh-product.oss-cn-shenzhen.aliyuncs.com/slider-img/d269c537a8ab4162ae0d3cbac3aa52f3.png\", \"https://zh-product.oss-cn-shenzhen.aliyuncs.com/slider-img/85dec4b01dee4e8fb102970c01b8d8b9.png\", \"https://zh-product.oss-cn-shenzhen.aliyuncs.com/slider-img/4d6ca860093143819b209201fc150ba1.png\"]', NULL, NULL, NULL, 1, 0, 0, 0, '2023-10-11 18:01:50', '2023-10-11 18:01:50');
INSERT INTO `tb_product` VALUES (3, 1, 'Xiaomi 12S Ultra', '徕卡专业光学镜头｜骁龙8+ 旗舰处理器｜徕卡原生双画质｜1 英寸大底专业主摄｜小米澎湃 P1 快充芯片｜全场景疾速抓拍｜IP68级防尘防水', 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/show-img/cae7e8c393884b0eb687b912ff454a2c.png', '[\"https://zh-product.oss-cn-shenzhen.aliyuncs.com/slider-img/2e52833835074f2b94d346dca4628959.png\", \"https://zh-product.oss-cn-shenzhen.aliyuncs.com/slider-img/a3576c5d60b4450c916ea4263bb59c31.png\", \"https://zh-product.oss-cn-shenzhen.aliyuncs.com/slider-img/c08f5368353d40bf889dec8cac739be4.png\"]', NULL, NULL, NULL, 1, 0, 0, 0, '2023-10-11 18:01:50', '2023-10-11 18:01:50');
INSERT INTO `tb_product` VALUES (4, 1, 'Xiaomi 12S Pro', '骁龙8+ 旗舰处理器 | 徕卡光学镜头 | 徕卡原生双画质 | 徕卡水印、大师镜头包 | 全场景疾速抓拍 | 5000万三主摄 | 小米自研澎湃P1芯片 | 120W小米澎湃秒充 | 4600mAh大电量 | 2K AMOLED屏', 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/show-img/d612969eee074d40845a9aca6ecf707b.png', '[\"https://zh-product.oss-cn-shenzhen.aliyuncs.com/slider-img/7d72d2fa7f7c428e8e93c434920ffb3e.png\", \"https://zh-product.oss-cn-shenzhen.aliyuncs.com/slider-img/ad4ec1ae84a940b1a81a7d8530b8bf4f.png\", \"https://zh-product.oss-cn-shenzhen.aliyuncs.com/slider-img/89c1cf4136d8465196974a1aacad7dd0.png\", \"https://zh-product.oss-cn-shenzhen.aliyuncs.com/slider-img/26ba2fdd3557400a91e7455d8e7d5c03.png\", \"https://zh-product.oss-cn-shenzhen.aliyuncs.com/slider-img/69c617b911004be4b76bd1ef227420f9.png\"]', NULL, NULL, NULL, 1, 0, 0, 0, '2023-10-11 18:01:50', '2023-10-11 18:01:50');
INSERT INTO `tb_product` VALUES (5, 1, 'Xiaomi 12S', '骁龙8+ 旗舰处理器 | 单手可握好手感 | 徕卡光学镜头 | 徕卡原生双画质 | 全场景疾速抓拍 | 4500mAh大电量 | 6.28″AMOLED屏幕', 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/show-img/005703996fb84f8da430edcc8dbf2a44.png', '[\"https://zh-product.oss-cn-shenzhen.aliyuncs.com/slider-img/646e018aab514dc1bdd125e5b85da5f9.png\", \"https://zh-product.oss-cn-shenzhen.aliyuncs.com/slider-img/736f62d9da2e4326a5b1cf0357eb66bd.png\", \"https://zh-product.oss-cn-shenzhen.aliyuncs.com/slider-img/c3b41a9a0b1d452d82fcf1600d411dad.png\", \"https://zh-product.oss-cn-shenzhen.aliyuncs.com/slider-img/030c1fcd68774b31a3447c2f9e5b1625.png\", \"https://zh-product.oss-cn-shenzhen.aliyuncs.com/slider-img/17a5e9951a77448cac85ab8e515a0395.png\", \"https://zh-product.oss-cn-shenzhen.aliyuncs.com/slider-img/e46652f0cdaf4937852bdcde5434ede7.png\"]', NULL, NULL, NULL, 1, 0, 0, 0, '2023-10-11 18:01:50', '2023-10-11 18:01:50');
INSERT INTO `tb_product` VALUES (6, 2, '小米电视大师 82英寸', '多分区背光 | 双120Hz高刷 | 4GB+64GB', 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/show-img/c5abf0d0321e4dd79dba7d2f7cbd65d0.jpg', '[\"https://zh-product.oss-cn-shenzhen.aliyuncs.com/slider-img/ca6b13a94191423ab75c7621f0965b46.jpg\", \"https://zh-product.oss-cn-shenzhen.aliyuncs.com/slider-img/b2c70452e91b4dfa82a3f6bc66191084.jpg\", \"https://zh-product.oss-cn-shenzhen.aliyuncs.com/slider-img/0e20642077c147cea2f4c93960f04081.jpg\", \"https://zh-product.oss-cn-shenzhen.aliyuncs.com/slider-img/333594ea104d4d0da501fc3be8db74bd.jpg\", \"https://zh-product.oss-cn-shenzhen.aliyuncs.com/slider-img/50b7b4e2b7454a79a53005f1e85f7725.jpg\"]', NULL, NULL, NULL, 1, 0, 0, 0, '2023-10-11 18:01:50', '2023-10-11 18:01:50');
INSERT INTO `tb_product` VALUES (7, 2, 'Redmi MAX 98英寸', '120Hz高刷新率 | 4GB+64GB | MEMC运动补偿', 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/show-img/d97dfcf8add043be8837ac377f24b79e.jpg', '[\"https://zh-product.oss-cn-shenzhen.aliyuncs.com/slider-img/d79fcffa8d374dec9f2ea69a920fb5f5.jpg\", \"https://zh-product.oss-cn-shenzhen.aliyuncs.com/slider-img/3e225b262b2144d3b6d1c50f186d3da8.jpg\", \"https://zh-product.oss-cn-shenzhen.aliyuncs.com/slider-img/2c3315b1d41f41a3a97b4c88dab4cf74.jpg\", \"https://zh-product.oss-cn-shenzhen.aliyuncs.com/slider-img/3eb37f70ab8a4dda85df0a910eb9c734.jpg\", \"https://zh-product.oss-cn-shenzhen.aliyuncs.com/slider-img/5b43592c44de4ae59cf05112f73241a0.jpg\"]', NULL, NULL, NULL, 1, 0, 0, 0, '2023-10-11 18:01:50', '2023-10-11 18:01:50');
INSERT INTO `tb_product` VALUES (8, 2, '小米电视 大师 65英寸OLED', 'OLED自发光屏 | 百万级对比度 | 双120Hz高刷', 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/show-img/9fd43d1e3c034b8dbf1cf4ea4d4f72e6.jpg', '[\"https://zh-product.oss-cn-shenzhen.aliyuncs.com/slider-img/d4ff6770dbf14d0788bfc4fd4508047d.jpg\", \"https://zh-product.oss-cn-shenzhen.aliyuncs.com/slider-img/7e18f06991f0475ca88d731c28fc6cca.jpg\", \"https://zh-product.oss-cn-shenzhen.aliyuncs.com/slider-img/461fe1f51e66436bb38d6cecbefb90b4.jpg\", \"https://zh-product.oss-cn-shenzhen.aliyuncs.com/slider-img/a23cbcf903b34b1bb924608c1dd29a4d.jpg\", \"https://zh-product.oss-cn-shenzhen.aliyuncs.com/slider-img/cc6e4f49b7a04521868ed9a5f32fbb04.jpg\"]', NULL, NULL, NULL, 1, 0, 0, 0, '2023-10-11 18:01:50', '2023-10-11 18:01:50');
INSERT INTO `tb_product` VALUES (9, 3, 'Redmi Book Pro 14 2022', '全新12代英特尔处理器 | Windows 11 家庭中文版 | 2.5K 120Hz高清屏 | 可选MX550独立显卡', 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/show-img/27def103c74e4e8bac09d25ec4fa7475.jpg', '[\"https://zh-product.oss-cn-shenzhen.aliyuncs.com/slider-img/57a0a4d138014a489b231f0bda908ca6.png\"]', NULL, NULL, NULL, 1, 0, 0, 0, '2023-10-11 18:01:50', '2023-10-11 18:01:50');
INSERT INTO `tb_product` VALUES (10, 3, 'Redmi Book Pro 15 2022', '全新12代英特尔处理器 | Windows 11 家庭中文版 | 可选RTX 2050高性能独立显卡 | 3.2K 90Hz 原色超清屏', 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/show-img/71b9d985af86466289662ac420e33a81.jpg', '[\"https://zh-product.oss-cn-shenzhen.aliyuncs.com/slider-img/36a3c760c3c8403193199be470724da8.png\"]', NULL, NULL, NULL, 1, 0, 0, 0, '2023-10-11 18:01:50', '2023-10-11 18:01:50');
INSERT INTO `tb_product` VALUES (11, 3, 'Xiaomi Book Pro 16 2022', '4K OLED 触控屏 | CNC一体精雕工艺 | 压感触控板 | 12代英特尔®酷睿™处理器 | 可选RTX2050光追独显', 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/show-img/320206d87a9247e7b41b28b25f43b0d4.jpg', '[\"https://zh-product.oss-cn-shenzhen.aliyuncs.com/slider-img/ded9a6f23d3e4210bd323fd4576a1724.png\"]', NULL, NULL, NULL, 1, 0, 0, 0, '2023-10-11 18:01:50', '2023-10-11 18:01:50');

-- ----------------------------
-- Table structure for tb_product_category
-- ----------------------------
DROP TABLE IF EXISTS `tb_product_category`;
CREATE TABLE `tb_product_category`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `parent_id` int UNSIGNED NULL DEFAULT NULL COMMENT '父级ID,RootId:0',
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '分类名称',
  `icon` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'icon地址',
  `sort` int UNSIGNED NULL DEFAULT 0 COMMENT '排序字段',
  `navi_status` tinyint UNSIGNED NULL DEFAULT 0 COMMENT '是否首页导航显示,0:否、1:是',
  `delete_status` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除,0:否、1:是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品分类表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_product_category
-- ----------------------------
INSERT INTO `tb_product_category` VALUES (1, 0, '手机', NULL, 0, 0, 0);
INSERT INTO `tb_product_category` VALUES (2, 0, '电视', NULL, 0, 0, 0);
INSERT INTO `tb_product_category` VALUES (3, 0, '笔记本 平板', NULL, 0, 0, 0);
INSERT INTO `tb_product_category` VALUES (4, 0, '出行 穿戴', NULL, 0, 0, 0);
INSERT INTO `tb_product_category` VALUES (5, 0, '耳机 音箱', NULL, 0, 0, 0);
INSERT INTO `tb_product_category` VALUES (6, 0, '家电', NULL, 0, 0, 0);
INSERT INTO `tb_product_category` VALUES (7, 0, '智能 路由器', NULL, 0, 0, 0);
INSERT INTO `tb_product_category` VALUES (8, 0, '电源 配件', NULL, 0, 0, 0);
INSERT INTO `tb_product_category` VALUES (9, 0, '健康 儿童', NULL, 0, 0, 0);
INSERT INTO `tb_product_category` VALUES (10, 0, '生活 箱包', NULL, 0, 0, 0);

-- ----------------------------
-- Table structure for tb_product_category_attribute
-- ----------------------------
DROP TABLE IF EXISTS `tb_product_category_attribute`;
CREATE TABLE `tb_product_category_attribute`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品分类规格名称',
  `delete_status` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除,0:否、1:是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品分类属性表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_product_category_attribute
-- ----------------------------

-- ----------------------------
-- Table structure for tb_product_category_attribute_relation
-- ----------------------------
DROP TABLE IF EXISTS `tb_product_category_attribute_relation`;
CREATE TABLE `tb_product_category_attribute_relation`  (
  `product_category_id` int UNSIGNED NOT NULL COMMENT '商品分类ID',
  `product_category_attribute_id` int UNSIGNED NOT NULL COMMENT '商品分类属性ID',
  PRIMARY KEY (`product_category_id`, `product_category_attribute_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品分类属性关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_product_category_attribute_relation
-- ----------------------------

-- ----------------------------
-- Table structure for tb_product_comment
-- ----------------------------
DROP TABLE IF EXISTS `tb_product_comment`;
CREATE TABLE `tb_product_comment`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `product_id` int UNSIGNED NOT NULL COMMENT '商品ID',
  `parent_id` int NULL DEFAULT 0 COMMENT '父ID',
  `order_no` bigint NULL DEFAULT NULL COMMENT '订单号',
  `user_id` int UNSIGNED NOT NULL COMMENT '用户ID',
  `to_user_id` int NULL DEFAULT NULL COMMENT '被回复的用户ID',
  `nick_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `to_nick_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '被回复的用户昵称',
  `photo` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户头像',
  `sku_title` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '购买的商品规格',
  `content` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '评论内容',
  `picture` json NULL COMMENT '评论图片',
  `star` tinyint UNSIGNED NULL DEFAULT 5 COMMENT '评论数量,0-5',
  `like` int UNSIGNED NULL DEFAULT NULL COMMENT '评论点赞数量',
  `delete_status` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除,0:否、1:是',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_product_id_create_time`(`product_id` ASC, `create_time` DESC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品评价表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_product_comment
-- ----------------------------

-- ----------------------------
-- Table structure for tb_product_overview
-- ----------------------------
DROP TABLE IF EXISTS `tb_product_overview`;
CREATE TABLE `tb_product_overview`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `product_id` int UNSIGNED NOT NULL COMMENT '商品ID',
  `picture` json NULL COMMENT '商品概述图片',
  `delete_status` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除,0:否、1:是',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_product_id`(`product_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品概述表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_product_overview
-- ----------------------------

-- ----------------------------
-- Table structure for tb_product_spec
-- ----------------------------
DROP TABLE IF EXISTS `tb_product_spec`;
CREATE TABLE `tb_product_spec`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `product_id` int UNSIGNED NOT NULL COMMENT '商品ID',
  `picture` json NULL COMMENT '商品概述图片',
  `delete_status` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除,0:否、1:是',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_product_id`(`product_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品参数表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_product_spec
-- ----------------------------

-- ----------------------------
-- Table structure for tb_sku
-- ----------------------------
DROP TABLE IF EXISTS `tb_sku`;
CREATE TABLE `tb_sku`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `product_id` int UNSIGNED NOT NULL COMMENT '商品ID',
  `picture` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品规格图片',
  `title` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品规格标题',
  `param` json NULL COMMENT '商品规格参数',
  `price` decimal(10, 2) UNSIGNED NOT NULL COMMENT '商品规格单价',
  `saleable_status` tinyint UNSIGNED NOT NULL DEFAULT 1 COMMENT '是否上架,0:否、1:是',
  `delete_status` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除,0:否、1:是',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_product_id_create_time`(`product_id` ASC, `create_time` DESC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 62 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品规格表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_sku
-- ----------------------------
INSERT INTO `tb_sku` VALUES (1, 1, 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/sku-img/018a74063f074821927b910eb82afb90.jpg', NULL, '[{\"paramKey\": \"选择版本\", \"paramValue\": \"12GB+256GB\"}, {\"paramKey\": \"选择颜色\", \"paramValue\": \"月影黑\"}]', 8999.00, 1, 0, '2023-10-11 18:19:02', '2023-10-26 05:45:38');
INSERT INTO `tb_sku` VALUES (2, 1, 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/sku-img/018a74063f074821927b910eb82afb90.jpg', NULL, '[{\"paramKey\": \"选择版本\", \"paramValue\": \"12GB+512GB\"}, {\"paramKey\": \"选择颜色\", \"paramValue\": \"月影黑\"}]', 9999.00, 1, 0, '2023-10-11 18:19:02', '2023-10-11 18:19:02');
INSERT INTO `tb_sku` VALUES (3, 1, 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/sku-img/018a74063f074821927b910eb82afb90.jpg', NULL, '[{\"paramKey\": \"选择版本\", \"paramValue\": \"12GB+1024GB\"}, {\"paramKey\": \"选择颜色\", \"paramValue\": \"月影黑\"}]', 11999.00, 1, 0, '2023-10-11 18:19:03', '2023-10-11 18:19:03');
INSERT INTO `tb_sku` VALUES (4, 1, 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/sku-img/e139610ab78f4461984f74443bd59354.jpg', NULL, '[{\"paramKey\": \"选择版本\", \"paramValue\": \"12GB+256GB\"}, {\"paramKey\": \"选择颜色\", \"paramValue\": \"星耀金\"}]', 8999.00, 1, 0, '2023-10-11 18:19:03', '2023-10-11 18:19:03');
INSERT INTO `tb_sku` VALUES (5, 1, 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/sku-img/e139610ab78f4461984f74443bd59354.jpg', NULL, '[{\"paramKey\": \"选择版本\", \"paramValue\": \"12GB+512GB\"}, {\"paramKey\": \"选择颜色\", \"paramValue\": \"星耀金\"}]', 9999.00, 1, 0, '2023-10-11 18:19:03', '2023-10-11 18:19:03');
INSERT INTO `tb_sku` VALUES (6, 1, 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/sku-img/e139610ab78f4461984f74443bd59354.jpg', NULL, '[{\"paramKey\": \"选择版本\", \"paramValue\": \"12GB+1024GB\"}, {\"paramKey\": \"选择颜色\", \"paramValue\": \"星耀金\"}]', 11999.00, 1, 0, '2023-10-11 18:19:03', '2023-10-11 18:19:03');
INSERT INTO `tb_sku` VALUES (7, 2, 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/sku-img/32f99bf9e50149dd9499a0810e62e937.png', NULL, '[{\"paramKey\": \"选择版本\", \"paramValue\": \"8GB+128GB\"}, {\"paramKey\": \"选择颜色\", \"paramValue\": \"雅黑\"}]', 2999.00, 1, 0, '2023-10-11 18:19:03', '2023-10-11 18:19:03');
INSERT INTO `tb_sku` VALUES (8, 2, 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/sku-img/32f99bf9e50149dd9499a0810e62e937.png', NULL, '[{\"paramKey\": \"选择版本\", \"paramValue\": \"8GB+256GB\"}, {\"paramKey\": \"选择颜色\", \"paramValue\": \"雅黑\"}]', 3099.00, 1, 0, '2023-10-11 18:19:03', '2023-10-11 18:19:03');
INSERT INTO `tb_sku` VALUES (9, 2, 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/sku-img/32f99bf9e50149dd9499a0810e62e937.png', NULL, '[{\"paramKey\": \"选择版本\", \"paramValue\": \"12GB+256GB\"}, {\"paramKey\": \"选择颜色\", \"paramValue\": \"雅黑\"}]', 3399.00, 1, 0, '2023-10-11 18:19:03', '2023-10-11 18:19:03');
INSERT INTO `tb_sku` VALUES (10, 2, 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/sku-img/32f99bf9e50149dd9499a0810e62e937.png', NULL, '[{\"paramKey\": \"选择版本\", \"paramValue\": \"12GB+512GB\"}, {\"paramKey\": \"选择颜色\", \"paramValue\": \"雅黑\"}]', 3699.00, 1, 0, '2023-10-11 18:19:03', '2023-10-11 18:19:03');
INSERT INTO `tb_sku` VALUES (11, 2, 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/sku-img/2d16dde9ea0d44e5b9f38f124837bde9.png', NULL, '[{\"paramKey\": \"选择版本\", \"paramValue\": \"8GB+128GB\"}, {\"paramKey\": \"选择颜色\", \"paramValue\": \"银迹\"}]', 2999.00, 1, 0, '2023-10-11 18:19:03', '2023-10-11 18:19:03');
INSERT INTO `tb_sku` VALUES (12, 2, 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/sku-img/2d16dde9ea0d44e5b9f38f124837bde9.png', NULL, '[{\"paramKey\": \"选择版本\", \"paramValue\": \"8GB+256GB\"}, {\"paramKey\": \"选择颜色\", \"paramValue\": \"银迹\"}]', 3099.00, 1, 0, '2023-10-11 18:19:03', '2023-10-11 18:19:03');
INSERT INTO `tb_sku` VALUES (13, 2, 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/sku-img/2d16dde9ea0d44e5b9f38f124837bde9.png', NULL, '[{\"paramKey\": \"选择版本\", \"paramValue\": \"12GB+256GB\"}, {\"paramKey\": \"选择颜色\", \"paramValue\": \"银迹\"}]', 3399.00, 1, 0, '2023-10-11 18:19:03', '2023-10-11 18:19:03');
INSERT INTO `tb_sku` VALUES (14, 2, 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/sku-img/2d16dde9ea0d44e5b9f38f124837bde9.png', NULL, '[{\"paramKey\": \"选择版本\", \"paramValue\": \"12GB+512GB\"}, {\"paramKey\": \"选择颜色\", \"paramValue\": \"银迹\"}]', 3699.00, 1, 0, '2023-10-11 18:19:03', '2023-10-11 18:19:03');
INSERT INTO `tb_sku` VALUES (15, 2, 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/sku-img/2800eaf3681945bc8c52e860383e1aeb.png', NULL, '[{\"paramKey\": \"选择版本\", \"paramValue\": \"8GB+128GB\"}, {\"paramKey\": \"选择颜色\", \"paramValue\": \"冰蓝\"}]', 2999.00, 1, 0, '2023-10-11 18:19:03', '2023-10-11 18:19:03');
INSERT INTO `tb_sku` VALUES (16, 2, 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/sku-img/2800eaf3681945bc8c52e860383e1aeb.png', NULL, '[{\"paramKey\": \"选择版本\", \"paramValue\": \"8GB+256GB\"}, {\"paramKey\": \"选择颜色\", \"paramValue\": \"冰蓝\"}]', 3099.00, 1, 0, '2023-10-11 18:19:03', '2023-10-11 18:19:03');
INSERT INTO `tb_sku` VALUES (17, 2, 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/sku-img/2800eaf3681945bc8c52e860383e1aeb.png', NULL, '[{\"paramKey\": \"选择版本\", \"paramValue\": \"12GB+256GB\"}, {\"paramKey\": \"选择颜色\", \"paramValue\": \"冰蓝\"}]', 3399.00, 1, 0, '2023-10-11 18:19:03', '2023-10-11 18:19:03');
INSERT INTO `tb_sku` VALUES (18, 2, 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/sku-img/2800eaf3681945bc8c52e860383e1aeb.png', NULL, '[{\"paramKey\": \"选择版本\", \"paramValue\": \"12GB+512GB\"}, {\"paramKey\": \"选择颜色\", \"paramValue\": \"冰蓝\"}]', 3699.00, 1, 0, '2023-10-11 18:19:03', '2023-10-11 18:19:03');
INSERT INTO `tb_sku` VALUES (19, 2, 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/sku-img/838cb1ac907040c3833d608b06e9bd74.png', NULL, '[{\"paramKey\": \"选择版本\", \"paramValue\": \"12GB+512GB\"}, {\"paramKey\": \"选择颜色\", \"paramValue\": \"冠军版\"}]', 4199.00, 1, 0, '2023-10-11 18:19:03', '2023-10-11 18:19:03');
INSERT INTO `tb_sku` VALUES (20, 3, 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/sku-img/0babe0434cee460084a4070e863778e1.png', NULL, '[{\"paramKey\": \"选择版本\", \"paramValue\": \"8GB+256GB\"}, {\"paramKey\": \"选择颜色\", \"paramValue\": \"经典黑\"}]', 5999.00, 1, 0, '2023-10-11 18:19:03', '2023-10-11 18:19:03');
INSERT INTO `tb_sku` VALUES (21, 3, 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/sku-img/0babe0434cee460084a4070e863778e1.png', NULL, '[{\"paramKey\": \"选择版本\", \"paramValue\": \"12GB+256GB\"}, {\"paramKey\": \"选择颜色\", \"paramValue\": \"经典黑\"}]', 6499.00, 1, 0, '2023-10-11 18:19:03', '2023-10-11 18:19:03');
INSERT INTO `tb_sku` VALUES (22, 3, 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/sku-img/0babe0434cee460084a4070e863778e1.png', NULL, '[{\"paramKey\": \"选择版本\", \"paramValue\": \"12GB+512GB\"}, {\"paramKey\": \"选择颜色\", \"paramValue\": \"经典黑\"}]', 6999.00, 1, 0, '2023-10-11 18:19:03', '2023-10-11 18:19:03');
INSERT INTO `tb_sku` VALUES (23, 3, 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/sku-img/e402727a2f5b42f99d8501f9dbdd9c28.png', NULL, '[{\"paramKey\": \"选择版本\", \"paramValue\": \"8GB+256GB\"}, {\"paramKey\": \"选择颜色\", \"paramValue\": \"冷杉绿\"}]', 5999.00, 1, 0, '2023-10-11 18:19:03', '2023-10-11 18:19:03');
INSERT INTO `tb_sku` VALUES (24, 3, 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/sku-img/e402727a2f5b42f99d8501f9dbdd9c28.png', NULL, '[{\"paramKey\": \"选择版本\", \"paramValue\": \"12GB+256GB\"}, {\"paramKey\": \"选择颜色\", \"paramValue\": \"冷杉绿\"}]', 6499.00, 1, 0, '2023-10-11 18:19:03', '2023-10-11 18:19:03');
INSERT INTO `tb_sku` VALUES (25, 3, 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/sku-img/e402727a2f5b42f99d8501f9dbdd9c28.png', NULL, '[{\"paramKey\": \"选择版本\", \"paramValue\": \"12GB+512GB\"}, {\"paramKey\": \"选择颜色\", \"paramValue\": \"冷杉绿\"}]', 6999.00, 1, 0, '2023-10-11 18:19:03', '2023-10-11 18:19:03');
INSERT INTO `tb_sku` VALUES (26, 4, 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/sku-img/098170bcadf54fb7ab809a46a4c7b725.png', NULL, '[{\"paramKey\": \"选择版本\", \"paramValue\": \"8GB+128GB\"}, {\"paramKey\": \"选择颜色\", \"paramValue\": \"白色\"}]', 4699.00, 1, 0, '2023-10-11 18:19:03', '2023-10-11 18:19:03');
INSERT INTO `tb_sku` VALUES (27, 4, 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/sku-img/098170bcadf54fb7ab809a46a4c7b725.png', NULL, '[{\"paramKey\": \"选择版本\", \"paramValue\": \"8GB+256GB\"}, {\"paramKey\": \"选择颜色\", \"paramValue\": \"白色\"}]', 4999.00, 1, 0, '2023-10-11 18:19:03', '2023-10-11 18:19:03');
INSERT INTO `tb_sku` VALUES (28, 4, 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/sku-img/098170bcadf54fb7ab809a46a4c7b725.png', NULL, '[{\"paramKey\": \"选择版本\", \"paramValue\": \"12GB+256GB\"}, {\"paramKey\": \"选择颜色\", \"paramValue\": \"白色\"}]', 5399.00, 1, 0, '2023-10-11 18:19:03', '2023-10-11 18:19:03');
INSERT INTO `tb_sku` VALUES (29, 4, 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/sku-img/098170bcadf54fb7ab809a46a4c7b725.png', NULL, '[{\"paramKey\": \"选择版本\", \"paramValue\": \"12GB+512GB\"}, {\"paramKey\": \"选择颜色\", \"paramValue\": \"白色\"}]', 5899.00, 1, 0, '2023-10-11 18:19:03', '2023-10-11 18:19:03');
INSERT INTO `tb_sku` VALUES (30, 4, 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/sku-img/372d1e9464914275a5721b5933e0e1f7.png', NULL, '[{\"paramKey\": \"选择版本\", \"paramValue\": \"8GB+128GB\"}, {\"paramKey\": \"选择颜色\", \"paramValue\": \"黑色\"}]', 4699.00, 1, 0, '2023-10-11 18:19:03', '2023-10-11 18:19:03');
INSERT INTO `tb_sku` VALUES (31, 4, 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/sku-img/372d1e9464914275a5721b5933e0e1f7.png', NULL, '[{\"paramKey\": \"选择版本\", \"paramValue\": \"8GB+256GB\"}, {\"paramKey\": \"选择颜色\", \"paramValue\": \"黑色\"}]', 4999.00, 1, 0, '2023-10-11 18:19:03', '2023-10-11 18:19:03');
INSERT INTO `tb_sku` VALUES (32, 4, 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/sku-img/372d1e9464914275a5721b5933e0e1f7.png', NULL, '[{\"paramKey\": \"选择版本\", \"paramValue\": \"12GB+256GB\"}, {\"paramKey\": \"选择颜色\", \"paramValue\": \"黑色\"}]', 5399.00, 1, 0, '2023-10-11 18:19:04', '2023-10-11 18:19:04');
INSERT INTO `tb_sku` VALUES (33, 4, 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/sku-img/372d1e9464914275a5721b5933e0e1f7.png', NULL, '[{\"paramKey\": \"选择版本\", \"paramValue\": \"12GB+512GB\"}, {\"paramKey\": \"选择颜色\", \"paramValue\": \"黑色\"}]', 5899.00, 1, 0, '2023-10-11 18:19:04', '2023-10-11 18:19:04');
INSERT INTO `tb_sku` VALUES (34, 4, 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/sku-img/59e727efcb4a48f3b337c020c7064481.png', NULL, '[{\"paramKey\": \"选择版本\", \"paramValue\": \"8GB+256GB\"}, {\"paramKey\": \"选择颜色\", \"paramValue\": \"原野绿\"}]', 4999.00, 1, 0, '2023-10-11 18:19:04', '2023-10-11 18:19:04');
INSERT INTO `tb_sku` VALUES (35, 4, 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/sku-img/59e727efcb4a48f3b337c020c7064481.png', NULL, '[{\"paramKey\": \"选择版本\", \"paramValue\": \"12GB+256GB\"}, {\"paramKey\": \"选择颜色\", \"paramValue\": \"原野绿\"}]', 5399.00, 1, 0, '2023-10-11 18:19:04', '2023-10-11 18:19:04');
INSERT INTO `tb_sku` VALUES (36, 4, 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/sku-img/df53533ad82f487fbf7ede357e1c9d17.png', NULL, '[{\"paramKey\": \"选择版本\", \"paramValue\": \"8GB+256GB\"}, {\"paramKey\": \"选择颜色\", \"paramValue\": \"紫色\"}]', 4999.00, 1, 0, '2023-10-11 18:19:04', '2023-10-11 18:19:04');
INSERT INTO `tb_sku` VALUES (37, 4, 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/sku-img/df53533ad82f487fbf7ede357e1c9d17.png', NULL, '[{\"paramKey\": \"选择版本\", \"paramValue\": \"12GB+256GB\"}, {\"paramKey\": \"选择颜色\", \"paramValue\": \"紫色\"}]', 5399.00, 1, 0, '2023-10-11 18:19:04', '2023-10-11 18:19:04');
INSERT INTO `tb_sku` VALUES (38, 4, 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/sku-img/0a3f06cd2e3a417287c7269e7edcbe4c.png', NULL, '[{\"paramKey\": \"选择版本\", \"paramValue\": \"8GB+128GB\"}, {\"paramKey\": \"选择颜色\", \"paramValue\": \"白色\"}]', 3999.00, 1, 0, '2023-10-11 18:19:04', '2023-10-11 18:19:04');
INSERT INTO `tb_sku` VALUES (39, 4, 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/sku-img/0a3f06cd2e3a417287c7269e7edcbe4c.png', NULL, '[{\"paramKey\": \"选择版本\", \"paramValue\": \"8GB+256GB\"}, {\"paramKey\": \"选择颜色\", \"paramValue\": \"白色\"}]', 4299.00, 1, 0, '2023-10-11 18:19:04', '2023-10-11 18:19:04');
INSERT INTO `tb_sku` VALUES (40, 4, 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/sku-img/0a3f06cd2e3a417287c7269e7edcbe4c.png', NULL, '[{\"paramKey\": \"选择版本\", \"paramValue\": \"12GB+256GB\"}, {\"paramKey\": \"选择颜色\", \"paramValue\": \"白色\"}]', 4699.00, 1, 0, '2023-10-11 18:19:04', '2023-10-11 18:19:04');
INSERT INTO `tb_sku` VALUES (41, 4, 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/sku-img/0a3f06cd2e3a417287c7269e7edcbe4c.png', NULL, '[{\"paramKey\": \"选择版本\", \"paramValue\": \"12GB+512GB\"}, {\"paramKey\": \"选择颜色\", \"paramValue\": \"白色\"}]', 5199.00, 1, 0, '2023-10-11 18:19:04', '2023-10-11 18:19:04');
INSERT INTO `tb_sku` VALUES (42, 5, 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/sku-img/48b233e45f504253933b71b7da6e2885.png', NULL, '[{\"paramKey\": \"选择版本\", \"paramValue\": \"8GB+128GB\"}, {\"paramKey\": \"选择颜色\", \"paramValue\": \"黑色\"}]', 3999.00, 1, 0, '2023-10-11 18:19:04', '2023-10-11 18:19:04');
INSERT INTO `tb_sku` VALUES (43, 5, 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/sku-img/48b233e45f504253933b71b7da6e2885.png', NULL, '[{\"paramKey\": \"选择版本\", \"paramValue\": \"8GB+256GB\"}, {\"paramKey\": \"选择颜色\", \"paramValue\": \"黑色\"}]', 4299.00, 1, 0, '2023-10-11 18:19:04', '2023-10-11 18:19:04');
INSERT INTO `tb_sku` VALUES (44, 5, 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/sku-img/48b233e45f504253933b71b7da6e2885.png', NULL, '[{\"paramKey\": \"选择版本\", \"paramValue\": \"12GB+256GB\"}, {\"paramKey\": \"选择颜色\", \"paramValue\": \"黑色\"}]', 4699.00, 1, 0, '2023-10-11 18:19:04', '2023-10-11 18:19:04');
INSERT INTO `tb_sku` VALUES (45, 5, 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/sku-img/48b233e45f504253933b71b7da6e2885.png', NULL, '[{\"paramKey\": \"选择版本\", \"paramValue\": \"12GB+512GB\"}, {\"paramKey\": \"选择颜色\", \"paramValue\": \"黑色\"}]', 5199.00, 1, 0, '2023-10-11 18:19:04', '2023-10-11 18:19:04');
INSERT INTO `tb_sku` VALUES (46, 5, 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/sku-img/82e4958e57dc450e91fb4af8f32ef9ac.png', NULL, '[{\"paramKey\": \"选择版本\", \"paramValue\": \"8GB+256GB\"}, {\"paramKey\": \"选择颜色\", \"paramValue\": \"原野绿\"}]', 4299.00, 1, 0, '2023-10-11 18:19:04', '2023-10-11 18:19:04');
INSERT INTO `tb_sku` VALUES (47, 5, 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/sku-img/82e4958e57dc450e91fb4af8f32ef9ac.png', NULL, '[{\"paramKey\": \"选择版本\", \"paramValue\": \"12GB+256GB\"}, {\"paramKey\": \"选择颜色\", \"paramValue\": \"原野绿\"}]', 4699.00, 1, 0, '2023-10-11 18:19:04', '2023-10-11 18:19:04');
INSERT INTO `tb_sku` VALUES (48, 5, 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/sku-img/02fd77350ccb4b49b66ca40af21bd157.png', NULL, '[{\"paramKey\": \"选择版本\", \"paramValue\": \"8GB+256GB\"}, {\"paramKey\": \"选择颜色\", \"paramValue\": \"紫色\"}]', 4299.00, 1, 0, '2023-10-11 18:19:04', '2023-10-11 18:19:04');
INSERT INTO `tb_sku` VALUES (49, 5, 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/sku-img/02fd77350ccb4b49b66ca40af21bd157.png', NULL, '[{\"paramKey\": \"选择版本\", \"paramValue\": \"12GB+256GB\"}, {\"paramKey\": \"选择颜色\", \"paramValue\": \"紫色\"}]', 4699.00, 1, 0, '2023-10-11 18:19:04', '2023-10-11 18:19:04');
INSERT INTO `tb_sku` VALUES (50, 5, 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/sku-img/4d2a5de6a0ca4d789586bab08f7e345f.png', NULL, '[{\"paramKey\": \"选择版本\", \"paramValue\": \"8GB+256GB\"}, {\"paramKey\": \"选择颜色\", \"paramValue\": \"蓝色\"}]', 4299.00, 1, 0, '2023-10-11 18:19:04', '2023-10-11 18:19:04');
INSERT INTO `tb_sku` VALUES (51, 6, 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/sku-img/05ba931798cc46e09ab46f4c3a9ee1cc.jpg', NULL, '[{\"paramKey\": \"选择颜色\", \"paramValue\": \"黑色\"}, {\"paramKey\": \"选择尺寸\", \"paramValue\": \"82英寸\"}]', 9999.00, 1, 0, '2023-10-11 18:19:04', '2023-10-11 18:19:04');
INSERT INTO `tb_sku` VALUES (52, 7, 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/sku-img/dbcdad0e6b6249ada903184f6158c1d5.jpg', NULL, '[{\"paramKey\": \"选择颜色\", \"paramValue\": \"黑色\"}, {\"paramKey\": \"选择尺寸\", \"paramValue\": \"98英寸\"}]', 15999.00, 1, 0, '2023-10-11 18:19:04', '2023-10-11 18:19:04');
INSERT INTO `tb_sku` VALUES (53, 8, 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/sku-img/19dcdf257c3b486b84297631e8d2877b.jpg', NULL, '[{\"paramKey\": \"选择颜色\", \"paramValue\": \"黑色\"}, {\"paramKey\": \"选择尺寸\", \"paramValue\": \"65英寸\"}]', 8999.00, 1, 0, '2023-10-11 18:19:04', '2023-10-11 18:19:04');
INSERT INTO `tb_sku` VALUES (54, 9, 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/sku-img/5d4b0f0448c443f4a35c68ff2cae0663.jpg', NULL, '[{\"paramKey\": \"选择配置\", \"paramValue\": \"i5-12450H/集显/16G/512G\"}]', 4499.00, 1, 0, '2023-10-11 18:19:04', '2023-10-11 18:19:04');
INSERT INTO `tb_sku` VALUES (55, 9, 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/sku-img/5d4b0f0448c443f4a35c68ff2cae0663.jpg', NULL, '[{\"paramKey\": \"选择配置\", \"paramValue\": \"i5-12450H/MX550/16G/512G\"}]', 5399.00, 1, 0, '2023-10-11 18:19:04', '2023-10-11 18:19:04');
INSERT INTO `tb_sku` VALUES (56, 9, 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/sku-img/5d4b0f0448c443f4a35c68ff2cae0663.jpg', NULL, '[{\"paramKey\": \"选择配置\", \"paramValue\": \"i7-12650H/MX550/16G/512G\"}]', 6299.00, 1, 0, '2023-10-11 18:19:04', '2023-10-11 18:19:04');
INSERT INTO `tb_sku` VALUES (57, 10, 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/sku-img/c06eab4d4a0e4199901a32c79ecd3dc5.jpg', NULL, '[{\"paramKey\": \"选择配置\", \"paramValue\": \"i5-12450H/集显/16G/512G\"}]', 5399.00, 1, 0, '2023-10-11 18:19:04', '2023-10-11 18:19:04');
INSERT INTO `tb_sku` VALUES (58, 10, 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/sku-img/c06eab4d4a0e4199901a32c79ecd3dc5.jpg', NULL, '[{\"paramKey\": \"选择配置\", \"paramValue\": \"i5-12450H/RTX 2050/16G/512G\"}]', 6599.00, 1, 0, '2023-10-11 18:19:04', '2023-10-11 18:19:04');
INSERT INTO `tb_sku` VALUES (59, 10, 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/sku-img/c06eab4d4a0e4199901a32c79ecd3dc5.jpg', NULL, '[{\"paramKey\": \"选择配置\", \"paramValue\": \"i7-12650H/RTX 2050/16G/512G\"}]', 7299.00, 1, 0, '2023-10-11 18:19:04', '2023-10-11 18:19:04');
INSERT INTO `tb_sku` VALUES (60, 11, 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/sku-img/5ad98768297a4d0394a20ad4a551d0e3.jpg', NULL, '[{\"paramKey\": \"选择配置\", \"paramValue\": \"i5-1240P/16GB/512GB/UMA/16\\\" 3840*2400 60Hz/Gray\"}]', 7399.00, 1, 0, '2023-10-11 18:19:04', '2023-10-11 18:19:04');
INSERT INTO `tb_sku` VALUES (61, 11, 'https://zh-product.oss-cn-shenzhen.aliyuncs.com/sku-img/5ad98768297a4d0394a20ad4a551d0e3.jpg', NULL, '[{\"paramKey\": \"选择配置\", \"paramValue\": \"i7-1260P/16GB/512GB/RTX 2050/16\\\" 3840*2400 60Hz/Gray\"}]', 9399.00, 1, 0, '2023-10-11 18:19:04', '2023-10-11 18:19:04');

-- ----------------------------
-- Table structure for tb_sku_stock
-- ----------------------------
DROP TABLE IF EXISTS `tb_sku_stock`;
CREATE TABLE `tb_sku_stock`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `sku_id` int UNSIGNED NOT NULL COMMENT '商品规格ID',
  `actual_stock` int UNSIGNED NULL DEFAULT 0 COMMENT '实际库存',
  `lock_stock` int UNSIGNED NULL DEFAULT 0 COMMENT '锁定库存',
  `stock` int UNSIGNED NULL DEFAULT 0 COMMENT '可售卖库存',
  `delete_status` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除,0:否、1:是',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_sku_id`(`sku_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 62 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品规格库存表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_sku_stock
-- ----------------------------
INSERT INTO `tb_sku_stock` VALUES (1, 1, 9999, 0, 9999, 0, '2023-10-11 18:20:01', '2023-11-19 12:41:48');
INSERT INTO `tb_sku_stock` VALUES (2, 2, 9999, 0, 9999, 0, '2023-10-11 18:20:01', '2023-11-19 12:41:48');
INSERT INTO `tb_sku_stock` VALUES (3, 3, 9999, 0, 9999, 0, '2023-10-11 18:20:01', '2023-11-19 12:41:48');
INSERT INTO `tb_sku_stock` VALUES (4, 4, 9999, 0, 9999, 0, '2023-10-11 18:20:01', '2023-11-11 13:07:11');
INSERT INTO `tb_sku_stock` VALUES (5, 5, 9999, 0, 9999, 0, '2023-10-11 18:20:01', '2023-11-11 13:07:14');
INSERT INTO `tb_sku_stock` VALUES (6, 6, 9999, 0, 9999, 0, '2023-10-11 18:20:01', '2023-10-16 18:34:57');
INSERT INTO `tb_sku_stock` VALUES (7, 7, 9999, 0, 9999, 0, '2023-10-11 18:20:01', '2023-10-16 18:34:57');
INSERT INTO `tb_sku_stock` VALUES (8, 8, 9999, 0, 9999, 0, '2023-10-11 18:20:01', '2023-10-16 18:34:57');
INSERT INTO `tb_sku_stock` VALUES (9, 9, 9999, 0, 9999, 0, '2023-10-11 18:20:01', '2023-10-16 18:34:57');
INSERT INTO `tb_sku_stock` VALUES (10, 10, 9999, 0, 9999, 0, '2023-10-11 18:20:01', '2023-10-16 18:34:57');
INSERT INTO `tb_sku_stock` VALUES (11, 11, 9999, 0, 9999, 0, '2023-10-11 18:20:01', '2023-10-16 18:34:57');
INSERT INTO `tb_sku_stock` VALUES (12, 12, 9999, 0, 9999, 0, '2023-10-11 18:20:01', '2023-10-16 18:34:57');
INSERT INTO `tb_sku_stock` VALUES (13, 13, 9999, 0, 9999, 0, '2023-10-11 18:20:01', '2023-10-16 18:34:57');
INSERT INTO `tb_sku_stock` VALUES (14, 14, 9999, 0, 9999, 0, '2023-10-11 18:20:01', '2023-10-16 18:34:57');
INSERT INTO `tb_sku_stock` VALUES (15, 15, 9999, 0, 9999, 0, '2023-10-11 18:20:02', '2023-10-16 18:34:57');
INSERT INTO `tb_sku_stock` VALUES (16, 16, 9999, 0, 9999, 0, '2023-10-11 18:20:02', '2023-10-16 18:34:57');
INSERT INTO `tb_sku_stock` VALUES (17, 17, 9999, 0, 9999, 0, '2023-10-11 18:20:02', '2023-10-16 18:34:57');
INSERT INTO `tb_sku_stock` VALUES (18, 18, 9999, 0, 9999, 0, '2023-10-11 18:20:02', '2023-10-16 18:34:57');
INSERT INTO `tb_sku_stock` VALUES (19, 19, 9999, 0, 9999, 0, '2023-10-11 18:20:02', '2023-10-16 18:34:57');
INSERT INTO `tb_sku_stock` VALUES (20, 20, 9999, 0, 9999, 0, '2023-10-11 18:20:02', '2023-10-16 18:34:57');
INSERT INTO `tb_sku_stock` VALUES (21, 21, 9999, 0, 9999, 0, '2023-10-11 18:20:02', '2023-10-16 18:34:57');
INSERT INTO `tb_sku_stock` VALUES (22, 22, 9999, 0, 9999, 0, '2023-10-11 18:20:02', '2023-10-16 18:34:57');
INSERT INTO `tb_sku_stock` VALUES (23, 23, 9999, 0, 9999, 0, '2023-10-11 18:20:02', '2023-10-16 18:34:57');
INSERT INTO `tb_sku_stock` VALUES (24, 24, 9999, 0, 9999, 0, '2023-10-11 18:20:02', '2023-10-16 18:34:57');
INSERT INTO `tb_sku_stock` VALUES (25, 25, 9999, 0, 9999, 0, '2023-10-11 18:20:02', '2023-10-16 18:34:57');
INSERT INTO `tb_sku_stock` VALUES (26, 26, 9999, 0, 9999, 0, '2023-10-11 18:20:02', '2023-10-16 18:34:57');
INSERT INTO `tb_sku_stock` VALUES (27, 27, 9999, 0, 9999, 0, '2023-10-11 18:20:02', '2023-10-16 18:34:57');
INSERT INTO `tb_sku_stock` VALUES (28, 28, 9999, 0, 9999, 0, '2023-10-11 18:20:02', '2023-10-16 18:34:57');
INSERT INTO `tb_sku_stock` VALUES (29, 29, 9999, 0, 9999, 0, '2023-10-11 18:20:02', '2023-10-16 18:34:57');
INSERT INTO `tb_sku_stock` VALUES (30, 30, 9999, 0, 9999, 0, '2023-10-11 18:20:02', '2023-10-16 18:34:57');
INSERT INTO `tb_sku_stock` VALUES (31, 31, 9999, 0, 9999, 0, '2023-10-11 18:20:02', '2023-10-16 18:34:57');
INSERT INTO `tb_sku_stock` VALUES (32, 32, 9999, 0, 9999, 0, '2023-10-11 18:20:02', '2023-10-16 18:34:57');
INSERT INTO `tb_sku_stock` VALUES (33, 33, 9999, 0, 9999, 0, '2023-10-11 18:20:02', '2023-10-16 18:34:57');
INSERT INTO `tb_sku_stock` VALUES (34, 34, 9999, 0, 9999, 0, '2023-10-11 18:20:02', '2023-10-16 18:34:57');
INSERT INTO `tb_sku_stock` VALUES (35, 35, 9999, 0, 9999, 0, '2023-10-11 18:20:02', '2023-10-16 18:34:57');
INSERT INTO `tb_sku_stock` VALUES (36, 36, 9999, 0, 9999, 0, '2023-10-11 18:20:02', '2023-10-16 18:34:57');
INSERT INTO `tb_sku_stock` VALUES (37, 37, 9999, 0, 9999, 0, '2023-10-11 18:20:02', '2023-10-16 18:34:57');
INSERT INTO `tb_sku_stock` VALUES (38, 38, 9999, 0, 9999, 0, '2023-10-11 18:20:02', '2023-10-16 18:34:57');
INSERT INTO `tb_sku_stock` VALUES (39, 39, 9999, 0, 9999, 0, '2023-10-11 18:20:02', '2023-10-16 18:34:57');
INSERT INTO `tb_sku_stock` VALUES (40, 40, 9999, 0, 9999, 0, '2023-10-11 18:20:02', '2023-10-16 18:34:57');
INSERT INTO `tb_sku_stock` VALUES (41, 41, 9999, 0, 9999, 0, '2023-10-11 18:20:02', '2023-10-16 18:34:57');
INSERT INTO `tb_sku_stock` VALUES (42, 42, 9999, 0, 9999, 0, '2023-10-11 18:20:02', '2023-10-16 18:34:57');
INSERT INTO `tb_sku_stock` VALUES (43, 43, 9999, 0, 9999, 0, '2023-10-11 18:20:02', '2023-10-16 18:34:57');
INSERT INTO `tb_sku_stock` VALUES (44, 44, 9999, 0, 9999, 0, '2023-10-11 18:20:02', '2023-10-16 18:34:57');
INSERT INTO `tb_sku_stock` VALUES (45, 45, 9999, 0, 9999, 0, '2023-10-11 18:20:02', '2023-10-16 18:34:57');
INSERT INTO `tb_sku_stock` VALUES (46, 46, 9999, 0, 9999, 0, '2023-10-11 18:20:02', '2023-10-16 18:34:57');
INSERT INTO `tb_sku_stock` VALUES (47, 47, 9999, 0, 9999, 0, '2023-10-11 18:20:02', '2023-10-16 18:34:57');
INSERT INTO `tb_sku_stock` VALUES (48, 48, 9999, 0, 9999, 0, '2023-10-11 18:20:02', '2023-10-16 18:34:57');
INSERT INTO `tb_sku_stock` VALUES (49, 49, 9999, 0, 9999, 0, '2023-10-11 18:20:02', '2023-10-16 18:34:57');
INSERT INTO `tb_sku_stock` VALUES (50, 50, 9999, 0, 9999, 0, '2023-10-11 18:20:02', '2023-10-16 18:34:57');
INSERT INTO `tb_sku_stock` VALUES (51, 51, 9999, 0, 9999, 0, '2023-10-11 18:20:02', '2023-10-16 18:34:57');
INSERT INTO `tb_sku_stock` VALUES (52, 52, 9999, 0, 9999, 0, '2023-10-11 18:20:02', '2023-10-16 18:34:57');
INSERT INTO `tb_sku_stock` VALUES (53, 53, 9999, 0, 9999, 0, '2023-10-11 18:20:02', '2023-10-16 18:34:57');
INSERT INTO `tb_sku_stock` VALUES (54, 54, 9999, 0, 9999, 0, '2023-10-11 18:20:02', '2023-10-16 18:34:57');
INSERT INTO `tb_sku_stock` VALUES (55, 55, 9999, 0, 9999, 0, '2023-10-11 18:20:02', '2023-10-16 18:34:57');
INSERT INTO `tb_sku_stock` VALUES (56, 56, 9999, 0, 9999, 0, '2023-10-11 18:20:02', '2023-10-16 18:34:57');
INSERT INTO `tb_sku_stock` VALUES (57, 57, 9999, 0, 9999, 0, '2023-10-11 18:20:02', '2023-10-16 18:34:57');
INSERT INTO `tb_sku_stock` VALUES (58, 58, 9999, 0, 9999, 0, '2023-10-11 18:20:02', '2023-10-16 18:34:57');
INSERT INTO `tb_sku_stock` VALUES (59, 59, 9999, 0, 9999, 0, '2023-10-11 18:20:02', '2023-10-16 18:34:57');
INSERT INTO `tb_sku_stock` VALUES (60, 60, 9999, 0, 9999, 0, '2023-10-11 18:20:03', '2023-10-16 18:34:57');
INSERT INTO `tb_sku_stock` VALUES (61, 61, 9999, 0, 9999, 0, '2023-10-11 18:20:03', '2023-10-16 18:34:57');

-- ----------------------------
-- Table structure for tb_sku_stock_lock
-- ----------------------------
DROP TABLE IF EXISTS `tb_sku_stock_lock`;
CREATE TABLE `tb_sku_stock_lock`  (
  `order_no` bigint UNSIGNED NOT NULL COMMENT '订单号',
  `sku_id` int UNSIGNED NOT NULL COMMENT '商品规格ID',
  `product_id` int UNSIGNED NULL DEFAULT NULL COMMENT '商品ID',
  `count` int UNSIGNED NOT NULL COMMENT '库存锁定数量',
  `status` tinyint NULL DEFAULT 0 COMMENT '锁定状态，-1已解锁、0未锁定、1已锁定',
  `consume_version` int UNSIGNED NULL DEFAULT NULL COMMENT '消费版本号(乐观锁字段)',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`order_no`, `sku_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品规格锁定库存表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_sku_stock_lock
-- ----------------------------

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `uid` bigint NULL DEFAULT NULL COMMENT '用户UID',
  `nick_name` varchar(24) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `photo` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户头像',
  `gender` tinyint UNSIGNED NULL DEFAULT NULL COMMENT '用户性别,0:女、1:男、3:保密',
  `experience` int UNSIGNED NULL DEFAULT NULL COMMENT '会员经验值',
  `level_id` int UNSIGNED NULL DEFAULT NULL COMMENT '会员等级ID',
  `status` tinyint UNSIGNED NOT NULL DEFAULT 1 COMMENT '是否有效,0:否、1:是',
  `delete_status` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除,0:否、1:是',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_user
-- ----------------------------

-- ----------------------------
-- Table structure for tb_user_auths
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_auths`;
CREATE TABLE `tb_user_auths`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int UNSIGNED NOT NULL COMMENT '用户ID',
  `identity_type` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '登录类型',
  `identifier` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '登录标识',
  `credential` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '登录凭证',
  `status` tinyint UNSIGNED NOT NULL DEFAULT 1 COMMENT '是否有效,0:否、1:是',
  `delete_status` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除,0:否、1:是',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unq_user_id_create_time`(`user_id` ASC, `create_time` DESC) USING BTREE,
  UNIQUE INDEX `unq_identifier_create_time`(`identifier` ASC, `create_time` DESC) USING BTREE,
  UNIQUE INDEX `unq_credential_create_time`(`credential` ASC, `create_time` DESC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户登录类型表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_user_auths
-- ----------------------------

-- ----------------------------
-- Table structure for tb_user_coupon
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_coupon`;
CREATE TABLE `tb_user_coupon`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `coupon_id` int UNSIGNED NOT NULL COMMENT '优惠券ID',
  `user_id` int UNSIGNED NOT NULL COMMENT '用户ID',
  `quantity` int UNSIGNED NULL DEFAULT NULL COMMENT '优惠券数量',
  `status` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '优惠券状态,0:未使用、1:已使用',
  `delete_status` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除,0:否、1:是',
  `consume_version` int UNSIGNED NULL DEFAULT NULL COMMENT '消费版本号(乐观锁字段)',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unq_coupon_id_user_id_create_time`(`coupon_id` ASC, `user_id` ASC, `create_time` DESC) USING BTREE,
  INDEX `idx_user_id_create_time`(`user_id` ASC, `create_time` DESC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户优惠券表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_user_coupon
-- ----------------------------

-- ----------------------------
-- Table structure for tb_user_level
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_level`;
CREATE TABLE `tb_user_level`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `level` tinyint UNSIGNED NULL DEFAULT NULL COMMENT '会员等级',
  `title` varchar(24) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '会员等级名称',
  `experience` int UNSIGNED NULL DEFAULT NULL COMMENT '会员经验值',
  `discount` decimal(10, 2) UNSIGNED NULL DEFAULT NULL COMMENT '折扣',
  `delete_status` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除,0:否、1:是',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户会员等级表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_user_level
-- ----------------------------

-- ----------------------------
-- Table structure for tb_user_shipping_address
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_shipping_address`;
CREATE TABLE `tb_user_shipping_address`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int UNSIGNED NOT NULL COMMENT '用户ID',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '收货人姓名',
  `tel_no` varchar(24) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '收件人电话',
  `address` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '收件人地址',
  `detail_address` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '收件人详细地址',
  `default_status` tinyint UNSIGNED NULL DEFAULT 0 COMMENT '是否为默认地址,0:否、1:是',
  `delete_status` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除,0:否、1:是',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id_create_time`(`user_id` ASC, `create_time` DESC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户收货地址表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_user_shipping_address
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
