USE `lamp`;

DROP TABLE IF EXISTS `app_template`;

CREATE TABLE `app_template` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `resource_type` varchar(200) NOT NULL,
  `name` varchar(200) NOT NULL,
  `description` varchar(255) DEFAULT NULL,

  `repository_id` bigint(20) DEFAULT NULL,
  `app_group_id` varchar(200) DEFAULT NULL,
  `app_id` varchar(200) DEFAULT NULL,
  `app_name` varchar(200) DEFAULT NULL,
  `app_version` varchar(200) DEFAULT NULL,
  `app_url` varchar(1000) DEFAULT NULL,

  `process_type` varchar(200) NOT NULL,
  `app_directory` varchar(1000) DEFAULT NULL,
  `work_directory` varchar(1000) DEFAULT NULL,
  `pid_file` varchar(200) DEFAULT NULL,
  `start_command_line` varchar(1000) DEFAULT NULL,
  `stop_command_line` varchar(1000) DEFAULT NULL,
  `pre_installed` tinyint(1) NOT NULL,
  `app_filename` varchar(200) DEFAULT NULL,
  `monitor` tinyint(1) NOT NULL,

  `commands` text DEFAULT NULL,

  `deleted` tinyint(1) NOT NULL,
  `created_by` varchar(100) NOT NULL,
  `created_date` datetime NOT NULL,
  `last_modified_by` varchar(100) DEFAULT NULL,
  `last_modified_date` datetime,
  PRIMARY KEY (`id`),
  UNIQUE KEY `app_repository_uk_01` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
