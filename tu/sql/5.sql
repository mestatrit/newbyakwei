ALTER TABLE tuxiazi_feed.notice ADD COLUMN senderid BIGINT UNSIGNED NOT NULL  AFTER refoid ;
ALTER TABLE tuxiazi.photo DROP COLUMN privacy_flg ;
ALTER TABLE tuxiazi.user_photo DROP COLUMN privacy_flg ;
ALTER TABLE tuxiazi.photo_userlike ADD COLUMN createtime DATETIME NOT NULL  AFTER oid ;
ALTER TABLE tuxiazi.photo DROP COLUMN recentcmtdata;

CREATE  TABLE tuxiazi.new_table (
  oid BIGINT NOT NULL ,
  userid BIGINT NOT NULL ,
  photoid BIGINT NOT NULL ,
  createtime DATETIME NOT NULL ,
  PRIMARY KEY (oid) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;
