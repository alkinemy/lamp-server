USE `lamp`;

DROP TABLE IF EXISTS `lamp_target_server`;
DROP TABLE IF EXISTS `lamp_agent`;

CREATE TABLE `lamp_target_server` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL,
  `description` varchar(200) DEFAULT NULL,
  `hostname` varchar(100) NOT NULL,
  `address` varchar(100) NOT NULL,
  `auth_type` varchar(50) DEFAULT NULL,
  `username` varchar(100) DEFAULT NULL,
  `encrypted_password` varchar(100) DEFAULT NULL,
  `agent_installed` tinyint(1) NOT NULL,
  `agent_path` varchar(100) NOT NULL,
  `monitor` tinyint(1) NOT NULL,
  `monitor_interval` bigint(20) DEFAULT '0',
  `created_by` varchar(50) NOT NULL,
  `created_date` datetime NOT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` datetime,
  PRIMARY KEY (`id`),
  UNIQUE KEY `lamp_target_server_uk_01` (`hostname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `lamp_agent` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `type` varchar(100) DEFAULT NULL,
  `version` varchar(50) DEFAULT NULL,
  `target_server_id` bigint(20) NOT NULL,
  `protocol` varchar(100) DEFAULT NULL,
  `hostname` varchar(100) NOT NULL,
  `address` varchar(100) NOT NULL,
  `port` int NOT NULL,
  `secret_key` varchar(100) DEFAULT NULL,
  `agent_time` datetime DEFAULT NULL,
  `health_path` varchar(100) NOT NULL,
  `metrics_path` varchar(100) NOT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` datetime NOT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` datetime,
  PRIMARY KEY (`id`),
  UNIQUE KEY `lamp_agent_uk_01` (`hostname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;