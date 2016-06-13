USE `lamp`;

INSERT INTO `lamp_host` (`id`, `name`, `address`, `description`, `cluster_id`, `rack`, `data`, `health_monitoring_enabled`, `health_collection_enabled`, `health_type`, `health_path`, `metrics_monitoring_enabled`, `metrics_collection_enabled`, `metrics_type`, `metrics_path`, `auth_id`, `agent_installed`, `agent_installed_by`, `agent_installed_date`, `agent_file`, `agent_install_directory`, `agent_install_filename`, `agent_group_id`, `agent_artifact_id`, `agent_version`, `agent_pid_file`, `agent_start_command_line`, `agent_started_by`, `agent_started_date`, `agent_stop_command_line`, `agent_stopped_by`, `agent_stopped_date`, `created_by`, `created_date`, `last_modified_by`, `last_modified_date`)
VALUES
	('49368a2b-195f-491e-9b6f-a41d1ade704b', 'kangwooui-iMac.local', '127.0.0.1', NULL, '1234', NULL, NULL, 0, 0, NULL, NULL, 0, 0, NULL, NULL, NULL, 0, NULL, NULL, 'classpath:agent/lamp-agent.jar', '/lamp/agent/2', 'lamp-agent.jar', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'admin', '2016-06-13 13:17:24', 'admin', '2016-06-13 13:17:24');


INSERT INTO `lamp_host_status` (`id`, `status`, `last_status_time`, `cpu_user`, `cpu_nice`, `cpu_sys`, `disk_total`, `disk_used`, `disk_free`, `mem_total`, `mem_used`, `mem_free`, `swap_total`, `swap_used`, `swap_free`)
VALUES
	('49368a2b-195f-491e-9b6f-a41d1ade704b', NULL, NULL, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
