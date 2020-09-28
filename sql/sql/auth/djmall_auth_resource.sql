/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : djmall

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2020-08-30 17:00:31
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for djmall_auth_resource
-- ----------------------------
DROP TABLE IF EXISTS `djmall_auth_resource`;
CREATE TABLE `djmall_auth_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '资源ID',
  `resource_name` varchar(255) DEFAULT NULL COMMENT '资源名称',
  `url` varchar(255) DEFAULT NULL COMMENT '资源路径',
  `parent_id` int(11) DEFAULT NULL COMMENT '资源父级',
  `is_del` varchar(255) DEFAULT NULL COMMENT '资源状态 0 未删除 1 已删除',
  `resource_type` varchar(255) DEFAULT NULL COMMENT '资源类型 0：url 1：按钮',
  `resource_code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8 COMMENT='资源表';

-- ----------------------------
-- Records of djmall_auth_resource
-- ----------------------------
INSERT INTO `djmall_auth_resource` VALUES ('1', '权限管理', '--', '0', 'NOT_DEL', 'MENU', 'PERMISSION_MANAGE');
INSERT INTO `djmall_auth_resource` VALUES ('2', '用户管理', '/auth/user/toShow', '0', 'NOT_DEL', 'MENU', 'USER_MANAGE');
INSERT INTO `djmall_auth_resource` VALUES ('3', '角色管理', '/auth/role/toShow', '1', 'NOT_DEL', 'MENU', 'ROLE_MANAGE');
INSERT INTO `djmall_auth_resource` VALUES ('4', '资源管理', '/auth/resource/toShow', '1', 'NOT_DEL', 'MENU', 'RESOURCE_MANAGE');
INSERT INTO `djmall_auth_resource` VALUES ('5', '资源-新增', '--', '4', 'NOT_DEL', 'BUTTON', 'RESOURCE_ADD_BTN');
INSERT INTO `djmall_auth_resource` VALUES ('6', '资源-修改', '--', '4', 'NOT_DEL', 'BUTTON', 'RESOURCE_UPDATE_BTN');
INSERT INTO `djmall_auth_resource` VALUES ('7', '资源-删除', '--', '4', 'NOT_DEL', 'BUTTON', 'RESOURCE_DELETE_BTN');
INSERT INTO `djmall_auth_resource` VALUES ('8', '角色-新增', '--', '3', 'NOT_DEL', 'BUTTON', 'ROLE_ADD_BTN');
INSERT INTO `djmall_auth_resource` VALUES ('9', '角色-编辑', '--', '3', 'NOT_DEL', 'BUTTON', 'ROLE_UPDATE_BTN');
INSERT INTO `djmall_auth_resource` VALUES ('10', '用户-修改', '--', '2', 'NOT_DEL', 'BUTTON', 'USER_UPDATE_BTN');
INSERT INTO `djmall_auth_resource` VALUES ('11', '用户-激活', '--', '2', 'NOT_DEL', 'BUTTON', 'USER_ACTIVATION_BTN');
INSERT INTO `djmall_auth_resource` VALUES ('12', '用户-重置密码', '--', '2', 'NOT_DEL', 'BUTTON', 'USER_RESET_PASSWORD_BTN');
INSERT INTO `djmall_auth_resource` VALUES ('13', '用户-删除', '--', '2', 'NOT_DEL', 'BUTTON', 'USER_DELETE_BTN');
INSERT INTO `djmall_auth_resource` VALUES ('14', '用户-授权', '--', '2', 'NOT_DEL', 'BUTTON', 'USER_AUTH_BTN');
INSERT INTO `djmall_auth_resource` VALUES ('15', '角色-关联资源', '--', '3', 'NOT_DEL', 'BUTTON', 'ROLE_RELATED_RESOURCE_BTN');
INSERT INTO `djmall_auth_resource` VALUES ('16', '角色-删除', '--', '3', 'NOT_DEL', 'BUTTON', 'ROLE_DELETE_BTN');
INSERT INTO `djmall_auth_resource` VALUES ('17', '数据管理', '--', '0', 'NOT_DEL', 'MENU', 'DATA_MANAGE');
INSERT INTO `djmall_auth_resource` VALUES ('18', '字典管理', '/dict/base/toShow', '17', 'NOT_DEL', 'MENU', 'DICT_MANAGE');
INSERT INTO `djmall_auth_resource` VALUES ('19', '字典-新增', '--', '18', 'NOT_DEL', 'BUTTON', 'DICT_ADD_BTN');
INSERT INTO `djmall_auth_resource` VALUES ('20', '字典-修改', '--', '18', 'NOT_DEL', 'BUTTON', 'DICT_UPDATE_BTN');
INSERT INTO `djmall_auth_resource` VALUES ('21', '运费管理', '/dict/freight/toShow', '17', 'NOT_DEL', 'MENU', 'FREIGHT_MANAGE');
INSERT INTO `djmall_auth_resource` VALUES ('22', '运费-新增', '--', '21', 'NOT_DEL', 'BUTTON', 'FREIGHT_ADD_BTN');
INSERT INTO `djmall_auth_resource` VALUES ('23', '运费-修改', '--', '21', 'NOT_DEL', 'BUTTON', 'FREIGHT_UPDATE_BTN');
INSERT INTO `djmall_auth_resource` VALUES ('24', '属性管理', '/dict/attr/toShow', '17', 'NOT_DEL', 'MENU', 'ATTR_MANAGE');
INSERT INTO `djmall_auth_resource` VALUES ('25', '属性_新增', '--', '24', 'NOT_DEL', 'BUTTON', 'ATTR_ADD_BTN');
INSERT INTO `djmall_auth_resource` VALUES ('26', '关联属性值', '--', '24', 'NOT_DEL', 'BUTTON', 'RELATED_ATTR_VALUE_BTN');
INSERT INTO `djmall_auth_resource` VALUES ('27', '属性值_新增', '--', '24', 'NOT_DEL', 'BUTTON', 'ATTR_VALUE_ADD_BTN');
INSERT INTO `djmall_auth_resource` VALUES ('28', '属性值_删除', '--', '24', 'NOT_DEL', 'BUTTON', 'ATTR_VALUE_DELETE_BTN');
INSERT INTO `djmall_auth_resource` VALUES ('29', '通用SKU维护', '/dict/skuGm/toShow', '17', 'NOT_DEL', 'MENU', 'SKU_GM_MANAGE');
INSERT INTO `djmall_auth_resource` VALUES ('30', '通用SKU维护_新增', '--', '29', 'NOT_DEL', 'BUTTON', 'SKU_GM_ADD_BTN');
INSERT INTO `djmall_auth_resource` VALUES ('31', '关联属性', '--', '29', 'NOT_DEL', 'BUTTON', 'RELATED_ATTR_BTN');
INSERT INTO `djmall_auth_resource` VALUES ('32', '商品管理', '/product/spu/toShow', '0', 'NOT_DEL', 'MENU', 'PRODUCT_MANAGE');
INSERT INTO `djmall_auth_resource` VALUES ('33', '商品_新增', '--', '32', 'NOT_DEL', 'BUTTON', 'PRODUCT_ADD_BTN');
INSERT INTO `djmall_auth_resource` VALUES ('34', '商品_修改', '--', '32', 'NOT_DEL', 'BUTTON', 'PRODUCT_UPDATE_BTN');
INSERT INTO `djmall_auth_resource` VALUES ('35', '商品_上下架', '--', '32', 'NOT_DEL', 'BUTTON', 'PRODUCT_SHELF_BTN');
INSERT INTO `djmall_auth_resource` VALUES ('36', '增量索引', '--', '32', 'NOT_DEL', 'BUTTON', 'INCREMENTAL_INDEX_BTN');
INSERT INTO `djmall_auth_resource` VALUES ('37', '重构索引', '--', '32', 'NOT_DEL', 'BUTTON', 'REFACTORING_THE_INDEX_BTN');
INSERT INTO `djmall_auth_resource` VALUES ('38', '查看评论', '--', '32', 'NOT_DEL', 'BUTTON', 'VIEW_COMMENTS_BTN');
INSERT INTO `djmall_auth_resource` VALUES ('39', '下载导入模板', '--', '32', 'NOT_DEL', 'BUTTON', 'DOWNLOAD_THE_IMPORT_TEMPLATE_BTN');
INSERT INTO `djmall_auth_resource` VALUES ('40', '导入', '--', '32', 'NOT_DEL', 'BUTTON', 'IMPORT_BTN');
INSERT INTO `djmall_auth_resource` VALUES ('41', '商品SKU_修改库存', '--', '34', 'NOT_DEL', 'BUTTON', 'PRODUCT_SKU_UPDATE_COUNT_BTN');
INSERT INTO `djmall_auth_resource` VALUES ('42', '商品SKU_编辑', '--', '34', 'NOT_DEL', 'BUTTON', 'PRODUCT_SKU_EDIT_BTN');
INSERT INTO `djmall_auth_resource` VALUES ('43', '商品SKU_设为默认', '--', '34', 'NOT_DEL', 'BUTTON', 'PRODUCT_SKU_SET_AS_DEFAULT');
INSERT INTO `djmall_auth_resource` VALUES ('44', '商品SKU_上下架', '--', '34', 'NOT_DEL', 'BUTTON', 'PRODUCT_SKU_UPDATE_STATUS');
INSERT INTO `djmall_auth_resource` VALUES ('45', '商品SKU_展示', '--', '34', 'NOT_DEL', 'BUTTON', 'PRODUCT_SKU_SHOW_BTN');
INSERT INTO `djmall_auth_resource` VALUES ('46', '订单管理', '/order/toShow', '0', 'NOT_DEL', 'MENU', 'ORDER_MANAGE');
INSERT INTO `djmall_auth_resource` VALUES ('47', '导出订单', '--', '46', 'NOT_DEL', 'BUTTON', 'EXPORT_ORDER_BTN');
INSERT INTO `djmall_auth_resource` VALUES ('48', '统计报表', '/statement/toShow', '0', 'NOT_DEL', 'MENU', 'STATISTICAL_REPORT_MANAGE');
