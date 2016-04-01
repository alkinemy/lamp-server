USE `lamp`;

DROP TABLE IF EXISTS `lamp_script`;
DROP TABLE IF EXISTS `lamp_app_install_script`;
DROP TABLE IF EXISTS `lamp_script_command`;
DROP TABLE IF EXISTS `lamp_script_execute_command`;
DROP TABLE IF EXISTS `lamp_script_file_create_command`;
DROP TABLE IF EXISTS `lamp_script_file_remove_command`;


CREATE TABLE `lamp_script` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `version` varchar(200) NOT NULL,
  `script_type` varchar(200) NOT NULL,

  `created_by` varchar(100) NOT NULL,
  `created_date` datetime NOT NULL,
  `last_modified_by` varchar(100) DEFAULT NULL,
  `last_modified_date` datetime,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `lamp_app_install_script` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `template_id` varchar(100) NOT NULL,

  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `lamp_script_command` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `script_id` bigint(20) NOT NULL,

  `seq` int(10) NOT NULL,
  `name` varchar(200) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `command_type` varchar(200) DEFAULT NULL,

  `created_by` varchar(100) NOT NULL,
  `created_date` datetime NOT NULL,
  `last_modified_by` varchar(100) DEFAULT NULL,
  `last_modified_date` datetime,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `lamp_script_execute_command` (
  `id` varchar(200) NOT NULL,

  `command_shell` varchar(200) DEFAULT NULL,
  `command_line` varchar(255) DEFAULT NULL,

  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `lamp_script_file_create_command` (
  `id` varchar(200) NOT NULL,

  `filename` varchar(1000) DEFAULT NULL,
  `content` MEDIUMTEXT DEFAULT NULL,
  `charset` varchar(100) DEFAULT NULL,
  `expression_parser` varchar(100) DEFAULT NULL,

  `readable` tinyint(1) NOT NULL,
  `writable` tinyint(1) NOT NULL,
  `executable` tinyint(1) NOT NULL,

  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `lamp_script_file_remove_command` (
  `id` varchar(200) NOT NULL,

  `filename` varchar(1000) DEFAULT NULL,

  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;