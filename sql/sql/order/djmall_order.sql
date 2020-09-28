/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : djmall

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2020-08-30 17:03:45
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for djmall_order
-- ----------------------------
DROP TABLE IF EXISTS `djmall_order`;
CREATE TABLE `djmall_order` (
  `order_no` varchar(255) NOT NULL COMMENT '订单号',
  `buyer_id` int(11) NOT NULL COMMENT '买家ID',
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
  `alipay_trade_no` varchar(255) DEFAULT NULL COMMENT '支付宝交易号',
  PRIMARY KEY (`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单表-主';

-- ----------------------------
-- Records of djmall_order
-- ----------------------------
INSERT INTO `djmall_order` VALUES ('DJ20200731012811079944', '4', '10506.50', '9956.60', '8.50', '3', 'ALIPAY', '江苏', '徐州', '丰县', '程帆', '15949006976', '欢口镇4-47号', 'COMPLETED', '2020-07-31 01:28:11', '2020-08-17 20:44:13', '2020-08-22 22:18:12', null);
INSERT INTO `djmall_order` VALUES ('DJ20200809213923625668', '4', '10506.50', '9956.60', '8.50', '3', 'ALIPAY', '江苏', '徐州', '丰县', '程帆', '15949006976', '欢口镇4-47号', 'COMPLETED', '2020-08-09 21:39:24', '2020-08-18 20:44:16', '2020-08-22 22:18:13', null);
INSERT INTO `djmall_order` VALUES ('DJ20200809230629088068', '4', '10619.50', '10619.50', '21.50', '3', 'ALIPAY', '江苏', '徐州', '丰县', '程帆', '15949006976', '欢口镇4-47号', 'COMPLETED', '2020-08-09 23:06:29', '2020-08-19 20:44:19', '2020-08-22 22:18:15', null);
INSERT INTO `djmall_order` VALUES ('DJ20200822193607361321', '4', '5499.00', '5499.00', '0.00', '1', 'ALIPAY', '江苏', '徐州', '丰县', '程帆', '15949006976', '欢口镇4-47号', 'PAID', '2020-08-22 19:36:07', '2020-08-22 19:36:46', '2020-08-22 19:36:46', '2020082222001448660508595767');
INSERT INTO `djmall_order` VALUES ('DJ20200822200037226217', '4', '10525.00', '10525.00', '26.00', '2', 'ALIPAY', '江苏', '徐州', '丰县', '程帆', '15949006976', '欢口镇4-47号', 'PAID', '2020-08-22 20:00:37', '2020-08-22 20:01:49', '2020-08-22 20:01:49', '2020082222001448660508622252');
INSERT INTO `djmall_order` VALUES ('DJ20200822200234208564', '4', '99.00', '94.05', '0.00', '1', 'ALIPAY', '江苏', '徐州', '丰县', '程帆', '15949006976', '欢口镇4-47号', 'PAID', '2020-08-22 20:02:34', '2020-08-22 20:56:35', '2020-08-22 20:56:35', '2020082222001448660508615311');
INSERT INTO `djmall_order` VALUES ('DJ20200822213917362672', '4', '5499.00', '5499.00', '0.00', '1', 'ALIPAY', '江苏', '徐州', '丰县', '程帆', '15949006976', '欢口镇4-47号', 'PAID', '2020-08-22 21:39:17', '2020-08-22 21:41:07', '2020-08-22 21:41:07', '2020082222001448660508622255');
INSERT INTO `djmall_order` VALUES ('DJ20200822214032579492', '4', '6111.00', '6106.05', '13.00', '2', 'ALIPAY', '江苏', '徐州', '丰县', '程帆', '15949006976', '欢口镇4-47号', 'PAID', '2020-08-22 21:40:33', '2020-08-22 21:53:59', '2020-08-22 21:53:58', '2020082222001448660508612329');
INSERT INTO `djmall_order` VALUES ('DJ20200822220701945553', '4', '5499.00', '5499.00', '0.00', '1', 'ALIPAY', '江苏', '徐州', '丰县', '程帆', '15949006976', '欢口镇4-47号', 'PAID', '2020-08-22 22:07:02', '2020-08-22 22:11:43', '2020-08-22 22:11:43', '2020082222001448660508615313');
INSERT INTO `djmall_order` VALUES ('DJ20200822224025603933', '4', '6012.00', '6012.00', '13.00', '1', 'ALIPAY', '江苏', '徐州', '丰县', '程帆', '15949006976', '欢口镇4-47号', 'PAID', '2020-08-22 22:40:26', '2020-08-22 22:41:13', '2020-08-22 22:41:13', '2020082222001448660508612330');
INSERT INTO `djmall_order` VALUES ('DJ20200825022743298575', '4', '11613.00', '11613.00', '13.00', '2', 'WECHAT', '北京', '市辖区', '西城区', '程帆', '15949006976', '中南海1号楼', 'PAID', '2020-08-25 02:27:43', '2020-08-25 02:29:24', '2020-08-25 02:29:24', '2020082522001448660508831010');
