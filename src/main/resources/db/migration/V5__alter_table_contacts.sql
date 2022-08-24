ALTER TABLE contacts
ADD COLUMN `user_id` BIGINT NOT NULL;

ALTER TABLE contacts
ADD CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES users (`id`);