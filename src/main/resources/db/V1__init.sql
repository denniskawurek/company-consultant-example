CREATE DATABASE IF NOT EXISTS `company_consultant_example`;
USE `company_consultant_example`;

CREATE TABLE IF NOT EXISTS `company` (
  `company_id` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `company_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`company_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `consultant` (
  `consultant_id` int(11) NOT NULL,
  `company_id` int(11) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`consultant_id`),
  KEY `fk_consultant_to_company` (`company_id`),
  CONSTRAINT `fk_consultant_to_company` FOREIGN KEY (`company_id`) REFERENCES `company` (`company_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DELETE FROM `consultant`;
CREATE TABLE IF NOT EXISTS `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DELETE FROM `hibernate_sequence`;
INSERT INTO `hibernate_sequence` (`next_val`) VALUES
	(1);