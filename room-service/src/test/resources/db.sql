CREATE TABLE `category` (
  `id` tinyint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) UNIQUE COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `room` (
  `id` tinyint unsigned NOT NULL AUTO_INCREMENT,
  `code` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  `category_id` tinyint unsigned NOT NULL,
  UNIQUE KEY `room_unique` (`code`),
  PRIMARY KEY (`id`),
  CONSTRAINT `room_FK` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `schedule` (
  `id` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT (uuid()),
  `description` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `start` timestamp NOT NULL,
  `until` timestamp NOT NULL,
  `room_id` tinyint unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `schedule_FK` (`room_id`),
  CONSTRAINT `schedule_FK` FOREIGN KEY (`room_id`) REFERENCES `room` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `recurring_schedule` (
  `id` char(36) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT (uuid()),
  `day` tinyint unsigned NOT NULL,
  `hour_start` tinyint unsigned NOT NULL,
  `hour_end` tinyint unsigned NOT NULL,
  `room_id` tinyint unsigned NOT NULL,
  `minute_start` tinyint unsigned NOT NULL,
  `minute_end` tinyint unsigned NOT NULL,
  `from` date NOT NULL,
  `until` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `recurring_schedule_FK` (`room_id`),
  CONSTRAINT `recurring_schedule_FK` FOREIGN KEY (`room_id`) REFERENCES `room` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO category (id, name) values (1, 'Classroom');
INSERT INTO room (code, category_id) values ('A2', 1);