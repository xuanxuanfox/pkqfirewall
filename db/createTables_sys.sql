/*
SQLyog Enterprise - MySQL GUI v6.03
Host - 5.1.53-community : Database - pkqfirewall
*********************************************************************
Server version : 5.1.53-community
*/

create database if not exists `pkqfirewall`;

USE `pkqfirewall`;


/*Table structure for table `sys_access_control` */

DROP TABLE IF EXISTS `sys_access_control`;

CREATE TABLE `sys_access_control` (
  `sid` bigint(20) NOT NULL AUTO_INCREMENT,
  `access_id` bigint(20) DEFAULT NULL,
  `access_type` int(1) DEFAULT NULL,
  `function_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='系统功能权限表';

/*Data for the table `sys_access_control` */

DROP TABLE IF EXISTS `sys_config`;

CREATE TABLE `sys_config` (
  `config_group_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `config_id` bigint(15) NOT NULL,
  `config_value` varchar(255) DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  `comments` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`config_group_id`,`config_id`)
) ENGINE=InnoDB AUTO_INCREMENT=200006 DEFAULT CHARSET=utf8 COMMENT='系统字典表';


/*Table structure for table `sys_function` */

DROP TABLE IF EXISTS `sys_function`;

CREATE TABLE `sys_function` (
  `function_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `function_name` varchar(64) DEFAULT NULL,
  `parent_function_id` bigint(20) DEFAULT NULL,
  `group_id` int(2) DEFAULT NULL,
  `function_class` varchar(64) DEFAULT NULL,
  `type` int(1) DEFAULT NULL,
  `href` varchar(255) DEFAULT NULL,
  `function_index` int(2) DEFAULT NULL,
  `status` int(1) DEFAULT NULL,
  `comments` varchar(255) DEFAULT NULL,
  `title` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`function_id`)
) ENGINE=InnoDB AUTO_INCREMENT=30003 DEFAULT CHARSET=utf8 COMMENT='系统功能表';

/*Table structure for table `sys_login_logs` */

DROP TABLE IF EXISTS `sys_login_logs`;

CREATE TABLE `sys_login_logs` (
  `log_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `operator_id` bigint(20) DEFAULT NULL,
  `trans_type` int(4) DEFAULT NULL,
  `trans_result` int(2) DEFAULT NULL,
  `trans_details` varchar(2000) DEFAULT NULL,
  `trans_time` datetime DEFAULT NULL,
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1389 DEFAULT CHARSET=utf8 COMMENT='系统登陆日志表';


/*Table structure for table `sys_logs` */

DROP TABLE IF EXISTS `sys_logs`;

CREATE TABLE `sys_logs` (
  `log_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `operator_id` bigint(20) DEFAULT NULL,
  `trans_type` int(4) DEFAULT NULL,
  `trans_result` int(2) DEFAULT NULL,
  `trans_sub_type` varchar(256) DEFAULT NULL,
  `trans_details` varchar(2000) DEFAULT NULL,
  `trans_time` datetime DEFAULT NULL,
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='系统操作日志表';


/*Table structure for table `sys_operator` */

DROP TABLE IF EXISTS `sys_operator`;

CREATE TABLE `sys_operator` (
  `operator_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `operator_name` varchar(64) DEFAULT NULL,
  `operator_realname` varchar(64) DEFAULT NULL,
  `mobile` varchar(64) DEFAULT NULL,
  `email` varchar(64) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `comments` varchar(255) DEFAULT NULL,
  `status` int(1) DEFAULT NULL,
  `last_logintime` datetime DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`operator_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='账号表';


/*Table structure for table `sys_operator_role` */

DROP TABLE IF EXISTS `sys_operator_role`;

CREATE TABLE `sys_operator_role` (
  `sid` bigint(20) NOT NULL AUTO_INCREMENT,
  `operator_id` bigint(20) DEFAULT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='系统人员角色表';


/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(64) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `comments` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='系统角色表';

