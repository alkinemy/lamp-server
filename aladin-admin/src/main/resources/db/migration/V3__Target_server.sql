USE `lamp`;

DROP TABLE IF EXISTS `lamp_target_server`;
DROP TABLE IF EXISTS `lamp_agent`;

CREATE TABLE `lamp_target_server` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL,
  `description` varchar(200) DEFAULT NULL,
  `hostname` varchar(100) NOT NULL,
  `address` varchar(100) NOT NULL,
  `ssh_port` int NOT NULL,
  `auth_type` varchar(100) DEFAULT NULL,
  `username` varchar(100) DEFAULT NULL,
  `encrypted_password` varchar(100) DEFAULT NULL,
  `agent_installed` tinyint(1) NOT NULL,
  `agent_install_path` varchar(100) NOT NULL,
  `monitor` tinyint(1) NOT NULL,
  `monitor_interval` bigint(20) DEFAULT '0',
  `created_by` varchar(100) NOT NULL,
  `created_date` datetime NOT NULL,
  `last_modified_by` varchar(100) DEFAULT NULL,
  `last_modified_date` datetime,
  PRIMARY KEY (`id`),
  UNIQUE KEY `lamp_target_server_uk_01` (`hostname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `lamp_agent` (
  `id` varchar(100) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `type` varchar(100) DEFAULT NULL,
  `version` varchar(100) DEFAULT NULL,
  `target_server_id` bigint(20) NOT NULL,
  `protocol` varchar(100) DEFAULT NULL,
  `hostname` varchar(100) NOT NULL,
  `address` varchar(100) NOT NULL,
  `port` int NOT NULL,
  `app_directory` varchar(1000) NULL,
  `work_directory` varchar(1000) NULL,
  `secret_key` varchar(255) DEFAULT NULL,
  `agent_time` datetime DEFAULT NULL,
  `health_path` varchar(100) DEFAULT NULL,
  `metrics_path` varchar(100) DEFAULT NULL,
  `created_by` varchar(100) NOT NULL,
  `created_date` datetime NOT NULL,
  `last_modified_by` varchar(100) DEFAULT NULL,
  `last_modified_date` datetime,
  PRIMARY KEY (`id`),
  UNIQUE KEY `lamp_agent_uk_01` (`hostname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;