CREATE TABLE contacts
(
    `id`      BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    `name`    VARCHAR(255)                      NOT NULL,
    `email`   VARCHAR(255)                      NOT NULL,
    `number`  VARCHAR(16)                       NOT NULL,
    `pic_url` VARCHAR(255)                      NOT NULL
);