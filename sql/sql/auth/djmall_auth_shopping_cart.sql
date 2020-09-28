/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : djmall

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2020-08-30 17:00:53
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for djmall_auth_shopping_cart
-- ----------------------------
DROP TABLE IF EXISTS `djmall_auth_shopping_cart`;
CREATE TABLE `djmall_auth_shopping_cart` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '购物车id',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `sku_id` int(11) DEFAULT NULL COMMENT '商品skuId',
  `product_id` int(11) DEFAULT NULL COMMENT '商品spuId',
  `quantity` int(11) DEFAULT NULL COMMENT '购买商品数量',
  `checked` int(11) DEFAULT NULL COMMENT '是否被选中 0：是 1：否',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of djmall_auth_shopping_cart
-- ----------------------------
INSERT INTO `djmall_auth_shopping_cart` VALUES ('39', '8', '141', '55', '1', '0');
INSERT INTO `djmall_auth_shopping_cart` VALUES ('40', '8', '137', '53', '3', '0');
INSERT INTO `djmall_auth_shopping_cart` VALUES ('55', '4', '143', '55', '1', '1');
