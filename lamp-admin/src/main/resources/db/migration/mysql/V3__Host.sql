USE `lamp`;

DROP TABLE IF EXISTS `lamp_ssh_key`;

DROP TABLE IF EXISTS `lamp_cluster`;

DROP TABLE IF EXISTS `lamp_host`;
DROP TABLE IF EXISTS `lamp_host_status`;

DROP TABLE IF EXISTS `lamp_task`;

DROP TABLE IF EXISTS `lamp_watch_target`;
DROP TABLE IF EXISTS `lamp_target_server_status`;
DROP TABLE IF EXISTS `lamp_agent`;

CREATE TABLE `lamp_ssh_key` (
  `id` VARCHAR(100) NOT NULL,
  `name` VARCHAR(200) DEFAULT NULL,
  `description` VARCHAR(255) DEFAULT NULL,

  `username` VARCHAR(255) NOT NULL,
  `private_key` MEDIUMTEXT DEFAULT NULL,
  `passphrase` VARCHAR(255) DEFAULT NULL,

  `created_by` VARCHAR(255) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `last_modified_by` VARCHAR(255) DEFAULT NULL,
  `last_modified_date` DATETIME,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `lamp_cluster` (
  `id` VARCHAR(100) NOT NULL,
  `name` VARCHAR(200) DEFAULT NULL,
  `description` VARCHAR(255) DEFAULT NULL,
  `type` VARCHAR(100) DEFAULT NULL,

  `data_type` VARCHAR(255) DEFAULT NULL,
  `data` MEDIUMTEXT DEFAULT NULL,

  `created_by` VARCHAR(255) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `last_modified_by` VARCHAR(255) DEFAULT NULL,
  `last_modified_date` DATETIME,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `lamp_host` (
  `id` VARCHAR(100) NOT NULL,
  `name` VARCHAR(200) DEFAULT NULL,
  `address` VARCHAR(255) DEFAULT NULL,
  `description` VARCHAR(255) DEFAULT NULL,

  `cluster_id` VARCHAR(100) DEFAULT NULL,
  `rack` VARCHAR(255) DEFAULT NULL,

  `data_type` VARCHAR(255) DEFAULT NULL,
  `data` MEDIUMTEXT DEFAULT NULL,

  `health_monitoring_enabled` TINYINT(1) DEFAULT NULL,
  `health_collection_enabled` TINYINT(1) DEFAULT NULL,
  `health_type` VARCHAR(100) DEFAULT NULL,
  `health_path` VARCHAR(255) DEFAULT NULL,

  `metrics_monitoring_enabled` TINYINT(1) DEFAULT NULL,
  `metrics_collection_enabled` TINYINT(1) DEFAULT NULL,
  `metrics_type` VARCHAR(100) DEFAULT NULL,
  `metrics_path` VARCHAR(255) DEFAULT NULL,

  `auth_id` VARCHAR(100) DEFAULT NULL,

  `agent_installed` TINYINT(1) NOT NULL,
  `agent_installed_by` VARCHAR(200) DEFAULT NULL,
  `agent_installed_date` DATETIME DEFAULT NULL,
  `agent_file` VARCHAR(255) DEFAULT NULL,
  `agent_install_directory` VARCHAR(255) DEFAULT NULL,
  `agent_install_filename` VARCHAR(200) DEFAULT NULL,
  `agent_group_id` VARCHAR(200) DEFAULT NULL,
  `agent_artifact_id` VARCHAR(200) DEFAULT NULL,
  `agent_version` VARCHAR(200) DEFAULT NULL,
  `agent_pid_file` VARCHAR(200) DEFAULT NULL,
  `agent_start_command_line` VARCHAR(200) DEFAULT NULL,
  `agent_started_by` VARCHAR(200) DEFAULT NULL,
  `agent_started_date` DATETIME DEFAULT NULL,
  `agent_stop_command_line` VARCHAR(200) DEFAULT NULL,
  `agent_stopped_by` VARCHAR(200) DEFAULT NULL,
  `agent_stopped_date` DATETIME DEFAULT NULL,

  `created_by` VARCHAR(255) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `last_modified_by` VARCHAR(255) DEFAULT NULL,
  `last_modified_date` DATETIME,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `lamp_host_status` (
  `id` VARCHAR(100) NOT NULL,

  `status` VARCHAR(100) DEFAULT NULL,
  `last_status_time` DATETIME DEFAULT NULL,

  `cpu_user` DOUBLE DEFAULT NULL,
  `cpu_nice` DOUBLE DEFAULT NULL,
  `cpu_sys` DOUBLE DEFAULT NULL,

  `disk_total` BIGINT DEFAULT NULL,
  `disk_used` BIGINT DEFAULT NULL,
  `disk_free` BIGINT DEFAULT NULL,

  `mem_total` BIGINT DEFAULT NULL,
  `mem_used` BIGINT DEFAULT NULL,
  `mem_free` BIGINT DEFAULT NULL,

  `swap_total` BIGINT DEFAULT NULL,
  `swap_used` BIGINT DEFAULT NULL,
  `swap_free` BIGINT DEFAULT NULL,

  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `lamp_task` (
  `id` VARCHAR(100) NOT NULL,
  `name` VARCHAR(200) DEFAULT NULL,
  `description` VARCHAR(255) DEFAULT NULL,
  `version` BIGINT DEFAULT NULL,

  `data_type` VARCHAR(255) DEFAULT NULL,
  `data` MEDIUMTEXT DEFAULT NULL,

  `status` VARCHAR(100) DEFAULT NULL,

  `created_by` VARCHAR(255) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `last_modified_by` VARCHAR(255) DEFAULT NULL,
  `last_modified_date` DATETIME,
  PRIMARY KEY (`id`),
  KEY `lamp_task_idx01` (`data_type`, `status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE `lamp_agent` (
  `id` varchar(100) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `group_id` varchar(200) DEFAULT NULL,
  `artifact_id` varchar(200) DEFAULT NULL,
  `version` varchar(100) DEFAULT NULL,
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `lamp_watch_target` (
  `id` varchar(100) NOT NULL,
  `name` varchar(200) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,

  `hostname` varchar(100) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,

  `agent_id` varchar(100) DEFAULT NULL,

  `target_type` varchar(100) DEFAULT NULL,

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

  `tags` varchar(4000) DEFAULT NULL,

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


