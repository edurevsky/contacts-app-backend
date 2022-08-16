CREATE TABLE users_roles
(
    `role_id` TINYINT NOT NULL,
    `user_id` BIGINT  NOT NULL,
    FOREIGN KEY (`role_id`) REFERENCES roles (`id`),
    FOREIGN KEY (`user_id`) REFERENCES users (`id`)
);