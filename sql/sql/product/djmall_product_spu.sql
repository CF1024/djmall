/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : djmall

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2020-08-30 17:04:26
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for djmall_product_spu
-- ----------------------------
DROP TABLE IF EXISTS `djmall_product_spu`;
CREATE TABLE `djmall_product_spu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品ID',
  `product_name` varchar(255) DEFAULT NULL COMMENT '商品名',
  `freight_id` int(11) DEFAULT NULL COMMENT '运费表ID',
  `product_describe` varchar(255) DEFAULT NULL COMMENT '商品描述',
  `product_img` varchar(255) DEFAULT NULL COMMENT '商品照片',
  `product_type` varchar(255) DEFAULT NULL COMMENT '商品类型（字典code）',
  `user_id` int(11) DEFAULT NULL COMMENT '新增人ID（用于展示判断）',
  `product_status` varchar(255) DEFAULT '1' COMMENT 'SKU状态[0下架,1上架]',
  `order_number` int(11) DEFAULT NULL COMMENT '订单量',
  `praise_number` int(11) DEFAULT NULL COMMENT '点赞量',
  `reviews_number` int(11) DEFAULT NULL COMMENT '评论量',
  `good_rate` int(11) DEFAULT NULL COMMENT '好评率',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of djmall_product_spu
-- ----------------------------
INSERT INTO `djmall_product_spu` VALUES ('47', 'iPhone11[24期免息]', '10', '贵 装逼神器', '7bd997ecd98b4779bda48973f808c715.jpg', '手机', '3', 'PRODUCT_STATUS_UP', '0', '1', '2', null, '2020-07-24 18:24:50', '2020-08-16 16:31:17');
INSERT INTO `djmall_product_spu` VALUES ('49', '衬衫【文艺范】', '8', '帅帅气气，阿giao见了都说：“一giao窝里giao”', '1c828c5dcc6e489082e7839c52de7e5f.jpg', '衣服', '2', 'PRODUCT_STATUS_UP', '0', '0', '2', null, '2020-07-24 18:24:50', '2020-08-15 18:01:20');
INSERT INTO `djmall_product_spu` VALUES ('50', '华为 P40[6期免息]', '9', '国产手机\r\n强大的鸿蒙(OS)系统', '0ceab2ae96a84cd9b3448fe7fa312c98.jpg', '手机', '3', 'PRODUCT_STATUS_UP', '0', '0', '2', null, '2020-07-24 18:24:50', '2020-08-16 16:31:17');
INSERT INTO `djmall_product_spu` VALUES ('51', 'iPhone11[12期免息]', '1', 'IOS系统', 'a2c187744f1143f88ad23e0f1e08a95b.jpg', '手机', '2', 'PRODUCT_STATUS_UP', '0', '1', '0', null, '2020-07-24 18:24:50', '2020-08-15 18:01:20');
INSERT INTO `djmall_product_spu` VALUES ('52', '联想拯救者', '10', '本人最爱游戏本', '0c3a7045a2fa45cf9cd4bb46ea917416.jpg', '电脑', '3', 'PRODUCT_STATUS_UP', '0', '1', '1', null, '2020-08-08 16:40:09', '2020-08-15 18:01:20');
INSERT INTO `djmall_product_spu` VALUES ('53', '短裤', '10', '夏季必备', '23bb226ce33e49bb8359f91e0c62fa17.jpg', '衣服', '3', 'PRODUCT_STATUS_UP', '0', '0', '0', null, '2020-08-08 16:40:09', '2020-08-15 18:01:20');
INSERT INTO `djmall_product_spu` VALUES ('54', '敞篷宝马【概念版】', '10', '性能怪物，撩妹神器', 'f84c7e49ea0a433ab6227f277d183d92.jpg', '汽车', '6', 'PRODUCT_STATUS_UP', '0', '1', '0', null, '2020-08-08 16:40:09', '2020-08-15 18:01:20');
INSERT INTO `djmall_product_spu` VALUES ('55', 'AJ1倒钩', '1', 'NBA名人堂传奇 乔丹代言', '1fa357390c1c408fa2ee9c846a8f4f7f.jpg', '鞋子', '6', 'PRODUCT_STATUS_UP', '0', '2', '0', null, '2020-08-08 16:40:09', '2020-08-15 18:01:20');
INSERT INTO `djmall_product_spu` VALUES ('56', 'test', '9', '1236', '34d4768df3974f7abf975f0ddac9118c.jpg', '手机', '3', 'PRODUCT_STATUS_UP', '0', '0', '0', null, '2020-08-25 02:36:10', '2020-08-25 02:37:05');
