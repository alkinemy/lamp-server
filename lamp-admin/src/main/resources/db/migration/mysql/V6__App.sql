USE `lamp`;

DROP TABLE IF EXISTS `lamp_app`;

CREATE TABLE `lamp_app` (
  `id` varchar(100) NOT NULL,
  `type` varchar(200) NOT NULL,

  `name` varchar(200) NOT NULL,
  `description` varchar(255) DEFAULT NULL,

  `parent` varchar(100) NOT NULL,

  `path` VARCHAR(2000) DEFAULT NULL,

  `data` MEDIUMTEXT DEFAULT NULL,

  `cpu` BIGINT DEFAULT NULL,
  `memory` BIGINT DEFAULT NULL,
  `disk_space` BIGINT DEFAULT NULL,
  `instances` BIGINT DEFAULT NULL,

  `created_by` varchar(100) NOT NULL,
  `created_date` datetime NOT NULL,
  `last_modified_by` varchar(100) DEFAULT NULL,
  `last_modified_date` datetime,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
