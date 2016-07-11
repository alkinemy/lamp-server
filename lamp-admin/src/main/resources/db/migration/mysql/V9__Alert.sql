USE `lamp`;

DROP TABLE IF EXISTS `lamp_alert_action`;
DROP TABLE IF EXISTS `lamp_alert_rule`;

CREATE TABLE `lamp_alert_action` (
  `id` VARCHAR(255) NOT NULL,
  `name` VARCHAR(255) DEFAULT NULL,
  `description` VARCHAR(255) DEFAULT NULL,
  `type` VARCHAR(255) DEFAULT NULL,
  `privated` TINYINT(1) DEFAULT 1,
  `data_type` VARCHAR(255) DEFAULT NULL,
  `data` MEDIUMTEXT DEFAULT NULL,

  `created_by` VARCHAR(100) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `last_modified_by` VARCHAR(100) DEFAULT NULL,
  `last_modified_date` DATETIME,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `lamp_alert_rule` (
  `id` VARCHAR(255) NOT NULL,
  `name` VARCHAR(255) DEFAULT NULL,
  `description` VARCHAR(255) DEFAULT NULL,
  `enabled` TINYINT(1) DEFAULT 1,
  `data_type` VARCHAR(255) DEFAULT NULL,
  `data` MEDIUMTEXT DEFAULT NULL,

  `created_by` VARCHAR(100) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `last_modified_by` VARCHAR(100) DEFAULT NULL,
  `last_modified_date` DATETIME,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
