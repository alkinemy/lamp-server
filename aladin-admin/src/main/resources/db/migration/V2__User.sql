USE `lamp`;

DROP TABLE IF EXISTS `lamp_user`;
DROP TABLE IF EXISTS `lamp_authority`;
DROP TABLE IF EXISTS `lamp_user_authority`;
DROP TABLE IF EXISTS `lamp_persistent_token`;

CREATE TABLE `lamp_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `login` varchar(100) NOT NULL,
  `password_hash` varchar(100) NOT NULL,
  `first_name` varchar(100) DEFAULT NULL,
  `last_name` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `lang_key` varchar(5) DEFAULT NULL,
  `activated` bit(1) NOT NULL,
  `activation_key` varchar(20) DEFAULT NULL,
  `reset_date` datetime,
  `reset_key` varchar(20) DEFAULT NULL,
  `created_by` varchar(100) NOT NULL,
  `created_date` datetime NOT NULL,
  `last_modified_by` varchar(100) DEFAULT NULL,
  `last_modified_date` datetime,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_lamp_user_01` (`login`),
  UNIQUE KEY `UK_lamp_user_02` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `lamp_authority` (
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `lamp_user_authority` (
  `user_id` bigint(20) NOT NULL,
  `authority_name` varchar(100) NOT NULL,
  PRIMARY KEY (`user_id`,`authority_name`),
  KEY `lamp_user_authority_idx_01` (`authority_name`),
  CONSTRAINT `lamp_user_authority_fk_01` FOREIGN KEY (`authority_name`) REFERENCES `lamp_authority` (`name`),
  CONSTRAINT `lamp_user_authority_fk_02` FOREIGN KEY (`user_id`) REFERENCES `lamp_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `lamp_persistent_token` (
  `series` varchar(255) NOT NULL,
  `ip_address` varchar(39) DEFAULT NULL,
  `token_date` tinyblob,
  `token_value` varchar(255) NOT NULL,
  `user_agent` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`series`),
  KEY `lamp_persistent_token_idx_01` (`user_id`),
  CONSTRAINT `lamp_persistent_token_fk_01` FOREIGN KEY (`user_id`) REFERENCES `lamp_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO `lamp_authority` (`name`)
VALUES
	('ROLE_ADMIN'),
	('ROLE_USER');


INSERT INTO `lamp_user` (`id`, `created_by`, `created_date`, `last_modified_by`, `last_modified_date`, `activated`, `activation_key`, `email`, `first_name`, `lang_key`, `last_name`, `login`, `password_hash`, `reset_date`, `reset_key`)
VALUES
	(1, 'system', '2015-11-16 13:05:21', 'system', '2015-11-16 13:05:21', 1, '', 'system@localhost', 'System', 'en', 'System', 'system', '$2a$10$mE.qmcV0mFU5NcKh73TZx.z4ueI/.bDWbj0T1BYyqP481kGGarKLG', NULL, NULL),
	(2, 'system', '2015-11-16 13:05:21', 'system', '2015-11-16 13:05:21', 1, '', 'admin@localhost', 'Administrator', 'en', 'Administrator', 'admin', '$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC', NULL, NULL);

INSERT INTO `lamp_user_authority` (`user_id`, `authority_name`)
VALUES
	(1, 'ROLE_ADMIN'),
	(1, 'ROLE_USER'),
	(2, 'ROLE_ADMIN');