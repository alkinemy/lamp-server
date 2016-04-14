USE `lamp`;

INSERT INTO `lamp_app_repository` (`id`, `repository_type`, `name`, `description`, `deleted`, `created_by`, `created_date`, `last_modified_by`, `last_modified_date`)
VALUES
	('b1b9e4cc-a11e-47cf-95ff-21a2c0356a0d', 'LOCAL', '로컬 저장소', '로컬 저장소', 0, 'admin', '2016-03-16 10:21:53', 'admin', '2016-03-16 10:21:53');


INSERT INTO `lamp_app_local_repository` (`id`, `repository_path`, `file_limit_size`, `file_expiration_time`)
VALUES
	('b1b9e4cc-a11e-47cf-95ff-21a2c0356a0d', '/lamp/repo/local', NULL, NULL);

