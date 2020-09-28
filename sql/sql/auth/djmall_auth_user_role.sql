/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : djmall

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2020-08-30 17:01:06
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for djmall_auth_user_role
-- ----------------------------
DROP TABLE IF EXISTS `djmall_auth_user_role`;
CREATE TABLE `djmall_auth_user_role` (
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `role_id` int(11) DEFAULT NULL COMMENT '角色id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色关联表';

-- ----------------------------
-- Records of djmall_auth_user_role
-- ----------------------------
INSERT INTO `djmall_auth_user_role` VALUES ('2', '2');
INSERT INTO `djmall_auth_user_role` VALUES ('1', '1');
INSERT INTO `djmall_auth_user_role` VALUES ('4', '1');
INSERT INTO `djmall_auth_user_role` VALUES ('6', '2');
INSERT INTO `djmall_auth_user_role` VALUES ('7', '3');
INSERT INTO `djmall_auth_user_role` VALUES ('8', '3');
INSERT INTO `djmall_auth_user_role` VALUES ('3', '2');
