USE `lamp`;

DROP TABLE IF EXISTS `lamp_app`;

CREATE TABLE `lamp_app` (
  `id` VARCHAR(255) NOT NULL,
  `type` VARCHAR(200) NOT NULL,

  `name` VARCHAR(100) DEFAULT NULL,
  `description` VARCHAR(255) DEFAULT NULL,
  `path` VARCHAR(255) DEFAULT NULL,

  `data` MEDIUMTEXT DEFAULT NULL,

  `cluster_id` VARCHAR(100) DEFAULT NULL,
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
