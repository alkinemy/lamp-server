USE `lamp`;

-- INSERT INTO `lamp_target_server` (`id`, `name`, `description`, `hostname`, `address`, `ssh_port`, `ssh_auth_type`, `ssh_key_id`, `ssh_key`, `ssh_username`, `ssh_password`, `agent_installed`, `agent_installed_by`, `agent_installed_date`, `agent_install_path`, `agent_install_filename`, `agent_group_id`, `agent_artifact_id`, `agent_version`, `agent_pid_file`, `agent_start_command_line`, `agent_started_by`, `agent_started_date`, `agent_stop_command_line`, `agent_stopped_by`, `agent_stopped_date`, `created_by`, `created_date`, `last_modified_by`, `last_modified_date`)
-- VALUES
-- 	('dc386fc0-1319-4a7d-b218-07b5c55784ff', 'localhost', '로컬 호스트', 'localhost', '127.0.0.1', 22, 'PASSWORD', NULL, NULL, '', '', 0, NULL, NULL, '/lamp/agent', NULL, NULL, NULL, NULL, '', '', NULL, NULL, '', NULL, NULL, 'admin', '2016-03-16 11:19:44', 'admin', '2016-03-16 11:19:44');
--
-- INSERT INTO `lamp_watch_target` (`id`, `name`, `hostname`, `address`, `agent_id`, `group_id`, `artifact_id`, `version`, `health_monitoring_enabled`, `health_collection_enabled`, `health_type`, `health_url`, `health_export_prefix`, `metrics_monitoring_enabled`, `metrics_collection_enabled`, `metrics_type`, `metrics_url`, `metrics_export_prefix`, `created_by`, `created_date`, `last_modified_by`, `last_modified_date`)
-- VALUES
-- 	('dc386fc0-1319-4a7d-b218-07b5c55784ff', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, 1, 'SpringBoot', 'http://127.0.0.1:9090/health', NULL, 1, 1, 'SpringBoot', 'http://127.0.0.1:9090/metrics', NULL, NULL, NULL, NULL, NULL);
