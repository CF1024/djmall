/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : djmall

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2020-08-30 17:03:03
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for djmall_dict_product_attr_value
-- ----------------------------
DROP TABLE IF EXISTS `djmall_dict_product_attr_value`;
CREATE TABLE `djmall_dict_product_attr_value` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品属性值ID',
  `attr_id` int(11) DEFAULT NULL COMMENT '商品属性ID',
  `attr_value` varchar(255) DEFAULT NULL COMMENT '属性值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 COMMENT='商品属性值表';

-- ----------------------------
-- Records of djmall_dict_product_attr_value
-- ----------------------------
INSERT INTO `djmall_dict_product_attr_value` VALUES ('1', '1', '红色');
INSERT INTO `djmall_dict_product_attr_value` VALUES ('2', '1', '黄色');
INSERT INTO `djmall_dict_product_attr_value` VALUES ('3', '1', '蓝色');
INSERT INTO `djmall_dict_product_attr_value` VALUES ('4', '1', '紫色');
INSERT INTO `djmall_dict_product_attr_value` VALUES ('5', '1', '白色');
INSERT INTO `djmall_dict_product_attr_value` VALUES ('6', '1', '黑色');
INSERT INTO `djmall_dict_product_attr_value` VALUES ('7', '1', '绿色');
INSERT INTO `djmall_dict_product_attr_value` VALUES ('8', '1', '橙色');
INSERT INTO `djmall_dict_product_attr_value` VALUES ('9', '1', '粉色');
INSERT INTO `djmall_dict_product_attr_value` VALUES ('10', '2', 'XS码');
INSERT INTO `djmall_dict_product_attr_value` VALUES ('11', '2', 'S码');
INSERT INTO `djmall_dict_product_attr_value` VALUES ('12', '2', 'M码');
INSERT INTO `djmall_dict_product_attr_value` VALUES ('13', '2', 'L码');
INSERT INTO `djmall_dict_product_attr_value` VALUES ('14', '2', 'XL码');
INSERT INTO `djmall_dict_product_attr_value` VALUES ('15', '2', 'XXL码');
INSERT INTO `djmall_dict_product_attr_value` VALUES ('16', '3', '39码');
INSERT INTO `djmall_dict_product_attr_value` VALUES ('17', '3', '40码');
INSERT INTO `djmall_dict_product_attr_value` VALUES ('18', '3', '41码');
INSERT INTO `djmall_dict_product_attr_value` VALUES ('19', '3', '42码');
INSERT INTO `djmall_dict_product_attr_value` VALUES ('20', '3', '40.5码');
INSERT INTO `djmall_dict_product_attr_value` VALUES ('21', '3', '43码');
INSERT INTO `djmall_dict_product_attr_value` VALUES ('22', '4', '钢');
