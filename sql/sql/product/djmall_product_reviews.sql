/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : djmall

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2020-08-30 17:04:14
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for djmall_product_reviews
-- ----------------------------
DROP TABLE IF EXISTS `djmall_product_reviews`;
CREATE TABLE `djmall_product_reviews` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `reviewer_id` int(11) DEFAULT NULL COMMENT '回复id',
  `product_id` int(11) DEFAULT NULL COMMENT '商品id',
  `comment` varchar(255) DEFAULT NULL COMMENT '商品评论',
  `reply_id` int(11) DEFAULT NULL COMMENT '回复人id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `rate` double(10,2) DEFAULT NULL COMMENT '评分等级',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COMMENT='商品评论表';

-- ----------------------------
-- Records of djmall_product_reviews
-- ----------------------------
INSERT INTO `djmall_product_reviews` VALUES ('13', '4', '49', '衣服质量很好，穿着很舒服', '-1', '2020-08-16 16:33:51', '5.00');
INSERT INTO `djmall_product_reviews` VALUES ('14', '4', '47', '垃圾苹果，sb特朗普ndsl', '-1', '2020-08-16 16:34:58', '1.00');
INSERT INTO `djmall_product_reviews` VALUES ('15', '4', '50', '华为牛逼', '-1', '2020-08-16 16:34:58', '5.00');
INSERT INTO `djmall_product_reviews` VALUES ('16', '4', '49', '第二次购买了，质量真的很好，建议大家购买', '-1', '2020-08-16 16:35:34', '5.00');
INSERT INTO `djmall_product_reviews` VALUES ('17', '4', '50', '华为是真牛逼', '-1', '2020-08-16 16:36:27', '5.00');
INSERT INTO `djmall_product_reviews` VALUES ('23', '3', '3', '谢谢老板，给老板来杯卡布奇诺！！！', '15', '2020-08-17 21:08:10', null);
INSERT INTO `djmall_product_reviews` VALUES ('24', '4', '52', 'nice', '-1', '2020-08-25 02:31:52', '5.00');
