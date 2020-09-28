/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : djmall

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2020-08-30 17:02:56
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for djmall_dict_product_attr
-- ----------------------------
DROP TABLE IF EXISTS `djmall_dict_product_attr`;
CREATE TABLE `djmall_dict_product_attr` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品属性ID',
  `attr_name` varchar(255) DEFAULT NULL COMMENT '商品属性名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='商品属性表';

-- ----------------------------
-- Records of djmall_dict_product_attr
-- ----------------------------
INSERT INTO `djmall_dict_product_attr` VALUES ('1', '颜色');
INSERT INTO `djmall_dict_product_attr` VALUES ('2', '尺码');
INSERT INTO `djmall_dict_product_attr` VALUES ('3', '鞋码');
INSERT INTO `djmall_dict_product_attr` VALUES ('4', '材料');
