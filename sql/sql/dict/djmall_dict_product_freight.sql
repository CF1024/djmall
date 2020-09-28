/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : djmall

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2020-08-30 17:03:13
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for djmall_dict_product_freight
-- ----------------------------
DROP TABLE IF EXISTS `djmall_dict_product_freight`;
CREATE TABLE `djmall_dict_product_freight` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '运费ID',
  `company` varchar(255) DEFAULT NULL COMMENT '快递公司',
  `freight` decimal(10,2) DEFAULT NULL COMMENT '运费',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='运费表';

-- ----------------------------
-- Records of djmall_dict_product_freight
-- ----------------------------
INSERT INTO `djmall_dict_product_freight` VALUES ('1', 'SF', '13.00');
INSERT INTO `djmall_dict_product_freight` VALUES ('8', 'STO', '0.00');
INSERT INTO `djmall_dict_product_freight` VALUES ('9', 'YTO', '8.50');
INSERT INTO `djmall_dict_product_freight` VALUES ('10', 'ZTO', '0.00');
INSERT INTO `djmall_dict_product_freight` VALUES ('11', 'HTO', '20.00');
