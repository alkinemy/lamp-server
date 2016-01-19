USE `lamp`;

INSERT INTO `app_template` (`id`, `template_type`, `name`, `description`, `repository_id`, `group_id`, `app_id`, `app_name`, `app_version`, `app_url`, `process_type`, `pid_file`, `start_command_line`, `stop_command_line`, `pre_installed`, `app_filename`, `monitor`, `commands`, `deleted`, `created_by`, `created_date`, `last_modified_by`, `last_modified_date`)
VALUES
	(1, 'LOCAL', '테스트 템플릿', '테스트 템플릿입니다.', 1, 'lamp.agent', 'test-app', NULL, '0.0.1-SNAPSHOT', NULL, 'DEFAULT', '${workDirectory}/${appId}.pid', './${appId}.sh start', '', 0, '${appId}.jar', 1, '{\"SpringBootInstallCommand\":{\"springOpts\":\"\",\"jvmOpts\":\"-Xms128m -Xmx256m\",\"launchScriptFilename\":\"\",\"launchScript\":\"\"}}', 0, 'admin', '2016-01-19 09:31:50', 'admin', '2016-01-19 09:31:50');

