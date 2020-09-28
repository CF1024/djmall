/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : djmall

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2020-08-30 17:02:50
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for djmall_dict_base_data
-- ----------------------------
DROP TABLE IF EXISTS `djmall_dict_base_data`;
CREATE TABLE `djmall_dict_base_data` (
  `base_code` varchar(255) DEFAULT NULL COMMENT '编码',
  `base_name` varchar(255) DEFAULT NULL COMMENT '字典名',
  `parent_code` varchar(255) DEFAULT NULL COMMENT '父级编码'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='基础数据表';

-- ----------------------------
-- Records of djmall_dict_base_data
-- ----------------------------
INSERT INTO `djmall_dict_base_data` VALUES ('STATUS', '状态', 'SYSTEM');
INSERT INTO `djmall_dict_base_data` VALUES ('NOT_DEL', '未删除', 'STATUS');
INSERT INTO `djmall_dict_base_data` VALUES ('HAVE_DEL', '已删除', 'STATUS');
INSERT INTO `djmall_dict_base_data` VALUES ('ACTIVATE_STATUS', '激活状态', 'SYSTEM');
INSERT INTO `djmall_dict_base_data` VALUES ('HAVE_ACTIVATE', '已激活', 'ACTIVATE_STATUS');
INSERT INTO `djmall_dict_base_data` VALUES ('NOT_ACTIVATE', '未激活', 'ACTIVATE_STATUS');
INSERT INTO `djmall_dict_base_data` VALUES ('USER_SEX', '用户性别', 'SYSTEM');
INSERT INTO `djmall_dict_base_data` VALUES ('MAN', '男', 'USER_SEX');
INSERT INTO `djmall_dict_base_data` VALUES ('WOMAN', '女', 'USER_SEX');
INSERT INTO `djmall_dict_base_data` VALUES ('SECRECY', '保密', 'USER_SEX');
INSERT INTO `djmall_dict_base_data` VALUES ('RESOURCE_TYPE', '资源类型', 'SYSTEM');
INSERT INTO `djmall_dict_base_data` VALUES ('MENU', '菜单', 'RESOURCE_TYPE');
INSERT INTO `djmall_dict_base_data` VALUES ('BUTTON', '按钮', 'RESOURCE_TYPE');
INSERT INTO `djmall_dict_base_data` VALUES ('FORCE_UPDATE_PWD', '强制修改密码', 'STATUS');
INSERT INTO `djmall_dict_base_data` VALUES ('LOGISTICS_COMPANY', '物流公司', 'SYSTEM');
INSERT INTO `djmall_dict_base_data` VALUES ('SF', '顺丰', 'LOGISTICS_COMPANY');
INSERT INTO `djmall_dict_base_data` VALUES ('STO', '申通', 'LOGISTICS_COMPANY');
INSERT INTO `djmall_dict_base_data` VALUES ('YTO', '圆通', 'LOGISTICS_COMPANY');
INSERT INTO `djmall_dict_base_data` VALUES ('ZTO', '中通', 'LOGISTICS_COMPANY');
INSERT INTO `djmall_dict_base_data` VALUES ('HTO', '汇通', 'LOGISTICS_COMPANY');
INSERT INTO `djmall_dict_base_data` VALUES ('YUNDA', '韵达', 'LOGISTICS_COMPANY');
INSERT INTO `djmall_dict_base_data` VALUES ('PRODUCT_STATUS', '商品状态', 'SYSTEM');
INSERT INTO `djmall_dict_base_data` VALUES ('PRODUCT_STATUS_UP', '上架', 'PRODUCT_STATUS');
INSERT INTO `djmall_dict_base_data` VALUES ('PRODUCT_STATUS_DOWN', '下架', 'PRODUCT_STATUS');
INSERT INTO `djmall_dict_base_data` VALUES ('WHETHER_DEFAULT', '是否默认', 'SYSTEM');
INSERT INTO `djmall_dict_base_data` VALUES ('HAVE_DEFAULT', '默认', 'WHETHER_DEFAULT');
INSERT INTO `djmall_dict_base_data` VALUES ('NOT_DEFAULT', '不默认', 'WHETHER_DEFAULT');
INSERT INTO `djmall_dict_base_data` VALUES ('PAYMENT_TYPES', '支付方式', 'SYSTEM');
INSERT INTO `djmall_dict_base_data` VALUES ('ALIPAY', '支付宝', 'PAYMENT_TYPES');
INSERT INTO `djmall_dict_base_data` VALUES ('WECHAT', '微信', 'PAYMENT_TYPES');
INSERT INTO `djmall_dict_base_data` VALUES ('CASHONDELIVERY', '货到付款', 'PAYMENT_TYPES');
INSERT INTO `djmall_dict_base_data` VALUES ('ORDER_STATUS', '订单状态', 'SYSTEM');
INSERT INTO `djmall_dict_base_data` VALUES ('CANCELLED', '已取消', 'ORDER_STATUS');
INSERT INTO `djmall_dict_base_data` VALUES ('PENDING_PAYMENT', '待支付', 'ORDER_STATUS');
INSERT INTO `djmall_dict_base_data` VALUES ('TO_BE_DELIVERED', '待发货', 'ORDER_STATUS');
INSERT INTO `djmall_dict_base_data` VALUES ('SHIPPED', '已发货', 'ORDER_STATUS');
INSERT INTO `djmall_dict_base_data` VALUES ('CONFIRM_RECEIPT', '确认收货', 'ORDER_STATUS');
INSERT INTO `djmall_dict_base_data` VALUES ('COMPLETED', '已完成', 'ORDER_STATUS');
INSERT INTO `djmall_dict_base_data` VALUES ('PAID', '已支付', 'PAYMENT_TYPES');
INSERT INTO `djmall_dict_base_data` VALUES ('SKY_EYE', '天眼壹号', 'SYSTEM');
