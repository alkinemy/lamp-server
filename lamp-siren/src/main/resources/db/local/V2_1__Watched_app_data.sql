USE `lamp_watcher`;

INSERT INTO `lamp_watched_app` (`id`, `name`, `hostname`, `address`, `agent_id`, `group_id`, `artifact_id`, `version`, `monitoring_enabled`, `health_monitoring_enabled`, `health_collection_enabled`, `health_type`, `health_url`, `metrics_monitoring_enabled`, `metrics_collection_enabled`, `metrics_type`, `metrics_url`, `created_by`, `created_date`, `last_modified_by`, `last_modified_date`)
VALUES
	('self', '자기자신', 'localhost', NULL, NULL, NULL, NULL, NULL, 1, 1, 1, 'SpringBoot', 'http://localhost:8090/health', 1, 1, 'SpringBoot', 'http://localhost:8090/metrics', 'test', NULL, 'test', NULL);
