USE `lamp`;

DROP TABLE IF EXISTS `lamp_managed_app`;


CREATE TABLE `lamp_managed_app` (
  `id` varchar(200) NOT NULL,
  `name` varchar(200) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `target_server_id` varchar(100)  NOT NULL,
  `template_id` varchar(100) NOT NULL,
  `group_id` varchar(200) DEFAULT NULL,
  `artifact_id` varchar(200) DEFAULT NULL,
  `artifact_name` varchar(200) DEFAULT NULL,
  `version` varchar(200) DEFAULT NULL,
  `app_management_listener` varchar(1000) DEFAULT NULL,
  `updatable` tinyint(1) DEFAULT NULL,
  `register_date` datetime DEFAULT NULL,
  `install_date` datetime DEFAULT NULL,
  `created_by` varchar(100) NOT NULL,
  `created_date` datetime NOT NULL,
  `last_modified_by` varchar(100) DEFAULT NULL,
  `last_modified_date` datetime,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
