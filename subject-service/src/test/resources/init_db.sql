CREATE TABLE `subject` (
  `id` smallint unsigned NOT NULL AUTO_INCREMENT,
  `subject_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  PRIMARY KEY (`id`),
  UNIQUE KEY `subject_UN` (`subject_code`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `subject` VALUES (1,'MK01','Mathematics','Mathematics for first year.'), (2,'MK02','Biology','Biology');