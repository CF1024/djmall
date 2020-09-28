/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : djmall

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2020-08-30 17:04:20
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for djmall_product_sku
-- ----------------------------
DROP TABLE IF EXISTS `djmall_product_sku`;
CREATE TABLE `djmall_product_sku` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'skuID',
  `product_id` int(11) DEFAULT NULL COMMENT '商品ID',
  `sku_price` decimal(10,2) DEFAULT NULL COMMENT 'SKU价格',
  `sku_count` int(11) DEFAULT NULL COMMENT 'sku库存',
  `sku_rate` int(255) DEFAULT NULL COMMENT 'sku折扣 ',
  `sku_status` varchar(255) DEFAULT NULL COMMENT 'sku状态 下架 上架',
  `sku_attr_ids` varchar(255) DEFAULT NULL COMMENT 'sku属性ID集合 -1 代表自定义',
  `sku_attr_names` varchar(255) DEFAULT NULL COMMENT 'sku属性名集合',
  `sku_attr_value_ids` varchar(255) DEFAULT NULL COMMENT 'sku属性值ID集合 -1 代表自定义',
  `sku_attr_value_names` varchar(255) DEFAULT NULL COMMENT 'sku属性值名集合',
  `is_default` varchar(255) DEFAULT NULL COMMENT '是否默认',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=148 DEFAULT CHARSET=utf8 COMMENT='商品sku表';

