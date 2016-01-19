USE `lamp`;

INSERT INTO `app_repository` (`id`, `repository_type`, `name`, `description`, `deleted`, `created_by`, `created_date`, `last_modified_by`, `last_modified_date`)
VALUES
	(1, 'LOCAL', '로컬 저장소', '로컬 저장소', 0, 'admin', '2016-01-18 17:47:00', 'admin', '2016-01-18 17:47:00');

INSERT INTO `app_local_repository` (`id`, `repository_path`, `file_limit_size`, `file_expiration_date`)
VALUES
	(1, '/lamp/loca-repository', NULL, NULL);

