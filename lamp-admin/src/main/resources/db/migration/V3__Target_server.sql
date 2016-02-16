USE `lamp`;

DROP TABLE IF EXISTS `lamp_target_server`;
DROP TABLE IF EXISTS `lamp_target_server_status`;
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
  `private_key` varchar(200) DEFAULT NULL,
  `agent_installed` tinyint(1) NOT NULL,
  `agent_installed_by` varchar(200) DEFAULT NULL,
  `agent_installed_date` datetime DEFAULT NULL,
  `agent_install_path` varchar(200) NOT NULL,
  `agent_install_filename` varchar(200) DEFAULT NULL,
  `agent_pid_file` varchar(200) DEFAULT NULL,
  `agent_start_command_line` varchar(200) DEFAULT NULL,
  `agent_started_by` varchar(200) DEFAULT NULL,
  `agent_started_date` datetime DEFAULT NULL,
  `agent_stop_command_line` varchar(200) DEFAULT NULL,
  `agent_stopped_by` varchar(200) DEFAULT NULL,
  `agent_stopped_date` datetime DEFAULT NULL,

  `agent_health_url` varchar(1000) DEFAULT NULL,
  `agent_monitor` tinyint(1) NOT NULL,
  `agent_monitor_interval` bigint(20) DEFAULT '0',
  `created_by` varchar(100) NOT NULL,
  `created_date` datetime NOT NULL,
  `last_modified_by` varchar(100) DEFAULT NULL,
  `last_modified_date` datetime,
  PRIMARY KEY (`id`),
  UNIQUE KEY `lamp_target_server_uk_01` (`hostname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `lamp_target_server_status` (
  `id` bigint(20) NOT NULL,
  `agent_status` varchar(200) DEFAULT NULL,
  `agent_status_description` varchar(1000) DEFAULT NULL,
  `agent_status_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
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
  `health_path` varchar(200) DEFAULT NULL,
  `metrics_path` varchar(200) DEFAULT NULL,
  `created_by` varchar(100) NOT NULL,
  `created_date` datetime NOT NULL,
  `last_modified_by` varchar(100) DEFAULT NULL,
  `last_modified_date` datetime,
  PRIMARY KEY (`id`),
  UNIQUE KEY `lamp_agent_uk_01` (`hostname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;