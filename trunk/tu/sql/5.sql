ALTER TABLE tuxiazi_feed.notice ADD COLUMN senderid BIGINT UNSIGNED NOT NULL  AFTER refoid ;
ALTER TABLE tuxiazi.photo DROP COLUMN privacy_flg ;
ALTER TABLE tuxiazi.user_photo DROP COLUMN privacy_flg ;
