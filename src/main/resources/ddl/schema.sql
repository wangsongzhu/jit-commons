-- create database
CREATE SCHEMA jit DEFAULT CHARACTER SET utf8mb4;

-- create user
CREATE USER 'jitadmin'@'%' IDENTIFIED BY 'P@ssw0rd';
GRANT ALL PRIVILEGES ON jit.* TO 'jitadmin'@'%';
CREATE USER 'jitadmin'@'localhost' IDENTIFIED BY 'P@ssw0rd';
GRANT ALL PRIVILEGES ON jit.* TO 'jitadmin'@'localhost';
FLUSH PRIVILEGES;

use
jit;

--- j_users table
CREATE TABLE `j_users`
(
    `id`                VARCHAR(64)  NOT NULL,
    `email`             VARCHAR(254) NOT NULL,
    `password_hash`     VARCHAR(256) NOT NULL,
    `signup_date`       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `verified`          TINYINT NOT NULL DEFAULT 0,
    `last_login`        TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE
);





--- j_urls table
CREATE TABLE `j_urls`
(
    `id`                    VARCHAR(64) NOT NULL,
    `user_id`               VARCHAR(64) NOT NULL,
    `name`                  VARCHAR(256),
    `title`                 VARCHAR(256),
    `original_url`          TEXT        NOT NULL,
    `short_url`             VARCHAR(25) NOT NULL,
    `short_part`            VARCHAR(10) NOT NULL,
    `expiration_date`       DATETIME  DEFAULT '9999-12-31 23:59:59',
    `is_click_limited`      BOOLEAN   DEFAULT 0,
    `click_limit`           INT NULL DEFAULT 0,
    `editable`              BOOLEAN   DEFAULT 0,
    `is_password_protected` BOOLEAN   DEFAULT 0,
    `show_original_url`     BOOLEAN   DEFAULT 1,
    `created`               TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `click_count`           INT NULL DEFAULT 0,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
    UNIQUE INDEX `short_url_UNIQUE` (`short_url` ASC) VISIBLE,
    CONSTRAINT `j_users_id_FK`
        FOREIGN KEY (`user_id`)
            REFERENCES `j_users` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);

--- j_click_records table
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
    `rendering_engine_maker` VARCHAR(256) NULL,
    `platform_maker`         VARCHAR(256) NULL,
    `language`               VARCHAR(256) NULL,
    `geolocation`            VARCHAR(512) NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `j_urls_id_FK`
        FOREIGN KEY (`url_id`)
            REFERENCES `j_urls` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);

--- j_tag table
CREATE TABLE `j_tag`
(
    `id`      BIGINT       NOT NULL AUTO_INCREMENT,
    `name`    VARCHAR(256) NOT NULL,
    `user_id` VARCHAR(64)  NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `j_tag_user_id_FK`
        FOREIGN KEY (`user_id`)
            REFERENCES `j_users` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
) AUTO_INCREMENT = 1000000;

--- j_url_tag table
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
);

--- j_qr_codes table
CREATE TABLE `j_qr_codes`
(
    `id`        BIGINT       NOT NULL AUTO_INCREMENT,
    `url_id`    VARCHAR(64)  NOT NULL,
    `file_name` VARCHAR(255) NOT NULL,
    `file_path` VARCHAR(255) NOT NULL,
    `file_type` VARCHAR(10)  NOT NULL,
    `width`     INT          NOT NULL,
    `height`    INT          NOT NULL,
    `icon_path` VARCHAR(255) NULL,
    `created`   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    INDEX       `j_qr_codes_url_id_FK_idx` (`url_id` ASC) VISIBLE,
    CONSTRAINT `j_qr_codes_url_id_FK`
        FOREIGN KEY (`url_id`)
            REFERENCES `j_urls` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
) AUTO_INCREMENT = 1000000;;
