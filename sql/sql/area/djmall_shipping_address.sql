/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : djmall

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2020-08-30 17:02:38
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for djmall_shipping_address
-- ----------------------------
DROP TABLE IF EXISTS `djmall_shipping_address`;
CREATE TABLE `djmall_shipping_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `receiver_name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `province` int(255) DEFAULT NULL,
  `city` int(255) DEFAULT NULL,
  `county` int(255) DEFAULT NULL,
  `detailed_address` varchar(255) DEFAULT NULL,
  `is_del` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of djmall_shipping_address
-- ----------------------------
INSERT INTO `djmall_shipping_address` VALUES ('1', '4', '程帆', '15949006976', '16', '229', '1930', '欢口镇4-47号', 'NOT_DEL');
INSERT INTO `djmall_shipping_address` VALUES ('2', '4', '程帆', '15949006976', '2', '52', '503', '来广营一号首都师范大学', 'NOT_DEL');
INSERT INTO `djmall_shipping_address` VALUES ('3', '4', '程帆', '15949006976', '2', '52', '501', '中南海1号楼', 'NOT_DEL');
