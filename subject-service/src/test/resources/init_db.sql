DROP TABLE IF EXISTS `class`;
CREATE TABLE `class` (
  `id` tinyint unsigned NOT NULL AUTO_INCREMENT,
  `class_id` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `student_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `first_name` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,
  `last_name` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `address` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `phone` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


DROP TABLE IF EXISTS `subject`;
CREATE TABLE `subject` (
  `id` smallint unsigned NOT NULL AUTO_INCREMENT,
  `subject_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` text COLLATE utf8mb4_unicode_ci,
  PRIMARY KEY (`id`),
  UNIQUE KEY `subject_UN` (`subject_code`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


DROP TABLE IF EXISTS `course_plan`;
CREATE TABLE `course_plan` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `student_id` int unsigned NOT NULL,
  `subject_id` smallint unsigned NOT NULL,
  `semester` tinyint unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `course_plan_FK` (`subject_id`),
  KEY `course_plan_FK_1` (`student_id`),
  CONSTRAINT `course_plan_FK` FOREIGN KEY (`subject_id`) REFERENCES `subject` (`id`),
  CONSTRAINT `course_plan_FK_1` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

LOCK TABLES `subject` WRITE;
INSERT INTO `subject` VALUES (1,'MK01','Mathematics','Mathematics for first year.'),(2,'MK02','Biology','Biology');
UNLOCK TABLES;