-- ----------------------------
-- Records of djmall_product_sku
-- ----------------------------
INSERT INTO `djmall_product_sku` VALUES ('104', '47', '5499.00', '100', '90', 'PRODUCT_STATUS_UP', '6,9,4,5,-1,-1', ' 黑色, 粉色, 紫色, 白色, 64G, 128G', '6--1', ' 黑色- 64G', 'HAVE_DEFAULT');
INSERT INTO `djmall_product_sku` VALUES ('105', '47', '5899.00', '100', '100', 'PRODUCT_STATUS_UP', '6,9,4,5,-1,-1', ' 黑色, 粉色, 紫色, 白色, 64G, 128G', '6--1', ' 黑色- 128G', 'NOT_DEFAULT');
INSERT INTO `djmall_product_sku` VALUES ('106', '47', '5499.00', '100', '100', 'PRODUCT_STATUS_UP', '6,9,4,5,-1,-1', ' 黑色, 粉色, 紫色, 白色, 64G, 128G', '9--1', ' 粉色- 64G', 'NOT_DEFAULT');
INSERT INTO `djmall_product_sku` VALUES ('107', '47', '5899.00', '100', '100', 'PRODUCT_STATUS_UP', '6,9,4,5,-1,-1', ' 黑色, 粉色, 紫色, 白色, 64G, 128G', '9--1', ' 粉色- 128G', 'NOT_DEFAULT');
INSERT INTO `djmall_product_sku` VALUES ('108', '47', '5499.00', '100', '100', 'PRODUCT_STATUS_UP', '6,9,4,5,-1,-1', ' 黑色, 粉色, 紫色, 白色, 64G, 128G', '4--1', ' 紫色- 64G', 'NOT_DEFAULT');
INSERT INTO `djmall_product_sku` VALUES ('109', '47', '5899.00', '100', '100', 'PRODUCT_STATUS_UP', '6,9,4,5,-1,-1', ' 黑色, 粉色, 紫色, 白色, 64G, 128G', '4--1', ' 紫色- 128G', 'NOT_DEFAULT');
INSERT INTO `djmall_product_sku` VALUES ('110', '47', '5499.00', '100', '100', 'PRODUCT_STATUS_UP', '6,9,4,5,-1,-1', ' 黑色, 粉色, 紫色, 白色, 64G, 128G', '5--1', ' 白色- 64G', 'NOT_DEFAULT');
INSERT INTO `djmall_product_sku` VALUES ('111', '47', '5899.00', '100', '100', 'PRODUCT_STATUS_UP', '6,9,4,5,-1,-1', ' 黑色, 粉色, 紫色, 白色, 64G, 128G', '5--1', ' 白色- 128G', 'NOT_DEFAULT');
INSERT INTO `djmall_product_sku` VALUES ('113', '49', '100.00', '100', '100', 'PRODUCT_STATUS_UP', '5,6,13,14,12', ' 白色, 黑色, L码, XL码, M码', '5-13', ' 白色- L码', 'HAVE_DEFAULT');
INSERT INTO `djmall_product_sku` VALUES ('114', '49', '100.00', '100', '100', 'PRODUCT_STATUS_UP', '5,6,13,14,12', ' 白色, 黑色, L码, XL码, M码', '5-14', ' 白色- XL码', 'NOT_DEFAULT');
INSERT INTO `djmall_product_sku` VALUES ('115', '49', '100.00', '100', '100', 'PRODUCT_STATUS_UP', '5,6,13,14,12', ' 白色, 黑色, L码, XL码, M码', '5-12', ' 白色- M码', 'NOT_DEFAULT');
INSERT INTO `djmall_product_sku` VALUES ('116', '49', '100.00', '100', '100', 'PRODUCT_STATUS_UP', '5,6,13,14,12', ' 白色, 黑色, L码, XL码, M码', '6-13', ' 黑色- L码', 'NOT_DEFAULT');
INSERT INTO `djmall_product_sku` VALUES ('117', '49', '100.00', '100', '100', 'PRODUCT_STATUS_UP', '5,6,13,14,12', ' 白色, 黑色, L码, XL码, M码', '6-14', ' 黑色- XL码', 'NOT_DEFAULT');
INSERT INTO `djmall_product_sku` VALUES ('118', '49', '100.00', '100', '100', 'PRODUCT_STATUS_UP', '5,6,13,14,12', ' 白色, 黑色, L码, XL码, M码', '6-12', ' 黑色- M码', 'NOT_DEFAULT');
INSERT INTO `djmall_product_sku` VALUES ('119', '50', '4499.00', '100', '100', 'PRODUCT_STATUS_UP', '-1,-1,-1,-1,-1,-1,-1,-1', ' 零度白, 亮黑色, 深海蓝, 晨曦金, 6GB, 8GB, 128GB, 256GB', '-1--1--1', ' 零度白- 6GB- 128GB', 'NOT_DEFAULT');
INSERT INTO `djmall_product_sku` VALUES ('120', '50', '4899.00', '100', '100', 'PRODUCT_STATUS_UP', '-1,-1,-1,-1,-1,-1,-1,-1', ' 零度白, 亮黑色, 深海蓝, 晨曦金, 6GB, 8GB, 128GB, 256GB', '-1--1--1', ' 零度白- 8GB- 128GB', 'HAVE_DEFAULT');
INSERT INTO `djmall_product_sku` VALUES ('121', '50', '5299.00', '100', '100', 'PRODUCT_STATUS_UP', '-1,-1,-1,-1,-1,-1,-1,-1', ' 零度白, 亮黑色, 深海蓝, 晨曦金, 6GB, 8GB, 128GB, 256GB', '-1--1--1', ' 零度白- 8GB- 256GB', 'NOT_DEFAULT');
INSERT INTO `djmall_product_sku` VALUES ('122', '50', '4499.00', '100', '100', 'PRODUCT_STATUS_UP', '-1,-1,-1,-1,-1,-1,-1,-1', ' 零度白, 亮黑色, 深海蓝, 晨曦金, 6GB, 8GB, 128GB, 256GB', '-1--1--1', ' 亮黑色- 6GB- 128GB', 'NOT_DEFAULT');
INSERT INTO `djmall_product_sku` VALUES ('123', '50', '4899.00', '100', '100', 'PRODUCT_STATUS_UP', '-1,-1,-1,-1,-1,-1,-1,-1', ' 零度白, 亮黑色, 深海蓝, 晨曦金, 6GB, 8GB, 128GB, 256GB', '-1--1--1', ' 亮黑色- 8GB- 128GB', 'NOT_DEFAULT');
INSERT INTO `djmall_product_sku` VALUES ('124', '50', '5299.00', '100', '100', 'PRODUCT_STATUS_UP', '-1,-1,-1,-1,-1,-1,-1,-1', ' 零度白, 亮黑色, 深海蓝, 晨曦金, 6GB, 8GB, 128GB, 256GB', '-1--1--1', ' 亮黑色- 8GB- 256GB', 'NOT_DEFAULT');
INSERT INTO `djmall_product_sku` VALUES ('125', '50', '4499.00', '100', '100', 'PRODUCT_STATUS_UP', '-1,-1,-1,-1,-1,-1,-1,-1', ' 零度白, 亮黑色, 深海蓝, 晨曦金, 6GB, 8GB, 128GB, 256GB', '-1--1--1', ' 深海蓝- 6GB- 128GB', 'NOT_DEFAULT');
INSERT INTO `djmall_product_sku` VALUES ('126', '50', '4899.00', '100', '100', 'PRODUCT_STATUS_UP', '-1,-1,-1,-1,-1,-1,-1,-1', ' 零度白, 亮黑色, 深海蓝, 晨曦金, 6GB, 8GB, 128GB, 256GB', '-1--1--1', ' 深海蓝- 8GB- 128GB', 'NOT_DEFAULT');
INSERT INTO `djmall_product_sku` VALUES ('127', '50', '5299.00', '100', '100', 'PRODUCT_STATUS_UP', '-1,-1,-1,-1,-1,-1,-1,-1', ' 零度白, 亮黑色, 深海蓝, 晨曦金, 6GB, 8GB, 128GB, 256GB', '-1--1--1', ' 深海蓝- 8GB- 256GB', 'NOT_DEFAULT');
INSERT INTO `djmall_product_sku` VALUES ('128', '50', '4499.00', '100', '100', 'PRODUCT_STATUS_UP', '-1,-1,-1,-1,-1,-1,-1,-1', ' 零度白, 亮黑色, 深海蓝, 晨曦金, 6GB, 8GB, 128GB, 256GB', '-1--1--1', ' 晨曦金- 6GB- 128GB', 'NOT_DEFAULT');
INSERT INTO `djmall_product_sku` VALUES ('129', '50', '4899.00', '100', '100', 'PRODUCT_STATUS_UP', '-1,-1,-1,-1,-1,-1,-1,-1', ' 零度白, 亮黑色, 深海蓝, 晨曦金, 6GB, 8GB, 128GB, 256GB', '-1--1--1', ' 晨曦金- 8GB- 128GB', 'NOT_DEFAULT');
INSERT INTO `djmall_product_sku` VALUES ('130', '50', '5299.00', '100', '100', 'PRODUCT_STATUS_UP', '-1,-1,-1,-1,-1,-1,-1,-1', ' 零度白, 亮黑色, 深海蓝, 晨曦金, 6GB, 8GB, 128GB, 256GB', '-1--1--1', ' 晨曦金- 8GB- 256GB', 'NOT_DEFAULT');
INSERT INTO `djmall_product_sku` VALUES ('131', '51', '5999.00', '100', '100', 'PRODUCT_STATUS_UP', '4,5,6,-1,-1', ' 紫色, 白色, 黑色, 64G, 128G', '4--1', ' 紫色- 128G', 'HAVE_DEFAULT');
INSERT INTO `djmall_product_sku` VALUES ('132', '51', '5499.00', '100', '100', 'PRODUCT_STATUS_UP', '4,5,6,-1,-1', ' 紫色, 白色, 黑色, 64G, 128G', '5--1', ' 白色- 64G', 'NOT_DEFAULT');
INSERT INTO `djmall_product_sku` VALUES ('133', '51', '5999.00', '100', '100', 'PRODUCT_STATUS_UP', '4,5,6,-1,-1', ' 紫色, 白色, 黑色, 64G, 128G', '5--1', ' 白色- 128G', 'NOT_DEFAULT');
INSERT INTO `djmall_product_sku` VALUES ('134', '51', '5499.00', '100', '90', 'PRODUCT_STATUS_UP', '4,5,6,-1,-1', ' 紫色, 白色, 黑色, 64G, 128G', '6--1', ' 黑色- 64G', 'NOT_DEFAULT');
INSERT INTO `djmall_product_sku` VALUES ('135', '51', '5999.00', '100', '100', 'PRODUCT_STATUS_UP', '4,5,6,-1,-1', ' 紫色, 白色, 黑色, 64G, 128G', '6--1', ' 黑色- 128G', 'NOT_DEFAULT');
INSERT INTO `djmall_product_sku` VALUES ('136', '52', '5499.00', '100', '100', 'PRODUCT_STATUS_UP', '-', '-', '-', '黑色-512GB', 'HAVE_DEFAULT');
INSERT INTO `djmall_product_sku` VALUES ('137', '53', '99.00', '80', '95', 'PRODUCT_STATUS_UP', '-', '-', '-', '黑色-棉', 'HAVE_DEFAULT');
INSERT INTO `djmall_product_sku` VALUES ('138', '54', '3000000.00', '100', '100', 'PRODUCT_STATUS_UP', '5,6,9', ' 白色, 黑色, 粉色', '5', ' 白色', 'HAVE_DEFAULT');
INSERT INTO `djmall_product_sku` VALUES ('139', '54', '3000000.00', '100', '100', 'PRODUCT_STATUS_UP', '5,6,9', ' 白色, 黑色, 粉色', '6', ' 黑色', 'NOT_DEFAULT');
INSERT INTO `djmall_product_sku` VALUES ('140', '54', '3000000.00', '100', '100', 'PRODUCT_STATUS_UP', '5,6,9', ' 白色, 黑色, 粉色', '9', ' 粉色', 'NOT_DEFAULT');
INSERT INTO `djmall_product_sku` VALUES ('141', '55', '4500.00', '0', '100', 'PRODUCT_STATUS_UP', '19,20,21,16,17,18,-1', ' 42码, 40.5码, 43码, 39码, 40码, 41码, 白棕色', '19--1', ' 42码- 白棕色', 'HAVE_DEFAULT');
INSERT INTO `djmall_product_sku` VALUES ('142', '55', '5800.00', '7', '100', 'PRODUCT_STATUS_UP', '19,20,21,16,17,18,-1', ' 42码, 40.5码, 43码, 39码, 40码, 41码, 白棕色', '20--1', ' 40.5码- 白棕色', 'NOT_DEFAULT');
INSERT INTO `djmall_product_sku` VALUES ('143', '55', '4300.00', '8', '100', 'PRODUCT_STATUS_UP', '19,20,21,16,17,18,-1', ' 42码, 40.5码, 43码, 39码, 40码, 41码, 白棕色', '21--1', ' 43码- 白棕色', 'NOT_DEFAULT');
INSERT INTO `djmall_product_sku` VALUES ('144', '55', '5299.00', '10', '100', 'PRODUCT_STATUS_UP', '19,20,21,16,17,18,-1', ' 42码, 40.5码, 43码, 39码, 40码, 41码, 白棕色', '16--1', ' 39码- 白棕色', 'NOT_DEFAULT');
INSERT INTO `djmall_product_sku` VALUES ('145', '55', '5499.00', '10', '100', 'PRODUCT_STATUS_UP', '19,20,21,16,17,18,-1', ' 42码, 40.5码, 43码, 39码, 40码, 41码, 白棕色', '17--1', ' 40码- 白棕色', 'NOT_DEFAULT');
INSERT INTO `djmall_product_sku` VALUES ('146', '55', '5399.00', '10', '100', 'PRODUCT_STATUS_UP', '19,20,21,16,17,18,-1', ' 42码, 40.5码, 43码, 39码, 40码, 41码, 白棕色', '18--1', ' 41码- 白棕色', 'NOT_DEFAULT');
INSERT INTO `djmall_product_sku` VALUES ('147', '56', '100.00', '10', '100', 'PRODUCT_STATUS_UP', '1,2', ' 红色, 黄色', '2', ' 黄色', 'HAVE_DEFAULT');
