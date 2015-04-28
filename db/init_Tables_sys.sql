
USE pkqfirewall;

-- ----------------------------
-- Records of sys_access_control
-- ----------------------------
INSERT INTO sys_access_control VALUES ('1', '1', '1', '10000');
INSERT INTO sys_access_control VALUES ('2', '1', '1', '10001');
INSERT INTO sys_access_control VALUES ('3', '1', '1', '20000');
INSERT INTO sys_access_control VALUES ('4', '1', '1', '20001');
INSERT INTO sys_access_control VALUES ('5', '1', '1', '20002');
INSERT INTO sys_access_control VALUES ('6', '1', '1', '20003');
INSERT INTO sys_access_control VALUES ('7', '1', '1', '20004');
INSERT INTO sys_access_control VALUES ('8', '1', '1', '30000');
INSERT INTO sys_access_control VALUES ('9', '1', '1', '30001');
INSERT INTO sys_access_control VALUES ('10', '1', '1', '30002');
INSERT INTO sys_access_control VALUES ('11', '1', '1', '10002');


-- ----------------------------
-- Records of sys_config
-- ----------------------------

INSERT INTO sys_config VALUES ('100000', '1', 'LINUX', '2014-05-08 20:39:02', '设备类型');
INSERT INTO sys_config VALUES ('100000', '2', 'WINDOWS', '2014-05-08 20:39:02', '设备类型');
INSERT INTO sys_config VALUES ('100000', '3', 'HP-UNIX', '2014-05-08 20:39:02', '设备类型');
INSERT INTO sys_config VALUES ('100000', '4', 'SOLARIS', '2014-05-08 20:39:02', '设备类型');
INSERT INTO sys_config VALUES ('100000', '5', 'AIX', '2014-05-08 20:39:02', '设备类型');
INSERT INTO sys_config VALUES ('100004', '2', '账号操作', '2014-05-08 20:39:03', '操作业务');
INSERT INTO sys_config VALUES ('100004', '3', '设备操作', '2014-05-08 20:39:03', '操作业务');
INSERT INTO sys_config VALUES ('100004', '4', '资源操作', '2014-05-08 20:39:03', '操作业务');
INSERT INTO sys_config VALUES ('100004', '5', '检测行为操作', '2014-05-08 20:39:03', '操作业务');
INSERT INTO sys_config VALUES ('100004', '6', '检测结果操作', '2014-05-08 20:39:03', '操作业务');
INSERT INTO sys_config VALUES ('100005', '0', '失败', '2014-05-08 20:39:03', '操作结果');
INSERT INTO sys_config VALUES ('100005', '1', '成功', '2014-05-08 20:39:03', '操作结果');
INSERT INTO sys_config VALUES ('100006', '0', '登录', '2014-05-08 20:39:03', '操作业务');
INSERT INTO sys_config VALUES ('100006', '1', '登出', '2014-05-08 20:39:03', '操作业务');

INSERT INTO sys_config(config_group_id,config_id,config_value,insert_time,comments) VALUES ('200001', '1', 'REDHAT', now(), '设备类型-LINUX');
INSERT INTO sys_config(config_group_id,config_id,config_value,insert_time,comments) VALUES ('200001', '2', 'SUSE', now(), '设备类型-LINUX');
INSERT INTO sys_config(config_group_id,config_id,config_value,insert_time,comments) VALUES ('200001', '3', 'DEBIAN', now(), '设备类型-LINUX');
INSERT INTO sys_config(config_group_id,config_id,config_value,insert_time,comments) VALUES ('200001', '4', 'UBUNTU', now(), '设备类型-LINUX');
INSERT INTO sys_config(config_group_id,config_id,config_value,insert_time,comments) VALUES ('200002', '1', 'SERVER2008', now(), '设备类型-WINDOWS');
INSERT INTO sys_config(config_group_id,config_id,config_value,insert_time,comments) VALUES ('200002', '2', 'SERVER2003', now(), '设备类型-WINDOWS');
INSERT INTO sys_config(config_group_id,config_id,config_value,insert_time,comments) VALUES ('200002', '3', 'WIN8', now(), '设备类型-WINDOWS');
INSERT INTO sys_config(config_group_id,config_id,config_value,insert_time,comments) VALUES ('200002', '4', 'WIN7', now(), '设备类型-WINDOWS');
INSERT INTO sys_config(config_group_id,config_id,config_value,insert_time,comments) VALUES ('200002', '5', 'XP', now(), '设备类型-WINDOWS');
INSERT INTO sys_config(config_group_id,config_id,config_value,insert_time,comments) VALUES ('200005', '1', 'AIX', now(), '设备类型-AIX');
INSERT INTO sys_config(config_group_id,config_id,config_value,insert_time,comments) VALUES ('200003', '1', 'HP-UNIX', now(), '设备类型-HP-UNIX');
INSERT INTO sys_config(config_group_id,config_id,config_value,insert_time,comments) VALUES ('200004', '1', 'SOLARIS', now(), '设备类型-SOLARIS');

-- ----------------------------
-- Records of sys_function
-- ----------------------------
INSERT INTO sys_function VALUES ('10000', '基础功能', '0', '0', 'menu1000', '0', '', '1', '1', null, null);
INSERT INTO sys_function VALUES ('10001', '账号管理', '10000', '0', 'menu1001', '1', '/operator/index.action', '1', '1', null, null);
INSERT INTO sys_function VALUES ('10002', '权限管理', '10000', '0', 'menu1001', '1', '/system/privilege.action', '2', '1', null, null);
INSERT INTO sys_function VALUES ('20000', '业务功能', '0', '1', 'menu1000', '0', null, '2', '1', null, null);
INSERT INTO sys_function VALUES ('20001', '设备管理', '20000', '1', 'menu1001', '1', '/device/index.action', '1', '1', null, null);
INSERT INTO sys_function VALUES ('30000', '日志查询', '0', '0', 'menu1000', '0', null, '3', '1', null, null);
INSERT INTO sys_function VALUES ('30001', '登录日志', '30000', '1', 'menu1001', '1', '/log/loginIndex.action', '1', '1', null, null);
INSERT INTO sys_function VALUES ('30002', '操作日志', '30000', '1', 'menu1001', '1', '/log/transIndex.action', '2', '1', null, null);

-- ----------------------------
-- Records of sys_operator
-- ----------------------------
INSERT INTO sys_operator VALUES ('1', 'admin', '超级管理员', '', '', 'qjWdoH2lE6g=', '', '1', '2014-05-09 20:49:14', '2014-05-05 14:14:56');

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO sys_role VALUES ('1', '超级管理员', '2014-05-08 20:58:53', null);
INSERT INTO sys_role VALUES ('2', '平台管理员', '2014-05-08 20:58:47', null);
INSERT INTO sys_role VALUES ('3', '主机管理员', '2014-05-08 20:59:18', null);

-- ----------------------------
-- Records of sys_operator_role
-- ----------------------------
INSERT INTO sys_operator_role VALUES ('1', '1', '1');


