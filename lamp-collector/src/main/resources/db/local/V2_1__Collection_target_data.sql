USE `lamp_watcher`;

INSERT INTO `lamp_collection_target` (`id`, `name`, `hostname`, `address`, `agent_id`, `group_id`, `artifact_id`, `version`, `health_collection_enabled`, `health_type`, `health_url`, `metrics_collection_enabled`, `metrics_type`, `metrics_url`, `created_by`, `created_date`, `last_modified_by`, `last_modified_date`)
VALUES
	('self', 'lamp-collector', 'localhost', '127.0.01', NULL, NULL, NULL, NULL, 1, 'SpringBoot', 'http://localhost:9010/health', 1, 'SpringBoot', 'http://localhost:9010/metrics', 'admin', NULL, 'admin', NULL);
