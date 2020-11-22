DROP DATABASE IF EXISTS `1284-stream`;
CREATE DATABASE `1284-stream`;
USE `1284-stream`;

DROP TABLE IF EXISTS mooc_users;
CREATE TABLE mooc_users
(
    id            BIGINT       NOT NULL AUTO_INCREMENT,
    email         VARCHAR(255) NOT NULL,
    enabled       BIT          NOT NULL,
    mobile        VARCHAR(11)  NOT NULL,
    name          VARCHAR(50)  NOT NULL,
    password_hash VARCHAR(80)  NOT NULL,
    username      VARCHAR(50)  NOT NULL,
    age           INT          NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_mooc_users_username UNIQUE (username),
    CONSTRAINT uk_mooc_users_mobile UNIQUE (mobile),
    CONSTRAINT uk_mooc_users_email UNIQUE (email)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

INSERT INTO mooc_users(username, name, password_hash, email, mobile, age, enabled)
VALUES ('zhangsan', '张三', '1234', 'zhangsan@local.dev', '13000000001', 35, TRUE),
       ('lisi', '李四', '1234', 'lisi@local.dev', '13000000002', 32, TRUE),
       ('wangwu', '王五', '1234', 'wangwu@local.dev', '13100000001', 41, TRUE);
