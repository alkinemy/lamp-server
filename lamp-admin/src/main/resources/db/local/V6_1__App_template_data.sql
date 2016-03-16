USE `lamp`;

INSERT INTO `lamp_app_template` (`id`, `resource_type`, `name`, `description`, `repository_id`, `group_id`, `artifact_id`, `artifact_name`, `version`, `resource_url`, `process_type`, `app_directory`, `work_directory`, `log_directory`, `pid_file`, `ptql`, `std_out_file`, `std_err_file`, `command_shell`, `start_command_line`, `stop_command_line`, `pre_installed`, `app_filename`, `monitor`, `commands`, `parameters_type`, `parameters`, `deleted`, `created_by`, `created_date`, `last_modified_by`, `last_modified_date`)
VALUES
	('ba06f07e-5f5a-46ab-b929-5dec9bde9409', 'LOCAL', 'lamp-agent', '램프 에이전트 템플릿', 'b1b9e4cc-a11e-47cf-95ff-21a2c0356a0d', 'lamp.agent', 'lamp-agent', NULL, '', NULL, 'DAEMON', '', '${appDirectory}', '${appDirectory}/logs', '${workDirectory}/${artifactId}.pid', '', '', '', '', './${artifactId}.sh start', './${artifactId}.sh stop', 0, '${artifactId}.jar', 0, NULL, '0', '', 0, 'admin', '2016-03-16 11:03:28', 'admin', '2016-03-16 11:03:28');
