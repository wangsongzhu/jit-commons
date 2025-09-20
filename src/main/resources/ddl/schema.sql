-- create database
CREATE SCHEMA jit DEFAULT CHARACTER SET utf8mb4;

-- create user
CREATE USER 'jitadmin'@'%' IDENTIFIED BY 'P@ssw0rd';
GRANT ALL PRIVILEGES ON jit.* TO 'jitadmin'@'%';
CREATE USER 'jitadmin'@'localhost' IDENTIFIED BY 'P@ssw0rd';
GRANT ALL PRIVILEGES ON jit.* TO 'jitadmin'@'localhost';
FLUSH PRIVILEGES;

use jit;

-- #################################
-- ##              User           ##
-- #################################

-- j_users table
CREATE TABLE `j_users`
(
    `id`                VARCHAR(64)  NOT NULL COMMENT 'User UUID',
    `email`             VARCHAR(254) NOT NULL COMMENT 'User email',
    `password_hash`     VARCHAR(256) NOT NULL COMMENT 'Password encrypted',
    `signup_date`       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Date and time signed up',
    `verified`          TINYINT NOT NULL DEFAULT 0 COMMENT '0: Email not verified, 1: Email verified',
    `last_login`        TIMESTAMP COMMENT 'Date and time of last login',
    PRIMARY KEY (`id`),
    UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='Users';

-- #################################
-- ##   Permission Management     ##
-- #################################

-- roles master
CREATE TABLE `j_roles` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL UNIQUE COMMENT 'Role name',
  `description` VARCHAR(255) DEFAULT NULL COMMENT 'Role description',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='Roles master';

-- User role mapping
CREATE TABLE `j_user_roles` (
  `user_id` VARCHAR(64) NOT NULL COMMENT 'User ID',
  `role_id` BIGINT NOT NULL COMMENT 'Role ID',
  PRIMARY KEY (`user_id`,`role_id`),
  FOREIGN KEY (`user_id`) REFERENCES `j_users` (`id`) ON DELETE CASCADE,
  FOREIGN KEY (`role_id`) REFERENCES `j_roles` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='User role mapping';

-- Permissions master
CREATE TABLE `j_permissions` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL UNIQUE COMMENT 'Permission name',
  `description` VARCHAR(255) DEFAULT NULL COMMENT 'Permission description',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='Permissions master';

-- Role permissions mapping
CREATE TABLE `j_role_permissions` (
  `role_id` BIGINT NOT NULL COMMENT 'Role ID',
  `permission_id` BIGINT NOT NULL COMMENT 'Permission ID',
  PRIMARY KEY (`role_id`,`permission_id`),
  FOREIGN KEY (`role_id`) REFERENCES `j_roles` (`id`) ON DELETE CASCADE,
  FOREIGN KEY (`permission_id`) REFERENCES `j_permissions` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='Role permissions mapping';

-- Master data
INSERT INTO `j_roles` (`name`, `description`) VALUES
('ROLE_FREE', 'Free'),
('ROLE_STANDARD', 'Standard'),
('ROLE_PREMIER', 'Premier'),
('ROLE_PLATINUM', 'Platinum');

INSERT INTO `j_permissions` (`name`, `description`) VALUES
('P010', 'QR Codes, attibute: number/month'),
('P020', 'Links, attibute: number/month'),
('P030', 'Custom landing pages, attibute: number'),
('P040', 'Limited clicks, attibute: number/month'),
('P050', 'Limited QR Code scans, attibute: number/month'),
('P060', 'QR Code customizations'),
('P070', 'Advanced QR Code customizations'),
('P080', 'Days of click data, attibute: number'),
('P090', 'Days of scan data, attibute: number'),
('P100', 'UTM Builder'),
('P110', 'Link redirects'),
('P120', 'QR Code redirects'),
('P130', 'Complimentary custom domain'),
('P140', 'Branded links'),
('P150', 'Bulk link creation'),
('P160', 'Bulk QR Code creation'),
('P170', 'Custom campaign-level tracking'),
('P180', 'City-level scan data'),
('P190', 'Device type click scan data'),
('P200', 'Mobile deep linking');

-- ROLE_FREE Role permissions mapping
INSERT INTO `j_role_permissions` (`role_id`, `permission_id`) VALUES
((SELECT id FROM `j_roles` WHERE name = 'ROLE_FREE'), (SELECT id FROM `j_permissions` WHERE name = 'P010')),
((SELECT id FROM `j_roles` WHERE name = 'ROLE_FREE'), (SELECT id FROM `j_permissions` WHERE name = 'P020')),
((SELECT id FROM `j_roles` WHERE name = 'ROLE_FREE'), (SELECT id FROM `j_permissions` WHERE name = 'P030')),
((SELECT id FROM `j_roles` WHERE name = 'ROLE_FREE'), (SELECT id FROM `j_permissions` WHERE name = 'P040')),
((SELECT id FROM `j_roles` WHERE name = 'ROLE_FREE'), (SELECT id FROM `j_permissions` WHERE name = 'P050')),
((SELECT id FROM `j_roles` WHERE name = 'ROLE_FREE'), (SELECT id FROM `j_permissions` WHERE name = 'P060'));

-- ROLE_STANDARD Role permissions mapping
INSERT INTO `j_role_permissions` (`role_id`, `permission_id`) VALUES
((SELECT id FROM `j_roles` WHERE name = 'ROLE_STANDARD'), (SELECT id FROM `j_permissions` WHERE name = 'P010')),
((SELECT id FROM `j_roles` WHERE name = 'ROLE_STANDARD'), (SELECT id FROM `j_permissions` WHERE name = 'P020')),
((SELECT id FROM `j_roles` WHERE name = 'ROLE_STANDARD'), (SELECT id FROM `j_permissions` WHERE name = 'P030')),
((SELECT id FROM `j_roles` WHERE name = 'ROLE_STANDARD'), (SELECT id FROM `j_permissions` WHERE name = 'P040')),
((SELECT id FROM `j_roles` WHERE name = 'ROLE_STANDARD'), (SELECT id FROM `j_permissions` WHERE name = 'P050')),
((SELECT id FROM `j_roles` WHERE name = 'ROLE_STANDARD'), (SELECT id FROM `j_permissions` WHERE name = 'P060')),
((SELECT id FROM `j_roles` WHERE name = 'ROLE_STANDARD'), (SELECT id FROM `j_permissions` WHERE name = 'P070')),
((SELECT id FROM `j_roles` WHERE name = 'ROLE_STANDARD'), (SELECT id FROM `j_permissions` WHERE name = 'P080')),
((SELECT id FROM `j_roles` WHERE name = 'ROLE_STANDARD'), (SELECT id FROM `j_permissions` WHERE name = 'P090')),
((SELECT id FROM `j_roles` WHERE name = 'ROLE_STANDARD'), (SELECT id FROM `j_permissions` WHERE name = 'P100')),
((SELECT id FROM `j_roles` WHERE name = 'ROLE_STANDARD'), (SELECT id FROM `j_permissions` WHERE name = 'P110')),
((SELECT id FROM `j_roles` WHERE name = 'ROLE_STANDARD'), (SELECT id FROM `j_permissions` WHERE name = 'P120'));

-- ROLE_PREMIER Role permissions mapping
INSERT INTO `j_role_permissions` (`role_id`, `permission_id`) VALUES
((SELECT id FROM `j_roles` WHERE name = 'ROLE_PREMIER'), (SELECT id FROM `j_permissions` WHERE name = 'P010')),
((SELECT id FROM `j_roles` WHERE name = 'ROLE_PREMIER'), (SELECT id FROM `j_permissions` WHERE name = 'P020')),
((SELECT id FROM `j_roles` WHERE name = 'ROLE_PREMIER'), (SELECT id FROM `j_permissions` WHERE name = 'P030')),
((SELECT id FROM `j_roles` WHERE name = 'ROLE_PREMIER'), (SELECT id FROM `j_permissions` WHERE name = 'P040')),
((SELECT id FROM `j_roles` WHERE name = 'ROLE_PREMIER'), (SELECT id FROM `j_permissions` WHERE name = 'P050')),
((SELECT id FROM `j_roles` WHERE name = 'ROLE_PREMIER'), (SELECT id FROM `j_permissions` WHERE name = 'P060')),
((SELECT id FROM `j_roles` WHERE name = 'ROLE_PREMIER'), (SELECT id FROM `j_permissions` WHERE name = 'P070')),
((SELECT id FROM `j_roles` WHERE name = 'ROLE_PREMIER'), (SELECT id FROM `j_permissions` WHERE name = 'P080')),
((SELECT id FROM `j_roles` WHERE name = 'ROLE_PREMIER'), (SELECT id FROM `j_permissions` WHERE name = 'P090')),
((SELECT id FROM `j_roles` WHERE name = 'ROLE_PREMIER'), (SELECT id FROM `j_permissions` WHERE name = 'P100')),
((SELECT id FROM `j_roles` WHERE name = 'ROLE_PREMIER'), (SELECT id FROM `j_permissions` WHERE name = 'P110')),
((SELECT id FROM `j_roles` WHERE name = 'ROLE_PREMIER'), (SELECT id FROM `j_permissions` WHERE name = 'P120')),
((SELECT id FROM `j_roles` WHERE name = 'ROLE_PREMIER'), (SELECT id FROM `j_permissions` WHERE name = 'P130')),
((SELECT id FROM `j_roles` WHERE name = 'ROLE_PREMIER'), (SELECT id FROM `j_permissions` WHERE name = 'P140')),
((SELECT id FROM `j_roles` WHERE name = 'ROLE_PREMIER'), (SELECT id FROM `j_permissions` WHERE name = 'P150')),
((SELECT id FROM `j_roles` WHERE name = 'ROLE_PREMIER'), (SELECT id FROM `j_permissions` WHERE name = 'P160'));

-- ROLE_PLATINUM Role permissions mapping
INSERT INTO `j_role_permissions` (`role_id`, `permission_id`) VALUES
((SELECT id FROM `j_roles` WHERE name = 'ROLE_PLATINUM'), (SELECT id FROM `j_permissions` WHERE name = 'P010')),
((SELECT id FROM `j_roles` WHERE name = 'ROLE_PLATINUM'), (SELECT id FROM `j_permissions` WHERE name = 'P020')),
((SELECT id FROM `j_roles` WHERE name = 'ROLE_PLATINUM'), (SELECT id FROM `j_permissions` WHERE name = 'P030')),
((SELECT id FROM `j_roles` WHERE name = 'ROLE_PLATINUM'), (SELECT id FROM `j_permissions` WHERE name = 'P040')),
((SELECT id FROM `j_roles` WHERE name = 'ROLE_PLATINUM'), (SELECT id FROM `j_permissions` WHERE name = 'P050')),
((SELECT id FROM `j_roles` WHERE name = 'ROLE_PLATINUM'), (SELECT id FROM `j_permissions` WHERE name = 'P060')),
((SELECT id FROM `j_roles` WHERE name = 'ROLE_PLATINUM'), (SELECT id FROM `j_permissions` WHERE name = 'P070')),
((SELECT id FROM `j_roles` WHERE name = 'ROLE_PLATINUM'), (SELECT id FROM `j_permissions` WHERE name = 'P080')),
((SELECT id FROM `j_roles` WHERE name = 'ROLE_PLATINUM'), (SELECT id FROM `j_permissions` WHERE name = 'P090')),
((SELECT id FROM `j_roles` WHERE name = 'ROLE_PLATINUM'), (SELECT id FROM `j_permissions` WHERE name = 'P100')),
((SELECT id FROM `j_roles` WHERE name = 'ROLE_PLATINUM'), (SELECT id FROM `j_permissions` WHERE name = 'P110')),
((SELECT id FROM `j_roles` WHERE name = 'ROLE_PLATINUM'), (SELECT id FROM `j_permissions` WHERE name = 'P120')),
((SELECT id FROM `j_roles` WHERE name = 'ROLE_PLATINUM'), (SELECT id FROM `j_permissions` WHERE name = 'P130')),
((SELECT id FROM `j_roles` WHERE name = 'ROLE_PLATINUM'), (SELECT id FROM `j_permissions` WHERE name = 'P140')),
((SELECT id FROM `j_roles` WHERE name = 'ROLE_PLATINUM'), (SELECT id FROM `j_permissions` WHERE name = 'P150')),
((SELECT id FROM `j_roles` WHERE name = 'ROLE_PLATINUM'), (SELECT id FROM `j_permissions` WHERE name = 'P160')),
((SELECT id FROM `j_roles` WHERE name = 'ROLE_PLATINUM'), (SELECT id FROM `j_permissions` WHERE name = 'P170')),
((SELECT id FROM `j_roles` WHERE name = 'ROLE_PLATINUM'), (SELECT id FROM `j_permissions` WHERE name = 'P180')),
((SELECT id FROM `j_roles` WHERE name = 'ROLE_PLATINUM'), (SELECT id FROM `j_permissions` WHERE name = 'P190')),
((SELECT id FROM `j_roles` WHERE name = 'ROLE_PLATINUM'), (SELECT id FROM `j_permissions` WHERE name = 'P200'));


-- #################################
-- ##              URL            ##
-- #################################

-- j_urls table
CREATE TABLE `j_urls`
(
    `id`                    VARCHAR(64) NOT NULL,
    `user_id`               VARCHAR(64) NOT NULL COMMENT 'User ID',
    `title`                 VARCHAR(256) COMMENT 'title or header of target URL page',
    `original_url`          TEXT        NOT NULL COMMENT 'Original URL',
    `domain_url`            VARCHAR(25) NOT NULL COMMENT 'Domain URL',
    `short_url`             VARCHAR(10) NOT NULL COMMENT 'Short part of short URL',
    `expiration_date`       DATETIME  DEFAULT '9999-12-31 23:59:59' COMMENT 'Expiration date of URL',
    `is_click_limited`      BOOLEAN   DEFAULT 0 COMMENT '0: Not limited, 1: Click limited',
    `click_limit`           INT NULL DEFAULT 0 COMMENT 'Click limit of URL',
    `editable`              BOOLEAN   DEFAULT 0 COMMENT '0: Editable, 1: Not editable',
    `is_edited`             BOOLEAN   DEFAULT 0 COMMENT '0: Not edited, 1: Edited',
    `is_password_protected` BOOLEAN   DEFAULT 0 COMMENT '0: Not password protected, 1: Password protected',
    `show_original_url`     BOOLEAN   DEFAULT 0 COMMENT '0: Show original URL, 1: Hide original URL',
    `click_count`           INT NULL DEFAULT 0 COMMENT 'Clicked count',
    `has_qr_code`           BOOLEAN   DEFAULT 0 COMMENT '0: No QR Code, 1: Has QR Code',
    `created`               TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Created date and time',
    `modified`              TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
    UNIQUE INDEX `short_url_UNIQUE` (`short_url` ASC) VISIBLE,
    CONSTRAINT `j_urls_users_id_FK`
        FOREIGN KEY (`user_id`)
            REFERENCES `j_users` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='URL table';

-- j_click_records table
CREATE TABLE `j_click_records`
(
    `id`                     BIGINT      NOT NULL AUTO_INCREMENT,
    `url_id`                 VARCHAR(64) NOT NULL,
    `click_time`             TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `ip`                     VARCHAR(45) NULL,
    `browser`                VARCHAR(256) NULL,
    `browser_type`           VARCHAR(256) NULL,
    `browser_major_version`  VARCHAR(256) NULL,
    `device_type`            VARCHAR(256) NULL,
    `platform`               VARCHAR(256) NULL,
    `platform_version`       VARCHAR(256) NULL,
    `platform_maker`         VARCHAR(256) NULL,
    `rendering_engine_maker` VARCHAR(256) NULL,
    `language`               VARCHAR(256) NULL,
    `geolocation`            VARCHAR(512) NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `j_click_records_urls_id_FK`
        FOREIGN KEY (`url_id`)
            REFERENCES `j_urls` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='URL Clicked history table';

-- #################################
-- ##              Tag            ##
-- #################################

-- j_tag table
CREATE TABLE `j_tag`
(
    `id`      BIGINT       NOT NULL AUTO_INCREMENT,
    `name`    VARCHAR(256) NOT NULL COMMENT 'Tag name',
    `user_id` VARCHAR(64)  NOT NULL COMMENT 'User ID',
    PRIMARY KEY (`id`),
    CONSTRAINT `j_tag_user_id_FK`
        FOREIGN KEY (`user_id`)
            REFERENCES `j_users` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='Tag of user table';

-- j_url_tag table
CREATE TABLE `j_url_tag`
(
    `id`     BIGINT      NOT NULL AUTO_INCREMENT,
    `url_id` VARCHAR(64) NOT NULL,
    `tag_id` BIGINT      NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `j_url_tag_url_id_FK`
        FOREIGN KEY (`url_id`)
            REFERENCES `j_urls` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `j_url_tag_tag_id_FK`
        FOREIGN KEY (`tag_id`)
            REFERENCES `j_tag` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='Tag of URL table';

-- #################################
-- ##            QR Code          ##
-- #################################

-- j_qr_codes table
CREATE TABLE `j_qr_codes`
(
    `id`        BIGINT       NOT NULL AUTO_INCREMENT,
    `user_id`   VARCHAR(64)  NOT NULL COMMENT 'User ID',
    `url_id`    VARCHAR(64)  NOT NULL,
    `file_name` VARCHAR(255) NOT NULL COMMENT 'QR Code file name',
    `file_path` VARCHAR(255) NOT NULL COMMENT 'QR Code file path',
    `file_type` VARCHAR(10)  NOT NULL COMMENT 'QR Code file type',
    `width`     INT          NOT NULL COMMENT 'Width of QR Code file',
    `height`    INT          NOT NULL COMMENT 'Height of QR Code file',
    `icon_path` VARCHAR(255) NULL COMMENT 'Icon file path',
    `created`   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `modified`  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    INDEX       `j_qr_codes_url_id_FK_idx` (`url_id` ASC) VISIBLE,
    CONSTRAINT `j_qr_codes_url_id_FK`
        FOREIGN KEY (`url_id`)
            REFERENCES `j_urls` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci AUTO_INCREMENT = 1000000 COMMENT='QR Code table';

-- #################################
-- ##      Short URL Domains      ##
-- #################################

-- j_domains table
CREATE TABLE `j_domains` (
    `id`         BIGINT NOT NULL AUTO_INCREMENT,
    `user_id`    VARCHAR(64) NOT NULL COMMENT 'User ID',
    `name`       VARCHAR(256) NOT NULL COMMENT 'Domain URL',
    `is_active`  BOOLEAN DEFAULT 0 COMMENT '0: active, 1: inactive',
    `created`    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `modified`   TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `domains_name_UNIQUE` (`name` ASC) VISIBLE,
    CONSTRAINT `j_domains_users_id_FK`
        FOREIGN KEY (`user_id`)
            REFERENCES `j_users` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4 COLLATE = UTF8MB4_GENERAL_CI COMMENT='Short URL Domains';
