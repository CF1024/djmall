/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : djmall

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2020-08-30 17:03:50
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for djmall_order_detail
-- ----------------------------
DROP TABLE IF EXISTS `djmall_order_detail`;
CREATE TABLE `djmall_order_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '明细ID',
  `parent_order_no` varchar(255) NOT NULL COMMENT '父订单号',
  `child_order_no` varchar(255) NOT NULL COMMENT '子订单号',
  `buyer_id` int(11) NOT NULL COMMENT '买家ID',
  `product_id` int(11) NOT NULL COMMENT '商品ID',
  `business_id` int(11) NOT NULL COMMENT '商户ID',
  `sku_id` int(11) NOT NULL COMMENT 'SKUID-针对再次购买时使用',
  `sku_info` varchar(255) NOT NULL COMMENT 'SKU信息(iphone-红色-64G)',
  `sku_price` decimal(10,2) NOT NULL COMMENT 'SKU价格',
  `sku_rate` int(11) NOT NULL COMMENT 'SK折扣',
  `buy_count` int(11) NOT NULL COMMENT '购买数量',
  `pay_money` decimal(10,2) NOT NULL COMMENT '支付金额（含运费）',
  `sku_freight` decimal(10,2) NOT NULL COMMENT '运费',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `product_name` varchar(255) DEFAULT NULL COMMENT '商品名',
  `is_comment` int(11) DEFAULT NULL COMMENT '是否已评论',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8 COMMENT='订单-商品-明细表';

-- ----------------------------
-- Records of djmall_order_detail
-- ----------------------------
INSERT INTO `djmall_order_detail` VALUES ('1', 'DJ20200731012811079944', 'DJ20200731012811278864', '4', '49', '2', '115', ' 白色- M码', '100.00', '100', '1', '100.00', '0.00', '2020-07-31 01:28:11', '衬衫【文艺范】', '1');
INSERT INTO `djmall_order_detail` VALUES ('2', 'DJ20200731012811079944', 'DJ20200731012811279028', '4', '47', '3', '104', ' 黑色- 64G', '5499.00', '90', '1', '4949.10', '0.00', '2020-07-31 01:28:11', 'iPhone11[24期免息]', '1');
INSERT INTO `djmall_order_detail` VALUES ('3', 'DJ20200731012811079944', 'DJ20200731012811279028', '4', '50', '3', '120', ' 零度白- 8GB- 128GB', '4899.00', '100', '1', '4907.50', '8.50', '2020-07-31 01:28:11', '华为 P40[6期免息]', '1');
INSERT INTO `djmall_order_detail` VALUES ('4', 'DJ20200809213923625668', 'DJ20200809213923918938', '4', '49', '2', '115', ' 白色- M码', '100.00', '100', '1', '100.00', '0.00', '2020-08-09 21:39:24', '衬衫【文艺范】', '1');
INSERT INTO `djmall_order_detail` VALUES ('5', 'DJ20200809213923625668', 'DJ20200809213923919674', '4', '50', '3', '120', ' 零度白- 8GB- 128GB', '4899.00', '100', '1', '4907.50', '8.50', '2020-08-09 21:39:24', '华为 P40[6期免息]', '1');
INSERT INTO `djmall_order_detail` VALUES ('6', 'DJ20200809213923625668', 'DJ20200809213923919674', '4', '47', '3', '104', ' 黑色- 64G', '5499.00', '90', '1', '4949.10', '0.00', '2020-08-09 21:39:24', 'iPhone11[24期免息]', '1');
INSERT INTO `djmall_order_detail` VALUES ('7', 'DJ20200809230629088068', 'DJ20200809230629401789', '4', '51', '2', '133', ' 白色- 128G', '5999.00', '100', '1', '6012.00', '13.00', '2020-08-09 23:06:29', 'iPhone11[12期免息]', '0');
INSERT INTO `djmall_order_detail` VALUES ('8', 'DJ20200809230629088068', 'DJ20200809230629401789', '4', '49', '2', '117', ' 黑色- XL码', '100.00', '100', '1', '100.00', '0.00', '2020-08-09 23:06:29', '衬衫【文艺范】', '0');
INSERT INTO `djmall_order_detail` VALUES ('9', 'DJ20200809230629088068', 'DJ20200809230629402125', '4', '50', '3', '125', ' 深海蓝- 6GB- 128GB', '4499.00', '100', '1', '4507.50', '8.50', '2020-08-09 23:06:29', '华为 P40[6期免息]', '0');
INSERT INTO `djmall_order_detail` VALUES ('31', 'DJ20200822193607361321', 'DJ20200822193607643115', '4', '52', '3', '136', '黑色-512GB', '5499.00', '100', '1', '5499.00', '0.00', '2020-08-22 19:36:08', '联想拯救者', '1');
INSERT INTO `djmall_order_detail` VALUES ('32', 'DJ20200822200037226217', 'DJ20200822200037501650', '4', '51', '2', '133', ' 白色- 128G', '5999.00', '100', '1', '6012.00', '13.00', '2020-08-22 20:00:38', 'iPhone11[12期免息]', '0');
INSERT INTO `djmall_order_detail` VALUES ('33', 'DJ20200822200037226217', 'DJ20200822200037502876', '4', '55', '6', '141', ' 42码- 白棕色', '4500.00', '100', '1', '4513.00', '13.00', '2020-08-22 20:00:38', 'AJ1倒钩', '0');
INSERT INTO `djmall_order_detail` VALUES ('34', 'DJ20200822200234208564', 'DJ20200822200234213593', '4', '53', '3', '137', '黑色-棉', '99.00', '95', '1', '94.05', '0.00', '2020-08-22 20:02:34', '短裤', '0');
INSERT INTO `djmall_order_detail` VALUES ('38', 'DJ20200822213917362672', 'DJ20200822213917371773', '4', '52', '3', '136', '黑色-512GB', '5499.00', '100', '1', '5499.00', '0.00', '2020-08-22 21:39:17', '联想拯救者', '0');
INSERT INTO `djmall_order_detail` VALUES ('39', 'DJ20200822214032579492', 'DJ20200822214032584653', '4', '51', '2', '135', ' 黑色- 128G', '5999.00', '100', '1', '6012.00', '13.00', '2020-08-22 21:40:33', 'iPhone11[12期免息]', '0');
INSERT INTO `djmall_order_detail` VALUES ('40', 'DJ20200822214032579492', 'DJ20200822214032586192', '4', '53', '3', '137', '黑色-棉', '99.00', '95', '1', '94.05', '0.00', '2020-08-22 21:40:33', '短裤', '0');
INSERT INTO `djmall_order_detail` VALUES ('41', 'DJ20200822220701945553', 'DJ20200822220701957414', '4', '52', '3', '136', '黑色-512GB', '5499.00', '100', '1', '5499.00', '0.00', '2020-08-22 22:07:02', '联想拯救者', '0');
INSERT INTO `djmall_order_detail` VALUES ('43', 'DJ20200822224025603933', 'DJ20200822224025603498', '4', '51', '2', '133', ' 白色- 128G', '5999.00', '100', '1', '6012.00', '13.00', '2020-08-22 22:40:26', 'iPhone11[12期免息]', '0');
INSERT INTO `djmall_order_detail` VALUES ('44', 'DJ20200825022743298575', 'DJ20200825022743558096', '4', '55', '6', '142', ' 40.5码- 白棕色', '5800.00', '100', '2', '11613.00', '13.00', '2020-08-25 02:27:44', 'AJ1倒钩', '0');
