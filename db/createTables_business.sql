create database if not exists `pkqfirewall`;

USE `pkqfirewall`;


DROP TABLE IF EXISTS `device`;

CREATE TABLE `device` (
  `id` varchar(50) NOT NULL,
  `ip` varchar(50) DEFAULT NULL COMMENT '设备IP',
  `main_type` varchar(50) DEFAULT NULL COMMENT '设备一级类型',
  `type` varchar(50) DEFAULT NULL COMMENT '设备类型',
  `firewall_type` int(11) DEFAULT NULL COMMENT '防火墙类型id：iptables、windows firewall',
  `firewall_version` varchar(50) DEFAULT NULL COMMENT '防火墙版本',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备';

/*Table structure for table `frequently_rule` */

DROP TABLE IF EXISTS `frequently_rule`;

CREATE TABLE `frequently_rule` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `firewall_type` int(11) DEFAULT NULL COMMENT '防火墙类型id：iptables、windows firewall',
  `firewall_version` varchar(50) DEFAULT NULL COMMENT '防火墙版本',
  `content` int(11) DEFAULT NULL COMMENT '策略内容',
  `desc` varchar(100) DEFAULT NULL COMMENT '描述',
  `status` tinyint(4) DEFAULT NULL COMMENT '新建、已删除',
  `createtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='常用策略';

/*Table structure for table `rule` */

DROP TABLE IF EXISTS `rule`;

CREATE TABLE `rule` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `direction` varchar(20) DEFAULT NULL COMMENT 'inbound or outbound',
  `action` varchar(20) DEFAULT NULL COMMENT 'allow or deny',
  `protocol` varchar(20) DEFAULT NULL COMMENT 'tcp or udp',
  `port` varchar(10) DEFAULT NULL COMMENT '本地端口号',
  `remote_ip` varchar(50) DEFAULT NULL COMMENT '远端ip',
  `remote_port` varchar(10) DEFAULT NULL COMMENT '远端端口号',
  `content` varchar(255) DEFAULT NULL COMMENT '拼接后的策略命令',
  `deviceip` varchar(50) DEFAULT NULL COMMENT '要下发的设备ip',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态：新建、已下发、已删除',
  `createtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='策略';

/*Table structure for table `rules_log` */

DROP TABLE IF EXISTS `rules_log`;

CREATE TABLE `rules_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `deviceip` varchar(15) DEFAULT NULL COMMENT '下发的设备ip',
  `rule_content` varchar(255) DEFAULT NULL COMMENT '策略内容',
  `result_code` tinyint(4) DEFAULT NULL COMMENT '下发结果码',
  `result_message` varchar(255) DEFAULT NULL COMMENT '下发结果描述',
  `operator` varchar(50) DEFAULT NULL COMMENT '操作员账号',
  `createtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='策略设置日志';



DROP TABLE IF EXISTS `agentVersion`;

CREATE TABLE `agentVersion` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ostype` varchar(15) DEFAULT NULL COMMENT '操作系统类型',
  `versionIndex` varchar(255) DEFAULT NULL COMMENT '版本索引',
  `version` tinyint(4) DEFAULT NULL COMMENT '版本好',
  downUrl varchar(256) DEFAULT NULL COMMENT '下载地址',
  `flag` varchar(10) DEFAULT 'new' COMMENT '标识',
  `createtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='代理版本表';

