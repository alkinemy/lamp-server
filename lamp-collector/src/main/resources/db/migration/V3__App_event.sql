USE `lamp_collector`;

DROP TABLE IF EXISTS `lamp_event`;

CREATE TABLE `lamp_event` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL,
  `level` varchar(200) DEFAULT NULL,
  `message` text DEFAULT NULL,

  `time` datetime DEFAULT NULL,

  `source_id` varchar(200) DEFAULT NULL,
  `source_name` varchar(200) DEFAULT NULL,

  `source_hostname` varchar(200) DEFAULT NULL,


  `created_by` varchar(100) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` varchar(100) DEFAULT NULL,
  `last_modified_date` datetime,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
