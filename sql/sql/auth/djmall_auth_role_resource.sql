/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : djmall

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2020-08-30 17:00:44
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for djmall_auth_role_resource
-- ----------------------------
DROP TABLE IF EXISTS `djmall_auth_role_resource`;
CREATE TABLE `djmall_auth_role_resource` (
  `role_id` int(11) DEFAULT NULL COMMENT '角色id',
  `resource_id` int(11) DEFAULT NULL COMMENT '资源id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色资源关联表';

-- ----------------------------
-- Records of djmall_auth_role_resource
-- ----------------------------
INSERT INTO `djmall_auth_role_resource` VALUES ('1', '1');
INSERT INTO `djmall_auth_role_resource` VALUES ('1', '3');
INSERT INTO `djmall_auth_role_resource` VALUES ('1', '8');
INSERT INTO `djmall_auth_role_resource` VALUES ('1', '9');
INSERT INTO `djmall_auth_role_resource` VALUES ('1', '15');
INSERT INTO `djmall_auth_role_resource` VALUES ('1', '16');
INSERT INTO `djmall_auth_role_resource` VALUES ('1', '4');
INSERT INTO `djmall_auth_role_resource` VALUES ('1', '5');
INSERT INTO `djmall_auth_role_resource` VALUES ('1', '6');
INSERT INTO `djmall_auth_role_resource` VALUES ('1', '7');
INSERT INTO `djmall_auth_role_resource` VALUES ('1', '2');
INSERT INTO `djmall_auth_role_resource` VALUES ('1', '10');
INSERT INTO `djmall_auth_role_resource` VALUES ('1', '11');
INSERT INTO `djmall_auth_role_resource` VALUES ('1', '12');
INSERT INTO `djmall_auth_role_resource` VALUES ('1', '13');
INSERT INTO `djmall_auth_role_resource` VALUES ('1', '14');
INSERT INTO `djmall_auth_role_resource` VALUES ('1', '17');
INSERT INTO `djmall_auth_role_resource` VALUES ('1', '18');
INSERT INTO `djmall_auth_role_resource` VALUES ('1', '19');
INSERT INTO `djmall_auth_role_resource` VALUES ('1', '20');
INSERT INTO `djmall_auth_role_resource` VALUES ('1', '21');
INSERT INTO `djmall_auth_role_resource` VALUES ('1', '22');
INSERT INTO `djmall_auth_role_resource` VALUES ('1', '23');
INSERT INTO `djmall_auth_role_resource` VALUES ('1', '24');
INSERT INTO `djmall_auth_role_resource` VALUES ('1', '25');
INSERT INTO `djmall_auth_role_resource` VALUES ('1', '26');
INSERT INTO `djmall_auth_role_resource` VALUES ('1', '27');
INSERT INTO `djmall_auth_role_resource` VALUES ('1', '28');
INSERT INTO `djmall_auth_role_resource` VALUES ('1', '29');
INSERT INTO `djmall_auth_role_resource` VALUES ('1', '30');
INSERT INTO `djmall_auth_role_resource` VALUES ('1', '31');
INSERT INTO `djmall_auth_role_resource` VALUES ('1', '32');
INSERT INTO `djmall_auth_role_resource` VALUES ('1', '33');
INSERT INTO `djmall_auth_role_resource` VALUES ('1', '34');
INSERT INTO `djmall_auth_role_resource` VALUES ('1', '41');
INSERT INTO `djmall_auth_role_resource` VALUES ('1', '42');
INSERT INTO `djmall_auth_role_resource` VALUES ('1', '43');
INSERT INTO `djmall_auth_role_resource` VALUES ('1', '44');
INSERT INTO `djmall_auth_role_resource` VALUES ('1', '45');
INSERT INTO `djmall_auth_role_resource` VALUES ('1', '35');
INSERT INTO `djmall_auth_role_resource` VALUES ('1', '36');
INSERT INTO `djmall_auth_role_resource` VALUES ('1', '37');
INSERT INTO `djmall_auth_role_resource` VALUES ('1', '38');
INSERT INTO `djmall_auth_role_resource` VALUES ('1', '39');
INSERT INTO `djmall_auth_role_resource` VALUES ('1', '40');
INSERT INTO `djmall_auth_role_resource` VALUES ('1', '46');
INSERT INTO `djmall_auth_role_resource` VALUES ('1', '48');
INSERT INTO `djmall_auth_role_resource` VALUES ('2', '32');
INSERT INTO `djmall_auth_role_resource` VALUES ('2', '33');
INSERT INTO `djmall_auth_role_resource` VALUES ('2', '34');
INSERT INTO `djmall_auth_role_resource` VALUES ('2', '41');
INSERT INTO `djmall_auth_role_resource` VALUES ('2', '42');
INSERT INTO `djmall_auth_role_resource` VALUES ('2', '43');
INSERT INTO `djmall_auth_role_resource` VALUES ('2', '44');
INSERT INTO `djmall_auth_role_resource` VALUES ('2', '45');
INSERT INTO `djmall_auth_role_resource` VALUES ('2', '35');
INSERT INTO `djmall_auth_role_resource` VALUES ('2', '36');
INSERT INTO `djmall_auth_role_resource` VALUES ('2', '37');
INSERT INTO `djmall_auth_role_resource` VALUES ('2', '38');
INSERT INTO `djmall_auth_role_resource` VALUES ('2', '39');
INSERT INTO `djmall_auth_role_resource` VALUES ('2', '40');
INSERT INTO `djmall_auth_role_resource` VALUES ('2', '46');
INSERT INTO `djmall_auth_role_resource` VALUES ('2', '47');
INSERT INTO `djmall_auth_role_resource` VALUES ('2', '48');
