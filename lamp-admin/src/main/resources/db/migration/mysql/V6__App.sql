USE `lamp`;

DROP TABLE IF EXISTS `lamp_app`;
DROP TABLE IF EXISTS `lamp_app_history`;
DROP TABLE IF EXISTS `lamp_app_instance`;

CREATE TABLE `lamp_app` (
  `id` VARCHAR(100) NOT NULL,
  `version` VARCHAR(100) DEFAULT NULL,
  `type` VARCHAR(200) NOT NULL,

  `name` VARCHAR(100) DEFAULT NULL,
  `description` VARCHAR(255) DEFAULT NULL,
  `path` VARCHAR(255) DEFAULT NULL,
  `parent_path` VARCHAR(255) DEFAULT NULL,

  `data` MEDIUMTEXT DEFAULT NULL,

  `cluster_id` VARCHAR(100) DEFAULT NULL,
  `cpu` BIGINT DEFAULT NULL,
  `memory` BIGINT DEFAULT NULL,
  `disk_space` BIGINT DEFAULT NULL,
  `instances` BIGINT DEFAULT NULL,

  `created_by` VARCHAR(255) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `last_modified_by` VARCHAR(255) DEFAULT NULL,
  `last_modified_date` DATETIME,
  PRIMARY KEY (`id`),
  UNIQUE KEY `lamp_app_uk01` (`path`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `lamp_app_history` (
  `id` VARCHAR(100) NOT NULL,
  `version` VARCHAR(100) NOT NULL,
  `type` VARCHAR(200) NOT NULL,

  `name` VARCHAR(100) DEFAULT NULL,
  `description` VARCHAR(255) DEFAULT NULL,
  `path` VARCHAR(255) DEFAULT NULL,
  `parent_path` VARCHAR(255) DEFAULT NULL,

  `data` MEDIUMTEXT DEFAULT NULL,

  `cluster_id` VARCHAR(100) DEFAULT NULL,
  `cpu` BIGINT DEFAULT NULL,
  `memory` BIGINT DEFAULT NULL,
  `disk_space` BIGINT DEFAULT NULL,
  `instances` BIGINT DEFAULT NULL,

  `created_by` VARCHAR(255) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `last_modified_by` VARCHAR(255) DEFAULT NULL,
  `last_modified_date` DATETIME,
  PRIMARY KEY (`id`, `version`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `lamp_app_instance` (
  `id` VARCHAR(255) NOT NULL,
  `name` VARCHAR(255) DEFAULT NULL,
  `description` VARCHAR(255) DEFAULT NULL,

  `app_id` VARCHAR(255) DEFAULT NULL,
  `app_version` VARCHAR(100) DEFAULT NULL,
  `host_id` VARCHAR(255) DEFAULT NULL,

  `pid` VARCHAR(100) DEFAULT NULL,
  `status` VARCHAR(100)  DEFAULT NULL,
  `status_message` VARCHAR(4000) DEFAULT NULL,
  `monitored` TINYINT(1) DEFAULT NULL,

  `data` MEDIUMTEXT DEFAULT NULL,

  `created_by` VARCHAR(255) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `last_modified_by` VARCHAR(255) DEFAULT NULL,
  `last_modified_date` DATETIME,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;