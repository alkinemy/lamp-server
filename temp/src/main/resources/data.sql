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