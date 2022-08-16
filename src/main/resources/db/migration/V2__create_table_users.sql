CREATE TABLE users
(
    `id`       BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    `username` VARCHAR(255)                      NOT NULL,
    `password` VARCHAR(255)                      NOT NULL
);