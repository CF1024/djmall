/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : djmall

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2020-08-30 17:00:37
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for djmall_auth_role
-- ----------------------------
DROP TABLE IF EXISTS `djmall_auth_role`;
CREATE TABLE `djmall_auth_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(255) DEFAULT NULL COMMENT '角色名',
  `is_del` varchar(255) DEFAULT NULL COMMENT '角色状态 0 未删除 1 已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of djmall_auth_role
-- ----------------------------
INSERT INTO `djmall_auth_role` VALUES ('1', '管理员', 'NOT_DEL');
INSERT INTO `djmall_auth_role` VALUES ('2', '商家', 'NOT_DEL');
INSERT INTO `djmall_auth_role` VALUES ('3', '用户', 'NOT_DEL');
