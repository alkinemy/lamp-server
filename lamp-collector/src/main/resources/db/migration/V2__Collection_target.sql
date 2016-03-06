USE `lamp_watcher`;

DROP TABLE IF EXISTS `lamp_collection_target`;

CREATE TABLE `lamp_collection_target` (
  `id` varchar(100) NOT NULL,
  `name` varchar(200) DEFAULT NULL,

  `hostname` varchar(100) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,

  `agent_id` varchar(100) DEFAULT NULL,

  `group_id` varchar(100) DEFAULT NULL,
  `artifact_id` varchar(100) DEFAULT NULL,
  `version` varchar(100) DEFAULT NULL,

  `health_collection_enabled` tinyint(1) DEFAULT 0,
  `health_type` varchar(100) DEFAULT NULL,
  `health_url` varchar(1000) DEFAULT NULL,
  `health_prefix` varchar(200) DEFAULT NULL,

  `metrics_collection_enabled` tinyint(1) DEFAULT 0,
  `metrics_type` varchar(100) DEFAULT NULL,
  `metrics_url` varchar(1000) DEFAULT NULL,
  `metrics_prefix` varchar(200) DEFAULT NULL,

  `created_by` varchar(100) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` varchar(100) DEFAULT NULL,
  `last_modified_date` datetime,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

