USE `lamp`;

DROP TABLE IF EXISTS `lamp_app_local_file`;


CREATE TABLE `lamp_app_local_file` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `repository_id` bigint(20) NOT NULL,
  `group_id` varchar(200) DEFAULT NULL,
  `artifact_id` varchar(200) DEFAULT NULL,
  `base_version` varchar(200) DEFAULT NULL,
  `version` varchar(200) DEFAULT NULL,
  `pathname` varchar(500) DEFAULT NULL,
  `filename` varchar(200) DEFAULT NULL,
  `file_size` bigint(20) NULL,
  `content_type` varchar(200) NULL,
  `file_date` datetime DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL,
  `created_by` varchar(100) NOT NULL,
  `created_date` datetime NOT NULL,
  `last_modified_by` varchar(100) DEFAULT NULL,
  `last_modified_date` datetime,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
