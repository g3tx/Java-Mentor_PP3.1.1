# Дамп таблицы roles
# ------------------------------------------------------------
DROP TABLE IF EXISTS `t_roles`;
CREATE TABLE `t_roles`
(
    `role_id`  bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `rolename` varchar(250) DEFAULT NULL,
    PRIMARY KEY (`role_id`),
    UNIQUE KEY `role_id` (`role_id`),
    UNIQUE KEY `rolename` (`rolename`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

INSERT INTO `t_roles` (`role_id`, `rolename`)
VALUES (1, 'ROLE_ADMIN'),
       (2, 'ROLE_USER');

# Дамп таблицы users
# ------------------------------------------------------------
DROP TABLE IF EXISTS `t_users`;
CREATE TABLE `t_users`
(
    `user_id`   bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `username`  varchar(250) DEFAULT NULL,
    `password`  varchar(250) DEFAULT NULL,
    `firstname` varchar(250) DEFAULT NULL,
    `lastname`  varchar(250) DEFAULT NULL,
    `age`       bigint(20)   DEFAULT NULL,
    `email`     varchar(250) DEFAULT NULL,
    PRIMARY KEY (`user_id`),
    UNIQUE KEY `user_id` (`user_id`),
    UNIQUE KEY `username` (`username`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

INSERT INTO `t_users` (`user_id`, `username`, `password`, `firstname`, `lastname`, `age`, `email`)
VALUES (1, 'admin', 'admin', 'none', 'none', 0, 'none'),
       (2, 'editUser', 'editUser', 'none', 'none', 0, 'none'),
       (3, 'GoldenHairy', 'ThorOdinson954', 'Thor', 'Odinson', 2098, 'GodOfThunder@gmail.com'),
       (4, 'LokiRuled', 'LokiLaufeyson965', 'Loki', 'Laufeyson', 2059, 'GodOfMischiefr@gmail.com'),
       (5, 'AllFather', 'OdinBorson1', 'Odin', 'Borson', 6089, 'AllFather@gmail.com'),
       (6, 'Hela666', 'HelaOdinson437', 'Hela', 'Odinson', 3574, 'GoddessOfDeath@gmail.com');

# Дамп таблицы t_users_roles
# ------------------------------------------------------------
DROP TABLE IF EXISTS `t_users_roles`;
CREATE TABLE `t_users_roles`
(
    `user_id` bigint(20) unsigned DEFAULT NULL,
    `role_id` bigint(20) unsigned DEFAULT NULL,
    KEY `hasuser` (`user_id`),
    KEY `hasrole` (`role_id`),
    CONSTRAINT `hasrole` FOREIGN KEY (`role_id`) REFERENCES `t_roles` (`role_id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `hasuser` FOREIGN KEY (`user_id`) REFERENCES `t_users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

INSERT INTO `t_users_roles` (`user_id`, `role_id`)
VALUES (1, 1), -- editUser admin has role ADMIN
       (2, 2), -- editUser editUser has role USER
       (3, 2), -- editUser Thor has role USER
       (4, 2), -- editUser Loki has role USER
       (5, 1), -- editUser Odin has role ADMIN
       (5, 2), -- editUser Odin has role USER
       (6, 2); -- editUser Hela has role USER