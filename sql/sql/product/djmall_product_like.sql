/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : djmall

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2020-08-30 17:04:09
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for djmall_product_like
-- ----------------------------
DROP TABLE IF EXISTS `djmall_product_like`;
CREATE TABLE `djmall_product_like` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '点赞id',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `product_id` int(11) DEFAULT NULL COMMENT '商品id',
  `is_like` int(11) DEFAULT NULL COMMENT '是否点赞 0 是 1 否',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='商品点赞表';

-- ----------------------------
-- Records of djmall_product_like
-- ----------------------------
INSERT INTO `djmall_product_like` VALUES ('1', '4', '55', '0');
INSERT INTO `djmall_product_like` VALUES ('2', '7', '55', '0');
INSERT INTO `djmall_product_like` VALUES ('3', '7', '54', '0');
INSERT INTO `djmall_product_like` VALUES ('4', '7', '52', '0');
INSERT INTO `djmall_product_like` VALUES ('5', '7', '47', '0');
INSERT INTO `djmall_product_like` VALUES ('6', '8', '53', '1');
INSERT INTO `djmall_product_like` VALUES ('7', '8', '51', '0');
