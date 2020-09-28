/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : djmall

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2020-08-30 17:03:55
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for djmall_order_info
-- ----------------------------
DROP TABLE IF EXISTS `djmall_order_info`;
CREATE TABLE `djmall_order_info` (
  `order_no` varchar(255) NOT NULL COMMENT '订单号',
  `parent_order_no` varchar(255) NOT NULL COMMENT '父订单号',
  `buyer_id` int(11) NOT NULL COMMENT '买家ID',
  `business_id` int(11) NOT NULL COMMENT '商户ID',
  `product_id` int(11) NOT NULL COMMENT '商品ID',
  `total_money` decimal(10,2) NOT NULL COMMENT '订单总金额',
  `total_pay_money` decimal(10,2) NOT NULL COMMENT '实付总金额',
  `total_freight` decimal(10,2) NOT NULL COMMENT '总运费',
  `total_buy_count` int(11) NOT NULL COMMENT '总购买数量',
  `pay_type` varchar(255) NOT NULL COMMENT '支付方式',
  `receiver_province` varchar(255) NOT NULL COMMENT '收货信息-省',
  `receiver_city` varchar(255) NOT NULL COMMENT '收货信息-城市',
  `receiver_county` varchar(255) NOT NULL COMMENT '收货信息-区县',
  `receiver_name` varchar(255) NOT NULL COMMENT '收货信息-收货人',
  `receiver_phone` varchar(255) NOT NULL COMMENT '收货信息-手机号',
  `receiver_detail` varchar(255) NOT NULL COMMENT '收货信息-地址明细',
  `order_status` varchar(255) NOT NULL COMMENT '订单状态:[已取消/待支付/待发货/已发货/确认收货/已完成]',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remind_status` int(255) DEFAULT NULL COMMENT '提醒状态',
  `remind_time` datetime DEFAULT NULL COMMENT '提醒时间',
  `alipay_trade_no` varchar(255) DEFAULT NULL COMMENT '支付宝交易号',
  PRIMARY KEY (`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单表-子';

-- ----------------------------
-- Records of djmall_order_info
-- ----------------------------
INSERT INTO `djmall_order_info` VALUES ('DJ20200731012811278864', 'DJ20200731012811079944', '4', '2', '49', '100.00', '100.00', '0.00', '1', 'ALIPAY', '江苏', '徐州', '丰县', '程帆', '15949006976', '欢口镇4-47号', 'COMPLETED', '2020-07-31 01:28:11', null, '2020-08-22 22:18:19', '1', '2020-08-10 20:48:42', null);
INSERT INTO `djmall_order_info` VALUES ('DJ20200731012811279028', 'DJ20200731012811079944', '4', '3', '50', '10406.50', '9856.60', '8.50', '2', 'ALIPAY', '江苏', '徐州', '丰县', '程帆', '15949006976', '欢口镇4-47号', 'COMPLETED', '2020-07-31 01:28:11', null, '2020-08-22 22:18:20', '2', '2020-08-05 16:19:35', null);
INSERT INTO `djmall_order_info` VALUES ('DJ20200809213923918938', 'DJ20200809213923625668', '4', '2', '49', '100.00', '100.00', '0.00', '1', 'ALIPAY', '江苏', '徐州', '丰县', '程帆', '15949006976', '欢口镇4-47号', 'COMPLETED', '2020-08-09 21:39:24', null, '2020-08-22 22:18:22', null, null, null);
INSERT INTO `djmall_order_info` VALUES ('DJ20200809213923919674', 'DJ20200809213923625668', '4', '3', '47', '10406.50', '9856.60', '8.50', '2', 'ALIPAY', '江苏', '徐州', '丰县', '程帆', '15949006976', '欢口镇4-47号', 'COMPLETED', '2020-08-09 21:39:24', null, '2020-08-22 22:18:23', null, null, null);
INSERT INTO `djmall_order_info` VALUES ('DJ20200809230629401789', 'DJ20200809230629088068', '4', '2', '49', '6112.00', '6112.00', '13.00', '2', 'ALIPAY', '江苏', '徐州', '丰县', '程帆', '15949006976', '欢口镇4-47号', 'COMPLETED', '2020-08-09 23:06:29', null, '2020-08-22 22:18:24', null, null, null);
INSERT INTO `djmall_order_info` VALUES ('DJ20200822193607643115', 'DJ20200822193607361321', '4', '3', '52', '5499.00', '5499.00', '0.00', '1', 'ALIPAY', '江苏', '徐州', '丰县', '程帆', '15949006976', '欢口镇4-47号', 'COMPLETED', '2020-08-22 19:36:07', '2020-08-22 19:36:46', '2020-08-25 02:31:51', '2', '2020-08-25 08:31:16', '2020082222001448660508595767');
INSERT INTO `djmall_order_info` VALUES ('DJ20200822200037501650', 'DJ20200822200037226217', '4', '2', '51', '6012.00', '6012.00', '13.00', '1', 'ALIPAY', '江苏', '徐州', '丰县', '程帆', '15949006976', '欢口镇4-47号', 'PAID', '2020-08-22 20:00:37', '2020-08-22 20:01:49', '2020-08-25 02:31:18', '1', '2020-08-25 08:31:18', '2020082222001448660508622252');
INSERT INTO `djmall_order_info` VALUES ('DJ20200822200037502876', 'DJ20200822200037226217', '4', '6', '55', '4513.00', '4513.00', '13.00', '1', 'ALIPAY', '江苏', '徐州', '丰县', '程帆', '15949006976', '欢口镇4-47号', 'PAID', '2020-08-22 20:00:37', '2020-08-22 20:01:49', '2020-08-25 02:31:19', '1', '2020-08-25 08:31:20', '2020082222001448660508622252');
INSERT INTO `djmall_order_info` VALUES ('DJ20200822200234213593', 'DJ20200822200234208564', '4', '3', '53', '99.00', '94.05', '0.00', '1', 'ALIPAY', '江苏', '徐州', '丰县', '程帆', '15949006976', '欢口镇4-47号', 'CONFIRM_RECEIPT', '2020-08-22 20:02:34', '2020-08-22 20:56:35', '2020-08-25 02:38:55', '1', '2020-08-25 08:31:22', '2020082222001448660508615311');
INSERT INTO `djmall_order_info` VALUES ('DJ20200822213917371773', 'DJ20200822213917362672', '4', '3', '52', '5499.00', '5499.00', '0.00', '1', 'ALIPAY', '江苏', '徐州', '丰县', '程帆', '15949006976', '欢口镇4-47号', 'CONFIRM_RECEIPT', '2020-08-22 21:39:17', '2020-08-22 21:41:07', '2020-08-25 02:38:57', null, null, '2020082222001448660508622255');
INSERT INTO `djmall_order_info` VALUES ('DJ20200822214032584653', 'DJ20200822214032579492', '4', '2', '51', '6012.00', '6012.00', '13.00', '1', 'ALIPAY', '江苏', '徐州', '丰县', '程帆', '15949006976', '欢口镇4-47号', 'PAID', '2020-08-22 21:40:33', '2020-08-22 21:53:59', '2020-08-22 21:53:58', null, null, '2020082222001448660508612329');
INSERT INTO `djmall_order_info` VALUES ('DJ20200822214032586192', 'DJ20200822214032579492', '4', '3', '53', '99.00', '94.05', '0.00', '1', 'ALIPAY', '江苏', '徐州', '丰县', '程帆', '15949006976', '欢口镇4-47号', 'PAID', '2020-08-22 21:40:33', '2020-08-22 21:53:59', '2020-08-22 21:53:58', null, null, '2020082222001448660508612329');
INSERT INTO `djmall_order_info` VALUES ('DJ20200822220701957414', 'DJ20200822220701945553', '4', '3', '52', '5499.00', '5499.00', '0.00', '1', 'ALIPAY', '江苏', '徐州', '丰县', '程帆', '15949006976', '欢口镇4-47号', 'PAID', '2020-08-22 22:07:02', '2020-08-22 22:11:43', '2020-08-22 22:11:43', null, null, '2020082222001448660508615313');
INSERT INTO `djmall_order_info` VALUES ('DJ20200822224025603498', 'DJ20200822224025603933', '4', '2', '51', '6012.00', '6012.00', '13.00', '1', 'ALIPAY', '江苏', '徐州', '丰县', '程帆', '15949006976', '欢口镇4-47号', 'PAID', '2020-08-22 22:40:26', '2020-08-22 22:41:13', '2020-08-22 22:41:13', null, null, '2020082222001448660508612330');
INSERT INTO `djmall_order_info` VALUES ('DJ20200825022743558096', 'DJ20200825022743298575', '4', '6', '55', '11613.00', '11613.00', '13.00', '2', 'WECHAT', '北京', '市辖区', '西城区', '程帆', '15949006976', '中南海1号楼', 'PAID', '2020-08-25 02:27:43', '2020-08-25 02:29:24', '2020-08-25 02:30:02', '1', '2020-08-25 08:30:03', '2020082522001448660508831010');
