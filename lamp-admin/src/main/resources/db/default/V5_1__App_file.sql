USE `lamp`;

INSERT INTO `lamp_app_local_file` (`id`, `name`, `description`, `repository_id`, `group_id`, `artifact_id`, `base_version`, `version`, `pathname`, `filename`, `file_size`, `content_type`, `file_date`, `deleted`, `created_by`, `created_date`, `last_modified_by`, `last_modified_date`)
VALUES
	(1, '테스트 앱', '테스트 앱', 1, 'lamp.agent', 'test-app', '0.0.1-SNAPSHOT', '0.0.1-SNAPSHOT.1455843785159', '/lamp/local-repository/1/lamp.agent/test-app/test-app0.0.1-SNAPSHOT-1455843785169.jar', 'test-app-0.0.1-SNAPSHOT.jar', 13963128, 'application/java-archive', '2016-02-19 10:03:05', 0, 'admin', '2016-02-19 10:03:05', 'admin', '2016-02-19 10:03:05');
