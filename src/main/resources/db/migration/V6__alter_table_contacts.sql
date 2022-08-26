ALTER TABLE contacts
ADD COLUMN `fav` BOOLEAN;

UPDATE contacts
SET `fav` = FALSE WHERE `fav` IS NULL;