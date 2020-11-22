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
