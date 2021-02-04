/*
 Navicat Premium Data Transfer

 Source Server         : beiru
 Source Server Type    : MySQL
 Source Server Version : 50728
 Source Host           : rm-bp1yyix8l2696mx0njo.mysql.rds.aliyuncs.com:3306
 Source Schema         : elise

 Target Server Type    : MySQL
 Target Server Version : 50728
 File Encoding         : 65001

 Date: 23/09/2020 17:49:53
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin_menu
-- ----------------------------
DROP TABLE IF EXISTS `admin_menu`;
CREATE TABLE `admin_menu`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `menu_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `sort` int(11) NULL DEFAULT NULL,
  `sub_menu_ids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin_menu
-- ----------------------------
INSERT INTO `admin_menu` VALUES (1, '2020-07-31 20:28:22', 'chartRank', '菜单管理', 0, '9,11,10');
INSERT INTO `admin_menu` VALUES (3, '2020-07-31 20:30:41', 'productHouse', '系统监控', 100, '16,17');
INSERT INTO `admin_menu` VALUES (4, '2020-07-31 20:31:17', 'addUser', '系统管理', 0, '12,13,14,4,6,5,7,8,2,3,1');

-- ----------------------------
-- Table structure for admin_option_log
-- ----------------------------
DROP TABLE IF EXISTS `admin_option_log`;
CREATE TABLE `admin_option_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `call_api` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `error_log` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `param` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `user_id` int(11) NULL DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 280 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin_option_log
-- ----------------------------
INSERT INTO `admin_option_log` VALUES (279, '/admin/v1/login', '2020-09-23 17:49:14', NULL, '管理系统登录', '0:0:0:0:0:0:0:1', 'LoginParam->LoginParam(username=admin, password=123456)\n', 'OK', 1, 'admin');

-- ----------------------------
-- Table structure for admin_resource
-- ----------------------------
DROP TABLE IF EXISTS `admin_resource`;
CREATE TABLE `admin_resource`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `color` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `_condition` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `option_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `resource_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `route` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `sub_menu_id` bigint(20) NULL DEFAULT NULL,
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `ROUTE_UN_IDX`(`route`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 497 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin_resource
-- ----------------------------
INSERT INTO `admin_resource` VALUES (436, NULL, NULL, '2020-09-23 17:39:32', '客户端用户关系表', NULL, '客户端用户关系表', 'app-push-client-user-list', 'PANEL://{\"method\":\"GET\",\"url\":\"/admin/access/v1/app/push/client-user/list\"}', 3, 'MAIN');
INSERT INTO `admin_resource` VALUES (437, '', '', '2020-09-23 17:39:32', '发送单播', '发送单播', '发送单播', 'app-upush-send-unicast', 'PANEL://{\"method\":\"POST\",\"url\":\"/admin/access/v1/app/upush/send/unicast\"}', 2, 'COMMON');
INSERT INTO `admin_resource` VALUES (438, '', '', '2020-09-23 17:39:32', '发送广播', '发送广播', '发送广播', 'app-upush-send-broadcast', 'PANEL://{\"method\":\"POST\",\"url\":\"/admin/access/v1/app/upush/send/broadcast\"}', 2, 'COMMON');
INSERT INTO `admin_resource` VALUES (439, '', '', '2020-09-23 17:39:32', '推送详情', '查看', '推送详情', 'app-push-list', 'PANEL://{\"method\":\"GET\",\"url\":\"/admin/access/v1/app/push/get/{id}\"}', 2, 'COLUMN');
INSERT INTO `admin_resource` VALUES (440, NULL, NULL, '2020-09-23 17:39:33', '推送管理', NULL, '推送管理', 'app-push-list', 'PANEL://{\"method\":\"GET\",\"url\":\"/admin/access/v1/app/push/list\"}', 2, 'MAIN');
INSERT INTO `admin_resource` VALUES (441, NULL, NULL, '2020-09-23 17:39:33', '友盟推送配置', NULL, '友盟推送配置', 'app-upush-config', 'PANEL://{\"method\":\"POST\",\"url\":\"/admin/access/v1/app/upush/config\"}', 1, 'MAIN');
INSERT INTO `admin_resource` VALUES (442, '', '', '2020-09-23 17:39:33', '添加灰度用户', '添加灰度用户', '添加灰度用户', 'app-patch-gray-user-create', 'PANEL://{\"method\":\"PUT\",\"url\":\"/admin/access/v1/app-patch/gray-user\"}', 7, 'COMMON');
INSERT INTO `admin_resource` VALUES (443, '', '', '2020-09-23 17:39:33', '删除灰度用户', '删除', '删除灰度用户', 'app-patch-gray-user-del', 'REQUEST://{\"alert\":\"您确定要删除该数据吗？\",\"method\":\"DELETE\",\"url\":\"/admin/access/v1/app-patch/gray-user/{id}\"}', 7, 'COLUMN');
INSERT INTO `admin_resource` VALUES (444, NULL, NULL, '2020-09-23 17:39:33', '灰度用户列表', NULL, '灰度用户列表', 'app-patch-gray-user-list', 'PANEL://{\"method\":\"GET\",\"url\":\"/admin/access/v1/app-patch/gray-user/list\"}', 7, 'MAIN');
INSERT INTO `admin_resource` VALUES (445, '', '', '2020-09-23 17:39:33', '添加分支', '添加分支', '添加分支', 'app-patch-branch-create', 'PANEL://{\"method\":\"PUT\",\"url\":\"/admin/access/v1/app-patch/branch\"}', 5, 'COMMON');
INSERT INTO `admin_resource` VALUES (446, '', '', '2020-09-23 17:39:33', '分支信息', '查看', '分支信息', 'app-patch-branch-list', 'PANEL://{\"method\":\"GET\",\"url\":\"/admin/access/v1/app-patch/branch/{id}\"}', 5, 'COLUMN');
INSERT INTO `admin_resource` VALUES (447, '', '', '2020-09-23 17:39:33', '删除分支', '删除', '删除分支', 'app-patch-branch-del', 'REQUEST://{\"alert\":\"您确定要删除该数据吗？\",\"method\":\"DELETE\",\"url\":\"/admin/access/v1/app-patch/branch/{id}\"}', 5, 'COLUMN');
INSERT INTO `admin_resource` VALUES (448, '', '', '2020-09-23 17:39:33', '清除分支版本', '清除分支版本', '清除分支版本', 'app-patch-branch-version', 'REQUEST://{\"alert\":\"您确定清除该分支下的所有版本吗？这是一个危险并不可逆的操作，如果您对该功能不了解或不完全了解不要进行该操作！\",\"method\":\"DELETE\",\"url\":\"/admin/access/v1/app-patch/branch/version/{id}\"}', 5, 'COLUMN');
INSERT INTO `admin_resource` VALUES (449, NULL, NULL, '2020-09-23 17:39:33', '热更新分支列表', NULL, '热更新分支列表', 'app-patch-branch-list', 'PANEL://{\"method\":\"GET\",\"url\":\"/admin/access/v1/app-patch/branch/list\"}', 5, 'MAIN');
INSERT INTO `admin_resource` VALUES (450, NULL, NULL, '2020-09-23 17:39:34', '热更新配置', NULL, '热更新配置', 'app-patch-config', 'PANEL://{\"method\":\"POST\",\"url\":\"/admin/access/v1/app-patch/config\"}', 8, 'MAIN');
INSERT INTO `admin_resource` VALUES (451, '', '', '2020-09-23 17:39:34', '查看版本信息', '查看', '查看版本信息', 'app-patch-version-get', 'PANEL://{\"method\":\"GET\",\"url\":\"/admin/access/v1/app-patch/version/{id}\"}', 6, 'COLUMN');
INSERT INTO `admin_resource` VALUES (452, '', '{updateStatus}==1', '2020-09-23 17:39:34', '版本进入灰度', '灰度', '版本进入灰度', 'app-patch-version-gray', 'REQUEST://{\"alert\":\"您确定要进行灰度发布吗？\",\"method\":\"POST\",\"url\":\"/admin/access/v1/app-patch/version/gray/{id}\"}', 6, 'COLUMN');
INSERT INTO `admin_resource` VALUES (453, '', '{updateStatus}==2', '2020-09-23 17:39:34', '版本发布', '发布', '版本发布', 'app-patch-version-release', 'REQUEST://{\"alert\":\"您确定要正式发布吗？\",\"method\":\"POST\",\"url\":\"/admin/access/v1/app-patch/version/release/{id}\"}', 6, 'COLUMN');
INSERT INTO `admin_resource` VALUES (454, '', '{updateStatus}==2', '2020-09-23 17:39:34', '版本失败', '失败', '版本失败', 'app-patch-version-defeated', 'REQUEST://{\"alert\":\"您确定要失败吗？\",\"method\":\"DELETE\",\"url\":\"/admin/access/v1/app-patch/version/defeated/{id}\"}', 6, 'COLUMN');
INSERT INTO `admin_resource` VALUES (455, NULL, NULL, '2020-09-23 17:39:34', '热更新版本列表', NULL, '热更新版本列表', 'app-patch-version-list', 'PANEL://{\"method\":\"GET\",\"url\":\"/admin/access/v1/app-patch/version/list\"}', 6, 'MAIN');
INSERT INTO `admin_resource` VALUES (456, '', '', '2020-09-23 17:39:34', '发布Android版本', '发布安卓', '发布Android版本', 'app-version-create', 'PANEL://{\"method\":\"PUT\",\"url\":\"/admin/access/v1/app/android/version\"}', 4, 'COMMON');
INSERT INTO `admin_resource` VALUES (457, '', '', '2020-09-23 17:39:34', '发布IOS版本', '发布苹果', '发布IOS版本', 'app-version-create', 'PANEL://{\"method\":\"PUT\",\"url\":\"/admin/access/v1/app/ios/version\"}', 4, 'COMMON');
INSERT INTO `admin_resource` VALUES (458, '', '', '2020-09-23 17:39:34', '查看版本信息', '查看', '查看版本信息', 'app-version-get', 'PANEL://{\"method\":\"GET\",\"url\":\"/admin/access/v1/app/version/{id}\"}', 4, 'COLUMN');
INSERT INTO `admin_resource` VALUES (459, '', '{updateStatus}==1', '2020-09-23 17:39:34', '版本进入灰度', '灰度', '版本进入灰度', 'app-version-gray', 'REQUEST://{\"alert\":\"您确定要进行灰度发布吗？\",\"method\":\"POST\",\"url\":\"/admin/access/v1/app/version/gray/{id}\"}', 4, 'COLUMN');
INSERT INTO `admin_resource` VALUES (460, '', '{updateStatus}==2', '2020-09-23 17:39:34', '版本发布', '发布', '版本发布', 'app-version-release', 'REQUEST://{\"alert\":\"您确定要正式发布吗？\",\"method\":\"POST\",\"url\":\"/admin/access/v1/app/version/release/{id}\"}', 4, 'COLUMN');
INSERT INTO `admin_resource` VALUES (461, '', '{updateStatus}==2', '2020-09-23 17:39:35', '版本失败', '失败', '版本失败', 'app-version-defeated', 'REQUEST://{\"alert\":\"您确定要失败吗？\",\"method\":\"DELETE\",\"url\":\"/admin/access/v1/app/version/defeated/{id}\"}', 4, 'COLUMN');
INSERT INTO `admin_resource` VALUES (462, NULL, NULL, '2020-09-23 17:39:35', 'APP版本列表', NULL, 'APP版本列表', 'app-version-list', 'PANEL://{\"method\":\"GET\",\"url\":\"/admin/access/v1/app/version/list\"}', 4, 'MAIN');
INSERT INTO `admin_resource` VALUES (463, '', '', '2020-09-23 17:39:35', '子菜单添加', '增加', '子菜单添加', 'admin-menu-sub-add', 'PANEL://{\"method\":\"POST\",\"url\":\"/admin/access/v1/sys/menu/sub/add\"}', 11, 'COMMON');
INSERT INTO `admin_resource` VALUES (464, '', '', '2020-09-23 17:39:35', '子菜单修改', '修改', '子菜单修改', 'admin-menu-sub-update', 'PANEL://{\"method\":\"POST\",\"url\":\"/admin/access/v1/sys/menu/sub/update/{id}\"}', 11, 'COLUMN');
INSERT INTO `admin_resource` VALUES (465, '', '', '2020-09-23 17:39:35', '子菜单删除', '删除', '子菜单删除', 'admin-menu-sub-del', 'REQUEST://{\"alert\":\"您确定删除吗？\",\"method\":\"DELETE\",\"url\":\"/admin/access/v1/sys/menu/sub/del/{id}\"}', 11, 'COLUMN');
INSERT INTO `admin_resource` VALUES (466, NULL, NULL, '2020-09-23 17:39:35', '子菜单列表', NULL, '子菜单列表', 'admin-menu-sub-query', 'PANEL://{\"method\":\"GET\",\"url\":\"/admin/access/v1/sys/menu/sub/list\"}', 11, 'MAIN');
INSERT INTO `admin_resource` VALUES (467, '', '', '2020-09-23 17:39:35', '资源添加', '增加', '资源添加', 'admin-menu-resource-add', 'PANEL://{\"method\":\"POST\",\"url\":\"/admin/access/v1/sys/menu/resource/add\"}', 10, 'COMMON');
INSERT INTO `admin_resource` VALUES (468, '', '', '2020-09-23 17:39:35', '资源重置', '重置', '资源重置', 'admin-menu-resource-reset', 'REQUEST://{\"alert\":\"这个操作很危险！将会重置所有资源，请谨慎操作！\",\"method\":\"DELETE\",\"url\":\"/admin/access/v1/sys/menu/resource/reset\"}', 10, 'COMMON');
INSERT INTO `admin_resource` VALUES (469, '', '', '2020-09-23 17:39:35', '资源加载', '加载', '资源加载', 'admin-menu-resource-load', 'REQUEST://{\"alert\":\"您确定要加载新的资源吗？\",\"method\":\"DELETE\",\"url\":\"/admin/access/v1/sys/menu/resource/load\"}', 10, 'COMMON');
INSERT INTO `admin_resource` VALUES (470, '', '', '2020-09-23 17:39:35', '资源修改', '修改', '资源修改', 'admin-menu-resource-update', 'PANEL://{\"method\":\"POST\",\"url\":\"/admin/access/v1/sys/menu/resource/update/{id}\"}', 10, 'COLUMN');
INSERT INTO `admin_resource` VALUES (471, '', '', '2020-09-23 17:39:36', '资源删除', '删除', '资源删除', 'admin-menu-resource-del', 'REQUEST://{\"alert\":\"您确定删除吗？\",\"method\":\"DELETE\",\"url\":\"/admin/access/v1/sys/menu/resource/del/{id}\"}', 10, 'COLUMN');
INSERT INTO `admin_resource` VALUES (472, NULL, NULL, '2020-09-23 17:39:36', '资源列表', NULL, '资源列表', 'admin-menu-resource-query', 'PANEL://{\"method\":\"GET\",\"url\":\"/admin/access/v1/sys/menu/resource/list\"}', 10, 'MAIN');
INSERT INTO `admin_resource` VALUES (473, '', '', '2020-09-23 17:39:36', '菜单添加', '增加', '菜单添加', 'admin-menu-add', 'PANEL://{\"method\":\"POST\",\"url\":\"/admin/access/v1/sys/menu/add\"}', 9, 'COMMON');
INSERT INTO `admin_resource` VALUES (474, '', '', '2020-09-23 17:39:36', '菜单修改', '修改', '菜单修改', 'admin-menu-update', 'PANEL://{\"method\":\"POST\",\"url\":\"/admin/access/v1/sys/menu/update/{id}\"}', 9, 'COLUMN');
INSERT INTO `admin_resource` VALUES (475, '', '', '2020-09-23 17:39:36', '菜单删除', '删除', '菜单删除', 'admin-menu-del', 'REQUEST://{\"alert\":\"您确定删除吗？\",\"method\":\"DELETE\",\"url\":\"/admin/access/v1/sys/menu/del/{id}\"}', 9, 'COLUMN');
INSERT INTO `admin_resource` VALUES (476, NULL, NULL, '2020-09-23 17:39:36', '菜单列表', NULL, '菜单列表', 'admin-menu-query', 'PANEL://{\"method\":\"GET\",\"url\":\"/admin/access/v1/sys/menu/list\"}', 9, 'MAIN');
INSERT INTO `admin_resource` VALUES (477, '', '', '2020-09-23 17:39:36', '添加用户', '增加', '添加用户', 'ADMIN_USER_CREATE', 'PANEL://{\"method\":\"PUT\",\"url\":\"/admin/access/v1/sys-user\"}', 12, 'COMMON');
INSERT INTO `admin_resource` VALUES (478, '', '', '2020-09-23 17:39:36', '更新用户信息', '修改信息', '更新用户信息', 'ADMIN_USER_EDIT', 'PANEL://{\"method\":\"POST\",\"url\":\"/admin/access/v1/sys-user/{id}\"}', 12, 'COLUMN');
INSERT INTO `admin_resource` VALUES (479, '', '', '2020-09-23 17:39:36', '重置用户密码', '重置密码', '重置用户密码', 'ADMIN_USER_PASSWORD', 'PANEL://{\"method\":\"POST\",\"url\":\"/admin/access/v1/sys-user/{id}/password\"}', 12, 'COLUMN');
INSERT INTO `admin_resource` VALUES (480, '', '{state}==1', '2020-09-23 17:39:36', '禁用用户', '禁用', '禁用用户', 'ADMIN_USER_EDIT', 'REQUEST://{\"alert\":\"您确定禁用该账号吗？\",\"method\":\"DELETE\",\"url\":\"/admin/access/v1/sys-user/disable/{id}\"}', 12, 'COLUMN');
INSERT INTO `admin_resource` VALUES (481, '', '{state}==0', '2020-09-23 17:39:37', '启用用户', '启用', '启用用户', 'ADMIN_USER_EDIT', 'REQUEST://{\"alert\":\"您确定启用该账号吗？\",\"method\":\"POST\",\"url\":\"/admin/access/v1/sys-user/enable/{id}\"}', 12, 'COLUMN');
INSERT INTO `admin_resource` VALUES (482, '', '{id}!=1', '2020-09-23 17:39:37', '删除用户', '删除', '删除用户', 'ADMIN_USER_DELETE', 'REQUEST://{\"alert\":\"您确定要删除该用户吗？\",\"method\":\"DELETE\",\"url\":\"/admin/access/v1/sys-user/{id}\"}', 12, 'COLUMN');
INSERT INTO `admin_resource` VALUES (483, NULL, NULL, '2020-09-23 17:39:37', '用户列表', NULL, '用户列表', 'ADMIN_USER_LIST', 'PANEL://{\"method\":\"GET\",\"url\":\"/admin/access/v1/sys-user/list\"}', 12, 'MAIN');
INSERT INTO `admin_resource` VALUES (484, '', '', '2020-09-23 17:39:37', '创建角色', '增加', '创建角色', 'ADMIN_ROLE_CREATE', 'PANEL://{\"method\":\"PUT\",\"url\":\"/admin/access/v1/sys-role\"}', 13, 'COMMON');
INSERT INTO `admin_resource` VALUES (485, '', '{id}!=1', '2020-09-23 17:39:37', '编辑角色', '修改权限', '编辑角色', 'ADMIN_ROLE_EDIT', 'PANEL://{\"method\":\"POST\",\"url\":\"/admin/access/v1/sys-role/{id}\"}', 13, 'COLUMN');
INSERT INTO `admin_resource` VALUES (486, '', '{id}!=1', '2020-09-23 17:39:37', '删除角色', '删除', '删除角色', 'ADMIN_ROLE_DELETE', 'REQUEST://{\"alert\":\"您确定要删除该角色吗？该操作可能会造成系统不能正常运行，请谨慎操作！\",\"method\":\"DELETE\",\"url\":\"/admin/access/v1/sys-role/{id}\"}', 13, 'COLUMN');
INSERT INTO `admin_resource` VALUES (487, NULL, NULL, '2020-09-23 17:39:37', '角色列表', NULL, '角色列表', 'ADMIN_ROLE_LIST', 'PANEL://{\"method\":\"GET\",\"url\":\"/admin/access/v1/sys-role/list\"}', 13, 'MAIN');
INSERT INTO `admin_resource` VALUES (488, NULL, NULL, '2020-09-23 17:39:37', '操作审计', NULL, '操作审计', 'ADMIN_USER_OPTION_LIST', 'PANEL://{\"method\":\"GET\",\"url\":\"/admin/access/v1/sys-user/option-log/list\"}', 14, 'MAIN');
INSERT INTO `admin_resource` VALUES (493, '', '', '2020-09-23 17:39:38', '发送MQ消息', '发送消息', '发送MQ消息', NULL, 'PANEL://{\"method\":\"POST\",\"url\":\"/admin/access/v1/system/mq-message/push\"}', 17, 'COMMON');
INSERT INTO `admin_resource` VALUES (494, '', '', '2020-09-23 17:39:38', '消息重发', '消息重发', '消息重发', NULL, 'PANEL://{\"method\":\"POST\",\"url\":\"/admin/access/v1/system/mq-message/again/{messageId}\"}', 17, 'COLUMN');
INSERT INTO `admin_resource` VALUES (495, NULL, NULL, '2020-09-23 17:39:38', 'MQ消息', NULL, 'MQ消息', 'SYSTEM-MQ-MESSAGE', 'PANEL://{\"method\":\"GET\",\"url\":\"/admin/access/v1/system/mq-message/list\"}', 17, 'MAIN');
INSERT INTO `admin_resource` VALUES (496, NULL, NULL, '2020-09-23 17:39:38', '系统运行日志', NULL, '系统运行日志', 'SYSTEM-LOG-MESSAGE', 'PANEL://{\"method\":\"GET\",\"url\":\"/admin/access/v1/system/log/list\"}', 16, 'MAIN');

-- ----------------------------
-- Table structure for admin_role
-- ----------------------------
DROP TABLE IF EXISTS `admin_role`;
CREATE TABLE `admin_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `permissions` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin_role
-- ----------------------------
INSERT INTO `admin_role` VALUES (1, '2020-07-31 20:28:12', '系统管理员', '*');

-- ----------------------------
-- Table structure for admin_sub_menu
-- ----------------------------
DROP TABLE IF EXISTS `admin_sub_menu`;
CREATE TABLE `admin_sub_menu`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `classify` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `main_role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `main_route` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `menu_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `sort` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `MAIN_ROUTE_UN_IDX`(`main_route`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin_sub_menu
-- ----------------------------
INSERT INTO `admin_sub_menu` VALUES (1, 'APP推送', '2020-07-31 20:28:13', '', 'app-upush-config', 'PANEL://{\"method\":\"POST\",\"url\":\"/admin/access/v1/app/upush/config\"}', '友盟推送配置', 50);
INSERT INTO `admin_sub_menu` VALUES (2, 'APP推送', '2020-07-31 20:28:13', '', 'app-push-list', 'PANEL://{\"method\":\"GET\",\"url\":\"/admin/access/v1/app/push/list\"}', '推送管理', 0);
INSERT INTO `admin_sub_menu` VALUES (3, 'APP推送', '2020-07-31 20:28:14', '', 'app-push-client-user-list', 'PANEL://{\"method\":\"GET\",\"url\":\"/admin/access/v1/app/push/client-user/list\"}', '客户端用户关系表', 5);
INSERT INTO `admin_sub_menu` VALUES (4, 'APP更新', '2020-07-31 20:28:14', '', 'app-version-list', 'PANEL://{\"method\":\"GET\",\"url\":\"/admin/access/v1/app/version/list\"}', 'APP版本管理', 5);
INSERT INTO `admin_sub_menu` VALUES (5, 'APP更新', '2020-07-31 20:28:15', '', 'app-patch-branch-list', 'PANEL://{\"method\":\"GET\",\"url\":\"/admin/access/v1/app-patch/branch/list\"}', '热更新分支管理', 20);
INSERT INTO `admin_sub_menu` VALUES (6, 'APP更新', '2020-07-31 20:28:15', '', 'app-patch-version-list', 'PANEL://{\"method\":\"GET\",\"url\":\"/admin/access/v1/app-patch/version/list\"}', '热更新版本管理', 10);
INSERT INTO `admin_sub_menu` VALUES (7, 'APP更新', '2020-07-31 20:28:16', '', 'app-patch-gray-user-list', 'PANEL://{\"method\":\"GET\",\"url\":\"/admin/access/v1/app-patch/gray-user/list\"}', '灰度用户管理', 30);
INSERT INTO `admin_sub_menu` VALUES (8, 'APP更新', '2020-07-31 20:28:17', '', 'app-patch-config', 'PANEL://{\"method\":\"POST\",\"url\":\"/admin/access/v1/app-patch/config\"}', '热更新配置', 50);
INSERT INTO `admin_sub_menu` VALUES (9, '菜单管理', '2020-07-31 20:28:17', '', 'admin-menu-query', 'PANEL://{\"method\":\"GET\",\"url\":\"/admin/access/v1/sys/menu/list\"}', '菜单列表', 1);
INSERT INTO `admin_sub_menu` VALUES (10, '菜单管理', '2020-07-31 20:28:17', '', 'admin-menu-resource-query', 'PANEL://{\"method\":\"GET\",\"url\":\"/admin/access/v1/sys/menu/resource/list\"}', '资源列表', 3);
INSERT INTO `admin_sub_menu` VALUES (11, '菜单管理', '2020-07-31 20:28:18', '', 'admin-menu-sub-query', 'PANEL://{\"method\":\"GET\",\"url\":\"/admin/access/v1/sys/menu/sub/list\"}', '子菜单列表', 2);
INSERT INTO `admin_sub_menu` VALUES (12, '系统用户', '2020-07-31 20:28:19', '', 'ADMIN_USER_LIST', 'PANEL://{\"method\":\"GET\",\"url\":\"/admin/access/v1/sys-user/list\"}', '用户管理', -2147483648);
INSERT INTO `admin_sub_menu` VALUES (13, '系统用户', '2020-07-31 20:28:19', '', 'ADMIN_ROLE_LIST', 'PANEL://{\"method\":\"GET\",\"url\":\"/admin/access/v1/sys-role/list\"}', '角色管理', 0);
INSERT INTO `admin_sub_menu` VALUES (14, '系统用户', '2020-07-31 20:28:20', '', 'ADMIN_USER_OPTION_LIST', 'PANEL://{\"method\":\"GET\",\"url\":\"/admin/access/v1/sys-user/option-log/list\"}', '操作审计', 1);
INSERT INTO `admin_sub_menu` VALUES (16, '系统监测', '2020-07-31 20:28:21', '', 'SYSTEM-LOG-MESSAGE', 'PANEL://{\"method\":\"GET\",\"url\":\"/admin/access/v1/system/log/list\"}', '系统运行日志', 2147483647);
INSERT INTO `admin_sub_menu` VALUES (17, '系统监测', '2020-07-31 20:28:21', '', 'SYSTEM-MQ-MESSAGE', 'PANEL://{\"method\":\"GET\",\"url\":\"/admin/access/v1/system/mq-message/list\"}', 'MQ消息记录', 2147483647);

-- ----------------------------
-- Table structure for admin_user
-- ----------------------------
DROP TABLE IF EXISTS `admin_user`;
CREATE TABLE `admin_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `roles` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `state` int(11) NULL DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user`(`username`(191)) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin_user
-- ----------------------------
INSERT INTO `admin_user` VALUES (1, '2020-07-31 20:28:12', 'admin', '$2a$10$tfSvf.lsrVaMisPH1dmkYeicwBGHMfJVfzvAjf/6fDnXInPD5t1E.', '1', 1, 'admin');

-- ----------------------------
-- Table structure for doc_mock_template
-- ----------------------------
DROP TABLE IF EXISTS `doc_mock_template`;
CREATE TABLE `doc_mock_template`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `api_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `http_method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `status` int(11) NULL DEFAULT NULL,
  `template` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for log_message
-- ----------------------------
DROP TABLE IF EXISTS `log_message`;
CREATE TABLE `log_message`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `level` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `logger_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `message` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `thread_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `time_stamp` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `TIME_INDEX`(`time_stamp`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 58 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of log_message
-- ----------------------------
INSERT INTO `log_message` VALUES (1, '192.168.56.1', 'INFO', 'cn.bluetech.gragasotFrameworkApplication', 'Starting BootFrameworkApplication on PC-201908191007 with PID 640 (started by Administrator in F:\\项目\\elise)', 'main', 1600854413601);
INSERT INTO `log_message` VALUES (2, '192.168.56.1', 'INFO', 'cn.bluetech.gragasotFrameworkApplication', 'The following profiles are active: dev', 'main', 1600854413782);
INSERT INTO `log_message` VALUES (3, '192.168.56.1', 'INFO', 'org.springframework.data.repository.config.RepositoryConfigurationDelegate', 'Multiple Spring Data modules found, entering strict repository configuration mode!', 'main', 1600854415448);
INSERT INTO `log_message` VALUES (4, '192.168.56.1', 'INFO', 'org.springframework.data.repository.config.RepositoryConfigurationDelegate', 'Bootstrapping Spring Data repositories in DEFAULT mode.', 'main', 1600854415448);
INSERT INTO `log_message` VALUES (5, '192.168.56.1', 'INFO', 'org.springframework.data.repository.config.RepositoryConfigurationExtensionSupport', 'Spring Data JPA - Could not safely identify store assignment for repository candidate interface com.brframework.commondb.core.CommonRepository.', 'main', 1600854415758);
INSERT INTO `log_message` VALUES (6, '192.168.56.1', 'INFO', 'org.springframework.data.repository.config.RepositoryConfigurationDelegate', 'Finished Spring Data repository scanning in 432ms. Found 17 repository interfaces.', 'main', 1600854415896);
INSERT INTO `log_message` VALUES (7, '192.168.56.1', 'INFO', 'org.springframework.data.repository.config.RepositoryConfigurationDelegate', 'Multiple Spring Data modules found, entering strict repository configuration mode!', 'main', 1600854416242);
INSERT INTO `log_message` VALUES (8, '192.168.56.1', 'INFO', 'org.springframework.data.repository.config.RepositoryConfigurationDelegate', 'Bootstrapping Spring Data repositories in DEFAULT mode.', 'main', 1600854416245);
INSERT INTO `log_message` VALUES (9, '192.168.56.1', 'INFO', 'org.springframework.data.repository.config.RepositoryConfigurationExtensionSupport', 'Spring Data Redis - Could not safely identify store assignment for repository candidate interface cn.bluetech.gragaso.car.BrandDao.', 'main', 1600854416291);
INSERT INTO `log_message` VALUES (10, '192.168.56.1', 'INFO', 'org.springframework.data.repository.config.RepositoryConfigurationDelegate', 'Finished Spring Data repository scanning in 29ms. Found 0 repository interfaces.', 'main', 1600854416292);
INSERT INTO `log_message` VALUES (11, '192.168.56.1', 'INFO', 'org.springframework.context.support.PostProcessorRegistrationDelegate$BeanPostProcessorChecker', 'Bean \'org.springframework.transaction.annotation.ProxyTransactionManagementConfiguration\' of type [org.springframework.transaction.annotation.ProxyTransactionManagementConfiguration$$EnhancerBySpringCGLIB$$5f6863a1] is not eligible for getting processed by all BeanPostProcessors (for example: not eligible for auto-proxying)', 'main', 1600854416987);
INSERT INTO `log_message` VALUES (12, '192.168.56.1', 'INFO', 'org.springframework.context.support.PostProcessorRegistrationDelegate$BeanPostProcessorChecker', 'Bean \'org.springframework.security.config.annotation.configuration.ObjectPostProcessorConfiguration\' of type [org.springframework.security.config.annotation.configuration.ObjectPostProcessorConfiguration$$EnhancerBySpringCGLIB$$f8162bdb] is not eligible for getting processed by all BeanPostProcessors (for example: not eligible for auto-proxying)', 'main', 1600854417374);
INSERT INTO `log_message` VALUES (13, '192.168.56.1', 'INFO', 'org.springframework.context.support.PostProcessorRegistrationDelegate$BeanPostProcessorChecker', 'Bean \'objectPostProcessor\' of type [org.springframework.security.config.annotation.configuration.AutowireBeanFactoryObjectPostProcessor] is not eligible for getting processed by all BeanPostProcessors (for example: not eligible for auto-proxying)', 'main', 1600854417385);
INSERT INTO `log_message` VALUES (14, '192.168.56.1', 'INFO', 'org.springframework.context.support.PostProcessorRegistrationDelegate$BeanPostProcessorChecker', 'Bean \'org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler@1e495414\' of type [org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler] is not eligible for getting processed by all BeanPostProcessors (for example: not eligible for auto-proxying)', 'main', 1600854417390);
INSERT INTO `log_message` VALUES (15, '192.168.56.1', 'INFO', 'org.springframework.context.support.PostProcessorRegistrationDelegate$BeanPostProcessorChecker', 'Bean \'methodSecurityConfig\' of type [com.brframework.commonsecurity.MethodSecurityConfig$$EnhancerBySpringCGLIB$$ef4c125f] is not eligible for getting processed by all BeanPostProcessors (for example: not eligible for auto-proxying)', 'main', 1600854417392);
INSERT INTO `log_message` VALUES (16, '192.168.56.1', 'INFO', 'org.springframework.context.support.PostProcessorRegistrationDelegate$BeanPostProcessorChecker', 'Bean \'methodSecurityMetadataSource\' of type [org.springframework.security.access.method.DelegatingMethodSecurityMetadataSource] is not eligible for getting processed by all BeanPostProcessors (for example: not eligible for auto-proxying)', 'main', 1600854417405);
INSERT INTO `log_message` VALUES (17, '192.168.56.1', 'INFO', 'org.springframework.boot.web.embedded.tomcat.TomcatWebServer', 'Tomcat initialized with port(s): 8080 (http)', 'main', 1600854417923);
INSERT INTO `log_message` VALUES (18, '192.168.56.1', 'INFO', 'org.apache.coyote.http11.Http11NioProtocol', 'Initializing ProtocolHandler [\"http-nio-8080\"]', 'main', 1600854417954);
INSERT INTO `log_message` VALUES (19, '192.168.56.1', 'INFO', 'org.apache.catalina.core.StandardService', 'Starting service [Tomcat]', 'main', 1600854417967);
INSERT INTO `log_message` VALUES (20, '192.168.56.1', 'INFO', 'org.apache.catalina.core.StandardEngine', 'Starting Servlet engine: [Apache Tomcat/9.0.22]', 'main', 1600854417968);
INSERT INTO `log_message` VALUES (21, '192.168.56.1', 'INFO', 'org.apache.catalina.core.ContainerBase.[Tomcat].[localhost].[/]', 'Initializing Spring embedded WebApplicationContext', 'main', 1600854418208);
INSERT INTO `log_message` VALUES (22, '192.168.56.1', 'INFO', 'org.springframework.web.context.ContextLoader', 'Root WebApplicationContext: initialization completed in 4337 ms', 'main', 1600854418209);
INSERT INTO `log_message` VALUES (23, '192.168.56.1', 'WARN', 'org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration$JpaWebConfiguration$JpaWebMvcConfiguration', 'spring.jpa.open-in-view is enabled by default. Therefore, database queries may be performed during view rendering. Explicitly configure spring.jpa.open-in-view to disable this warning', 'main', 1600854418390);
INSERT INTO `log_message` VALUES (24, '192.168.56.1', 'INFO', 'org.hibernate.jpa.internal.util.LogHelper', 'HHH000204: Processing PersistenceUnitInfo [\n	name: default\n	...]', 'main', 1600854418706);
INSERT INTO `log_message` VALUES (25, '192.168.56.1', 'INFO', 'org.hibernate.Version', 'HHH000412: Hibernate Core {5.3.10.Final}', 'main', 1600854418858);
INSERT INTO `log_message` VALUES (26, '192.168.56.1', 'INFO', 'org.hibernate.cfg.Environment', 'HHH000206: hibernate.properties not found', 'main', 1600854418865);
INSERT INTO `log_message` VALUES (27, '192.168.56.1', 'INFO', 'org.hibernate.annotations.common.Version', 'HCANN000001: Hibernate Commons Annotations {5.0.4.Final}', 'main', 1600854419193);
INSERT INTO `log_message` VALUES (28, '192.168.56.1', 'INFO', 'com.zaxxer.hikari.HikariDataSource', 'HikariPool-1 - Starting...', 'main', 1600854419498);
INSERT INTO `log_message` VALUES (29, '192.168.56.1', 'INFO', 'com.zaxxer.hikari.HikariDataSource', 'HikariPool-1 - Start completed.', 'main', 1600854420074);
INSERT INTO `log_message` VALUES (30, '192.168.56.1', 'INFO', 'org.hibernate.dialect.Dialect', 'HHH000400: Using dialect: cn.bluetech.gragasils.ExpandMySQL5Dialect', 'main', 1600854420113);
INSERT INTO `log_message` VALUES (31, '192.168.56.1', 'INFO', 'org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean', 'Initialized JPA EntityManagerFactory for persistence unit \'default\'', 'main', 1600854422907);
INSERT INTO `log_message` VALUES (32, '192.168.56.1', 'INFO', 'org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor', 'Initializing ExecutorService \'applicationTaskExecutor\'', 'main', 1600854424546);
INSERT INTO `log_message` VALUES (33, '192.168.56.1', 'INFO', 'org.redisson.Version', 'Redisson 3.11.3', 'main', 1600854426266);
INSERT INTO `log_message` VALUES (34, '192.168.56.1', 'INFO', 'org.redisson.connection.pool.MasterPubSubConnectionPool', '1 connections initialized for r-bp1upco8p4uv383gctpd.redis.rds.aliyuncs.com/47.110.113.44:6379', 'redisson-netty-4-21', 1600854427125);
INSERT INTO `log_message` VALUES (35, '192.168.56.1', 'INFO', 'org.redisson.connection.pool.MasterConnectionPool', '24 connections initialized for r-bp1upco8p4uv383gctpd.redis.rds.aliyuncs.com/47.110.113.44:6379', 'redisson-netty-4-6', 1600854427136);
INSERT INTO `log_message` VALUES (36, '192.168.56.1', 'INFO', 'com.brframework.commonmq.core.RedissonMQClient', 'mq initialize ......', 'main', 1600854427294);
INSERT INTO `log_message` VALUES (37, '192.168.56.1', 'INFO', 'org.springframework.security.web.DefaultSecurityFilterChain', 'Creating filter chain: Ant [pattern=\'/admin/access/**\'], [org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter@48f6f272, org.springframework.security.web.context.SecurityContextPersistenceFilter@12ef8410, com.brframework.commonsecurity.SecurityFilter@2fd2a6a4, org.springframework.security.web.authentication.www.BasicAuthenticationFilter@fed510f, org.springframework.security.web.savedrequest.RequestCacheAwareFilter@71bebddf, org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter@30f929ff, org.springframework.security.web.authentication.AnonymousAuthenticationFilter@401b7109, org.springframework.security.web.session.SessionManagementFilter@6508e82, org.springframework.security.web.access.ExceptionTranslationFilter@5d237827]', 'main', 1600854428180);
INSERT INTO `log_message` VALUES (38, '192.168.56.1', 'INFO', 'org.springframework.security.web.DefaultSecurityFilterChain', 'Creating filter chain: Ant [pattern=\'/api/access/**\'], [org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter@57352624, org.springframework.security.web.context.SecurityContextPersistenceFilter@1d8f835c, com.brframework.commonsecurity.SecurityFilter@5ef22587, org.springframework.security.web.authentication.www.BasicAuthenticationFilter@5a816a78, org.springframework.security.web.savedrequest.RequestCacheAwareFilter@45c5cd9a, org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter@858bf8c, org.springframework.security.web.authentication.AnonymousAuthenticationFilter@e206be5, org.springframework.security.web.session.SessionManagementFilter@7af85b32, org.springframework.security.web.access.ExceptionTranslationFilter@4d001a14, org.springframework.security.web.access.intercept.FilterSecurityInterceptor@18791f7b]', 'main', 1600854428198);
INSERT INTO `log_message` VALUES (39, '192.168.56.1', 'INFO', 'org.springframework.security.web.DefaultSecurityFilterChain', 'Creating filter chain: any request, [org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter@292d1705, org.springframework.security.web.context.SecurityContextPersistenceFilter@3b191676, org.springframework.security.web.authentication.www.BasicAuthenticationFilter@5aac9d67, org.springframework.security.web.savedrequest.RequestCacheAwareFilter@75b05ec7, org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter@31d96cd6, org.springframework.security.web.authentication.AnonymousAuthenticationFilter@70796b21, org.springframework.security.web.session.SessionManagementFilter@5145dddc, org.springframework.security.web.access.ExceptionTranslationFilter@65a42136, org.springframework.security.web.access.intercept.FilterSecurityInterceptor@6f1ba660]', 'main', 1600854428203);
INSERT INTO `log_message` VALUES (40, '192.168.56.1', 'INFO', 'springfox.documentation.spring.web.PropertySourcedRequestMappingHandlerMapping', 'Mapped URL path [/v2/api-docs] onto method [public org.springframework.http.ResponseEntity<springfox.documentation.spring.web.json.Json> springfox.documentation.swagger2.web.Swagger2Controller.getDocumentation(java.lang.String,javax.servlet.http.HttpServletRequest)]', 'main', 1600854428248);
INSERT INTO `log_message` VALUES (41, '192.168.56.1', 'INFO', 'org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler', 'Initializing ExecutorService \'taskScheduler\'', 'main', 1600854428828);
INSERT INTO `log_message` VALUES (42, '192.168.56.1', 'INFO', 'springfox.documentation.spring.web.plugins.DocumentationPluginsBootstrapper', 'Context refreshed', 'main', 1600854428906);
INSERT INTO `log_message` VALUES (43, '192.168.56.1', 'INFO', 'springfox.documentation.spring.web.plugins.DocumentationPluginsBootstrapper', 'Found 5 custom documentation plugin(s)', 'main', 1600854428923);
INSERT INTO `log_message` VALUES (44, '192.168.56.1', 'INFO', 'springfox.documentation.spring.web.scanners.ApiListingReferenceScanner', 'Scanning for api listing references', 'main', 1600854429000);
INSERT INTO `log_message` VALUES (45, '192.168.56.1', 'INFO', 'springfox.documentation.spring.web.scanners.ApiListingReferenceScanner', 'Scanning for api listing references', 'main', 1600854429048);
INSERT INTO `log_message` VALUES (46, '192.168.56.1', 'INFO', 'springfox.documentation.spring.web.scanners.ApiListingReferenceScanner', 'Scanning for api listing references', 'main', 1600854429486);
INSERT INTO `log_message` VALUES (47, '192.168.56.1', 'INFO', 'springfox.documentation.spring.web.scanners.ApiListingReferenceScanner', 'Scanning for api listing references', 'main', 1600854429493);
INSERT INTO `log_message` VALUES (48, '192.168.56.1', 'INFO', 'springfox.documentation.spring.web.scanners.ApiListingReferenceScanner', 'Scanning for api listing references', 'main', 1600854429528);
INSERT INTO `log_message` VALUES (49, '192.168.56.1', 'INFO', 'org.apache.coyote.http11.Http11NioProtocol', 'Starting ProtocolHandler [\"http-nio-8080\"]', 'main', 1600854429550);
INSERT INTO `log_message` VALUES (50, '192.168.56.1', 'INFO', 'org.springframework.boot.web.embedded.tomcat.TomcatWebServer', 'Tomcat started on port(s): 8080 (http) with context path \'\'', 'main', 1600854429584);
INSERT INTO `log_message` VALUES (51, '192.168.56.1', 'INFO', 'cn.bluetech.gragasotFrameworkApplication', 'Started BootFrameworkApplication in 16.971 seconds (JVM running for 18.471)', 'main', 1600854429587);
INSERT INTO `log_message` VALUES (52, '192.168.56.1', 'INFO', 'org.apache.catalina.core.ContainerBase.[Tomcat].[localhost].[/]', 'Initializing Spring DispatcherServlet \'dispatcherServlet\'', 'http-nio-8080-exec-1', 1600854553635);
INSERT INTO `log_message` VALUES (53, '192.168.56.1', 'INFO', 'org.springframework.web.servlet.DispatcherServlet', 'Initializing Servlet \'dispatcherServlet\'', 'http-nio-8080-exec-1', 1600854553636);
INSERT INTO `log_message` VALUES (54, '192.168.56.1', 'INFO', 'org.springframework.web.servlet.DispatcherServlet', 'Completed initialization in 21 ms', 'http-nio-8080-exec-1', 1600854553658);
INSERT INTO `log_message` VALUES (55, '192.168.56.1', 'INFO', 'org.hibernate.hql.internal.QueryTranslatorFactoryInitiator', 'HHH000397: Using ASTQueryTranslatorFactory', 'http-nio-8080-exec-2', 1600854553743);
INSERT INTO `log_message` VALUES (56, '192.168.56.1', 'INFO', 'io.lettuce.core.EpollProvider', 'Starting without optional epoll library', 'http-nio-8080-exec-2', 1600854555721);
INSERT INTO `log_message` VALUES (57, '192.168.56.1', 'INFO', 'io.lettuce.core.KqueueProvider', 'Starting without optional kqueue library', 'http-nio-8080-exec-2', 1600854555729);

-- ----------------------------
-- Table structure for mq_log
-- ----------------------------
DROP TABLE IF EXISTS `mq_log`;
CREATE TABLE `mq_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `defeated_info` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `delayed_seconds` bigint(20) NULL DEFAULT NULL,
  `message` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `message_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `push_info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `push_time` bigint(20) NULL DEFAULT NULL,
  `running_end_time` bigint(20) NULL DEFAULT NULL,
  `running_info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `running_start_time` bigint(20) NULL DEFAULT NULL,
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `topic` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `MESSAGE_ID_UNIDX`(`message_id`) USING BTREE,
  INDEX `RUNNING_START_TIME_IDX`(`running_start_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pus_client_info
-- ----------------------------
DROP TABLE IF EXISTS `pus_client_info`;
CREATE TABLE `pus_client_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime(0) NOT NULL,
  `device_tokens` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `user_tokens` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pus_log
-- ----------------------------
DROP TABLE IF EXISTS `pus_log`;
CREATE TABLE `pus_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `device_tokens` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `error_info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `message_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `message_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `os` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `status` int(11) NULL DEFAULT NULL,
  `text` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `uri` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `user_tokens` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_app_version
-- ----------------------------
DROP TABLE IF EXISTS `sys_app_version`;
CREATE TABLE `sys_app_version`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `file_size` bigint(20) NOT NULL,
  `os` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `update_status` int(11) NULL DEFAULT NULL,
  `update_type` int(11) NULL DEFAULT NULL,
  `version` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `version_message` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `version_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `version_url` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_dictionary
-- ----------------------------
DROP TABLE IF EXISTS `sys_dictionary`;
CREATE TABLE `sys_dictionary`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `_key` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `update_date` datetime(0) NULL DEFAULT NULL,
  `value` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `DICTIONARY_KEY`(`_key`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_patch_branch
-- ----------------------------
DROP TABLE IF EXISTS `sys_patch_branch`;
CREATE TABLE `sys_patch_branch`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `base_package_size` bigint(20) NULL DEFAULT NULL,
  `base_package_update_date` datetime(0) NULL DEFAULT NULL,
  `base_package_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `branch_detail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `branch_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `os` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_patch_gray_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_patch_gray_user`;
CREATE TABLE `sys_patch_gray_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_patch_version
-- ----------------------------
DROP TABLE IF EXISTS `sys_patch_version`;
CREATE TABLE `sys_patch_version`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `assets_hash` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `branch` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `bundle_hash` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `file_size` bigint(20) NOT NULL,
  `hash` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `os` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `patch_file_size` bigint(20) NOT NULL,
  `patch_hash` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `patch_url` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `update_status` int(11) NULL DEFAULT NULL,
  `update_type` int(11) NULL DEFAULT NULL,
  `version` int(11) NULL DEFAULT NULL,
  `version_message` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `version_url` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
