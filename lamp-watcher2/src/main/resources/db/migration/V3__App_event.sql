USE `lamp_watcher`;

DROP TABLE IF EXISTS `lamp_watched_app_event`;

CREATE TABLE `lamp_watched_app_event` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,

  `app_id` varchar(200) DEFAULT NULL,
  `app_name` varchar(200) DEFAULT NULL,

  `app_hostname` varchar(200) DEFAULT NULL,

  `agent_id` varchar(200) DEFAULT NULL,
  `artifact_id` varchar(200) DEFAULT NULL,

  `name` varchar(200) NOT NULL,
  `level` varchar(200) DEFAULT NULL,
  `message` text DEFAULT NULL,

  `time` datetime DEFAULT NULL,

  `created_by` varchar(100) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` varchar(100) DEFAULT NULL,
  `last_modified_date` datetime,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
