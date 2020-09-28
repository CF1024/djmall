/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : djmall

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2020-08-30 17:03:19
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for djmall_dict_product_sku_gm
-- ----------------------------
DROP TABLE IF EXISTS `djmall_dict_product_sku_gm`;
CREATE TABLE `djmall_dict_product_sku_gm` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '通用SKU关系ID',
  `product_type` varchar(255) DEFAULT NULL COMMENT '商品类型',
  `attr_id` int(11) DEFAULT NULL COMMENT '属性ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COMMENT='通用SKU关系表';

-- ----------------------------
-- Records of djmall_dict_product_sku_gm
-- ----------------------------
INSERT INTO `djmall_dict_product_sku_gm` VALUES ('14', '衣服', '1');
INSERT INTO `djmall_dict_product_sku_gm` VALUES ('15', '衣服', '2');
INSERT INTO `djmall_dict_product_sku_gm` VALUES ('19', '鞋子', '1');
INSERT INTO `djmall_dict_product_sku_gm` VALUES ('20', '鞋子', '3');
