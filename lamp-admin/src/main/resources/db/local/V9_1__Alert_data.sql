
INSERT INTO `lamp_alert_action` (`id`, `name`, `description`, `type`, `data`, `created_by`, `created_date`, `last_modified_by`, `last_modified_date`)
VALUES
	('10a836e1-a8ac-430f-890f-51490b2785a6', '모니터링 알람', NULL, 'lamp.admin.domain.alert.model.MmsNotificationAction', '{\"id\":\"10a836e1-a8ac-430f-890f-51490b2785a6\",\"name\":\"모니터링 알람\",\"notificationIntervalSeconds\":60,\"subject\":\"Monitoring Alarm\",\"message\":\"[#{target.tags.clusterId}] #{rule.name} #{target.tags.hostName} #{state.value}\",\"phoneNumbers\":\"010-2768-0229\"}', 'system', '2016-07-08 14:46:47', 'system', '2016-07-08 14:46:47');
