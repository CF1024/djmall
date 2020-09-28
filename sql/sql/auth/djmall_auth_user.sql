/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : djmall

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2020-08-30 17:00:59
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for djmall_auth_user
-- ----------------------------
DROP TABLE IF EXISTS `djmall_auth_user`;
CREATE TABLE `djmall_auth_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户主键ID',
  `user_name` varchar(255) DEFAULT NULL COMMENT '用户名',
  `nick_name` varchar(255) DEFAULT NULL COMMENT '用户昵称',
  `user_pwd` varchar(255) DEFAULT NULL COMMENT '密码',
  `salt` varchar(255) DEFAULT NULL COMMENT '盐',
  `user_img` varchar(255) DEFAULT NULL,
  `user_phone` varchar(255) DEFAULT NULL COMMENT '手机号',
  `verify_code` varchar(255) DEFAULT NULL COMMENT '验证码',
  `user_email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `user_status` varchar(255) DEFAULT NULL COMMENT '用户激活状态 5：已激活 6：未激活',
  `user_sex` varchar(255) DEFAULT NULL COMMENT '性别 8 男 9 女  10 保密',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `invalidate_time` datetime DEFAULT NULL COMMENT '失效时间',
  `is_del` varchar(255) DEFAULT NULL COMMENT '用户状态 2：未删除 3 已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='djmall用户表';

-- ----------------------------
-- Records of djmall_auth_user
-- ----------------------------
INSERT INTO `djmall_auth_user` VALUES ('1', '12345', '雷霆嘎巴', '79d1a3ce610a5794eae87f06713c212c', '2f22c7e1f3c4891e6282d263ba510cd7', null, '15949006970', '123456', '952835040@qq.com', 'HAVE_ACTIVATE', 'SECRECY', '2020-06-05 19:29:28', '2020-08-25 09:34:42', '2020-06-10 14:10:46', 'NOT_DEL');
INSERT INTO `djmall_auth_user` VALUES ('2', '952835044', '程帆', '2c392a4b615f9e1c99f33b42cdbb08cc', 'b47dd9a3e3ca87a244aea14c63fa540b', null, '15949006976', '849259', '952835044@qq.com', 'HAVE_ACTIVATE', 'MAN', '2020-06-08 20:45:37', '2020-08-09 19:46:09', '2020-06-09 21:54:10', 'NOT_DEL');
INSERT INTO `djmall_auth_user` VALUES ('3', '123456', '刘宁', '9e7e03fbdc1063a31deb5c62f24b9ace', 'efd30520c4be146f1bdc0a4f5ab21160', null, '13338175166', '682823', '952835041@qq.com', 'HAVE_ACTIVATE', 'MAN', '2020-06-20 17:07:57', '2020-08-04 13:59:48', '2020-06-20 17:13:19', 'NOT_DEL');
INSERT INTO `djmall_auth_user` VALUES ('4', '1234567', 'giao哥', '03fb34e88ada6ba5b1dd7517690082e1', '22673f3dc0ac484b109431796e915c72', '64950f67732b462c8b5ff92adf7ef91e.jpg', '15949006971', null, '952835042@qq.com', 'HAVE_ACTIVATE', 'SECRECY', '2020-07-11 17:45:09', '2020-08-10 17:12:22', null, 'NOT_DEL');
INSERT INTO `djmall_auth_user` VALUES ('6', '12345678', '李江龙', '8b9edd0166dc41da5a3a2f7c1c14a081', '3fada407c87adf8227618fe047563a4b', null, '18260700320', null, 'chengfan0905@163.com', 'HAVE_ACTIVATE', 'SECRECY', '2020-08-09 20:01:55', '2020-08-09 20:02:16', null, 'NOT_DEL');
INSERT INTO `djmall_auth_user` VALUES ('7', '123456789', 'DJ816344', '90faa0f29b238ea556a62cbb536846c7', '724bc7b3b63dc3651bf165303fa393a8', null, '15949006677', null, '952833645@qq.com', 'HAVE_ACTIVATE', 'SECRECY', '2020-08-15 15:00:30', null, null, 'NOT_DEL');
INSERT INTO `djmall_auth_user` VALUES ('8', '610294853', 'mj01', '46bc3f005f7737dd4925558d6858ea24', '6a861e6957ae721a4d49320a77675622', null, '13546587985', null, '1234@qq.com', 'HAVE_ACTIVATE', 'SECRECY', '2020-08-24 16:18:53', '2020-08-25 02:45:50', null, 'NOT_DEL');
