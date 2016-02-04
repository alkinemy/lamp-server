USE `lamp`;

DROP TABLE IF EXISTS `lamp_agent_event`;

CREATE TABLE `lamp_agent_event` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `agent_id` varchar(200) NOT NULL,
  `agent_instance_id` bigint(20) NOT NULL,
  `agent_instance_event_sequence` bigint(20) NOT NULL,
  `event_name` varchar(200) NOT NULL,
  `event_level` varchar(200) DEFAULT NULL,
  `event_time` datetime NOT NULL,

  `app_id` varchar(200) DEFAULT NULL,
  `message` text DEFAULT NULL,

  `created_by` varchar(100) NOT NULL,
  `created_date` datetime NOT NULL,
  `last_modified_by` varchar(100) DEFAULT NULL,
  `last_modified_date` datetime,
  PRIMARY KEY (`id`),
  UNIQUE KEY `lamp_agent_event_uk_01` (`agent_id`, `agent_instance_id`, `agent_instance_event_sequence`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
