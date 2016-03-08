USE `lamp_watcher`;

INSERT INTO `lamp_monitoring_target` (`id`, `name`, `hostname`, `address`, `agent_id`, `group_id`, `artifact_id`, `version`, `health_monitoring_enabled`, `health_collection_enabled`, `health_type`, `health_url`, `health_export_prefix`, `metrics_monitoring_enabled`, `metrics_collection_enabled`, `metrics_type`, `metrics_url`, `metrics_export_prefix`, `created_by`, `created_date`, `last_modified_by`, `last_modified_date`)
VALUES
	('test', '테스트', 'localhost', '127.0.0.1', NULL, NULL, NULL, NULL, 1, 1, 'SpringBoot', 'http://localhost:9020/health', NULL, 1, 1, 'SpringBoot', 'http://localhost:9020/metrics', NULL, 'test', NULL, 'test', NULL);
