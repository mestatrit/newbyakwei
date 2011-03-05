ALTER TABLE tuxiazi.photo ADD COLUMN privacy_flg TINYINT(1) UNSIGNED NOT NULL AFTER create_time;
ALTER TABLE tuxiazi.user_photo ADD COLUMN privacy_flg TINYINT(1) UNSIGNED NOT NULL AFTER photoid;
