USE `lamp`;

DROP TABLE IF EXISTS `lamp_app_template`;

CREATE TABLE `lamp_app_template` (
  `id` varchar(100) NOT NULL,
  `resource_type` varchar(200) NOT NULL,
  `name` varchar(200) NOT NULL,
  `description` varchar(255) DEFAULT NULL,

  `repository_id` varchar(100) DEFAULT NULL,
  `group_id` varchar(200) DEFAULT NULL,
  `artifact_id` varchar(200) DEFAULT NULL,
  `artifact_name` varchar(200) DEFAULT NULL,
  `version` varchar(200) DEFAULT NULL,

  `resource_url` varchar(1000) DEFAULT NULL,

  `process_type` varchar(200) NOT NULL,
  `app_directory` varchar(1000) DEFAULT NULL,
  `work_directory` varchar(1000) DEFAULT NULL,
  `log_directory` varchar(1000) DEFAULT NULL,

  `pid_file` varchar(500) DEFAULT NULL,
  `ptql` varchar(500) DEFAULT NULL,

  `std_out_file` varchar(500) DEFAULT NULL,
  `std_err_file` varchar(500) DEFAULT NULL,

  `command_shell` varchar(255) DEFAULT NULL,
  `start_command_line` varchar(1000) DEFAULT NULL,
  `stop_command_line` varchar(1000) DEFAULT NULL,
  `pre_installed` tinyint(1) NOT NULL,
  `app_filename` varchar(200) DEFAULT NULL,
  `monitor` tinyint(1) NOT NULL,

  `commands` text DEFAULT NULL,

  `parameters_type` varchar(500) DEFAULT NULL,
  `parameters` text DEFAULT NULL,

  `deleted` tinyint(1) NOT NULL,
  `created_by` varchar(100) NOT NULL,
  `created_date` datetime NOT NULL,
  `last_modified_by` varchar(100) DEFAULT NULL,
  `last_modified_date` datetime,
  PRIMARY KEY (`id`),
  UNIQUE KEY `lamp_app_template_uk_01` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
