USE `lamp`;

DROP TABLE IF EXISTS `lamp_ssh_key`;
DROP TABLE IF EXISTS `lamp_target_server`;
DROP TABLE IF EXISTS `lamp_collection_target`;
DROP TABLE IF EXISTS `lamp_target_server_status`;
DROP TABLE IF EXISTS `lamp_agent`;

CREATE TABLE `lamp_ssh_key` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL,
  `description` varchar(200) DEFAULT NULL,

  `private_key` text DEFAULT NULL,
  `public_key` text DEFAULT NULL,

  `username` varchar(100) DEFAULT NULL,
  `encrypted_password` varchar(100) DEFAULT NULL,

  `created_by` varchar(100) NOT NULL,
  `created_date` datetime NOT NULL,
  `last_modified_by` varchar(100) DEFAULT NULL,
  `last_modified_date` datetime,
  PRIMARY KEY (`id`),
  UNIQUE KEY `lamp_ssh_key_uk_01` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `lamp_target_server` (
  `id` varchar(100) NOT NULL,
  `name` varchar(200) NOT NULL,
  `description` varchar(200) DEFAULT NULL,
  `hostname` varchar(100) NOT NULL,
  `address` varchar(100) NOT NULL,

  `ssh_port` int NOT NULL,
  `ssh_auth_type` varchar(100) DEFAULT NULL,
  `ssh_key_id` bigint(20) DEFAULT NULL,
  `ssh_key` varchar(1000) DEFAULT NULL,
  `ssh_username` varchar(100) DEFAULT NULL,
  `ssh_password` varchar(100) DEFAULT NULL,

  `agent_installed` tinyint(1) NOT NULL,
  `agent_installed_by` varchar(200) DEFAULT NULL,
  `agent_installed_date` datetime DEFAULT NULL,
  `agent_install_path` varchar(200) NOT NULL,
  `agent_install_filename` varchar(200) DEFAULT NULL,
  `agent_group_id` varchar(200) DEFAULT NULL,
  `agent_artifact_id` varchar(200) DEFAULT NULL,
  `agent_version` varchar(200) DEFAULT NULL,
  `agent_pid_file` varchar(200) DEFAULT NULL,
  `agent_start_command_line` varchar(200) DEFAULT NULL,
  `agent_started_by` varchar(200) DEFAULT NULL,
  `agent_started_date` datetime DEFAULT NULL,
  `agent_stop_command_line` varchar(200) DEFAULT NULL,
  `agent_stopped_by` varchar(200) DEFAULT NULL,
  `agent_stopped_date` datetime DEFAULT NULL,

  `created_by` varchar(100) NOT NULL,
  `created_date` datetime NOT NULL,
  `last_modified_by` varchar(100) DEFAULT NULL,
  `last_modified_date` datetime,
  PRIMARY KEY (`id`),
  UNIQUE KEY `lamp_target_server_uk_01` (`hostname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `lamp_collection_target` (
  `id` varchar(100) NOT NULL,
  `name` varchar(200) DEFAULT NULL,

  `hostname` varchar(100) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,

  `agent_id` varchar(100) DEFAULT NULL,

  `group_id` varchar(100) DEFAULT NULL,
  `artifact_id` varchar(100) DEFAULT NULL,
  `version` varchar(100) DEFAULT NULL,

  `health_monitoring_enabled` tinyint(1) DEFAULT 0,
  `health_collection_enabled` tinyint(1) DEFAULT 0,
  `health_type` varchar(100) DEFAULT NULL,
  `health_url` varchar(1000) DEFAULT NULL,
  `health_export_prefix` varchar(200) DEFAULT NULL,

  `metrics_monitoring_enabled` tinyint(1) DEFAULT 0,
  `metrics_collection_enabled` tinyint(1) DEFAULT 0,
  `metrics_type` varchar(100) DEFAULT NULL,
  `metrics_url` varchar(1000) DEFAULT NULL,
  `metrics_export_prefix` varchar(200) DEFAULT NULL,

  `created_by` varchar(100) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` varchar(100) DEFAULT NULL,
  `last_modified_date` datetime,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `lamp_target_server_status` (
  `id` varchar(100) NOT NULL,
  `agent_status` varchar(200) DEFAULT NULL,
  `agent_status_description` varchar(1000) DEFAULT NULL,
  `agent_status_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `lamp_agent` (
  `id` varchar(100) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `group_id` varchar(200) DEFAULT NULL,
  `artifact_id` varchar(200) DEFAULT NULL,
  `version` varchar(100) DEFAULT NULL,
  `target_server_id` varchar(100) NOT NULL,
  `protocol` varchar(100) DEFAULT NULL,
  `hostname` varchar(100) NOT NULL,
  `address` varchar(100) NOT NULL,
  `port` int NOT NULL,
  `app_directory` varchar(1000) NULL,
  `work_directory` varchar(1000) NULL,
  `secret_key` varchar(255) DEFAULT NULL,
  `agent_time` datetime DEFAULT NULL,
  `health_type` varchar(100) DEFAULT NULL,
  `health_path` varchar(200) DEFAULT NULL,
  `metrics_type` varchar(100) DEFAULT NULL,
  `metrics_path` varchar(200) DEFAULT NULL,
  `created_by` varchar(100) NOT NULL,
  `created_date` datetime NOT NULL,
  `last_modified_by` varchar(100) DEFAULT NULL,
  `last_modified_date` datetime,
  PRIMARY KEY (`id`),
  UNIQUE KEY `lamp_agent_uk_01` (`hostname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;