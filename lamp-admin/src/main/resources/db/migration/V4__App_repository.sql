USE `lamp`;

DROP TABLE IF EXISTS `lamp_app_repository`;
DROP TABLE IF EXISTS `lamp_app_local_repository`;
DROP TABLE IF EXISTS `lamp_app_maven_repository`;
DROP TABLE IF EXISTS `lamp_app_url_repository`;

CREATE TABLE `lamp_app_repository` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `repository_type` varchar(200) NOT NULL,
  `name` varchar(200) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL,
  `created_by` varchar(100) NOT NULL,
  `created_date` datetime NOT NULL,
  `last_modified_by` varchar(100) DEFAULT NULL,
  `last_modified_date` datetime,
  PRIMARY KEY (`id`),
  UNIQUE KEY `lamp_app_repository_uk_01` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `lamp_app_local_repository` (
  `id` bigint(20) NOT NULL,
  `repository_path` varchar(100) DEFAULT NULL,
  `file_limit_size` bigint(20) NULL,
  `file_expiration_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `lamp_app_maven_repository` (
  `id` bigint(20) NOT NULL,
  `repository_path` varchar(100) DEFAULT NULL,
  `repository_url` varchar(1000) DEFAULT NULL,
  `repository_username` varchar(200) DEFAULT NULL,
  `repository_password` varchar(200) DEFAULT NULL,
  `proxy` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `lamp_app_url_repository` (
  `id` bigint(20) NOT NULL,
  `repository_auth_type` varchar(200) DEFAULT NULL,
  `repository_auth_url` varchar(1000) DEFAULT NULL,
  `repository_username` varchar(200) DEFAULT NULL,
  `repository_password` varchar(200) DEFAULT NULL,
  `base_url` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



