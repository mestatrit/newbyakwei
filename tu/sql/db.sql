DROP TABLE IF EXISTS tuxiazi.api_user;
CREATE TABLE  tuxiazi.api_user (
  oid bigint(20) unsigned NOT NULL auto_increment,
  userid bigint(20) unsigned NOT NULL,
  api_type tinyint(3) unsigned NOT NULL,
  PRIMARY KEY  (oid),
  KEY Index_2 (userid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS tuxiazi.api_user_sina;
CREATE TABLE  tuxiazi.api_user_sina (
  sina_userid bigint(20) unsigned NOT NULL,
  access_token varchar(50) NOT NULL,
  token_secret varchar(50) NOT NULL,
  userid bigint(20) unsigned NOT NULL,
  PRIMARY KEY  (sina_userid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS tuxiazi.fans;
CREATE TABLE  tuxiazi.fans (
  oid bigint(20) unsigned NOT NULL auto_increment,
  userid bigint(20) unsigned NOT NULL,
  fansid bigint(20) unsigned NOT NULL,
  flg tinyint(1) unsigned NOT NULL,
  PRIMARY KEY  (oid),
  KEY Index_2 (userid)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS tuxiazi.friend;
CREATE TABLE  tuxiazi.friend (
  oid bigint(20) unsigned NOT NULL auto_increment,
  userid bigint(20) unsigned NOT NULL,
  friendid bigint(20) unsigned NOT NULL,
  flg tinyint(1) unsigned NOT NULL,
  PRIMARY KEY  (oid),
  KEY Index_2 (userid)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS tuxiazi.hotphoto;
CREATE TABLE  tuxiazi.hotphoto (
  oid bigint(20) unsigned NOT NULL auto_increment,
  photoid bigint(20) unsigned NOT NULL,
  path varchar(200) NOT NULL,
  PRIMARY KEY  (oid)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS tuxiazi.invitelog;
CREATE TABLE  tuxiazi.invitelog (
  logid bigint(20) unsigned NOT NULL auto_increment,
  userid bigint(20) unsigned NOT NULL,
  otherid varchar(45) NOT NULL,
  createtime datetime NOT NULL,
  api_type tinyint(3) unsigned NOT NULL,
  PRIMARY KEY  (logid),
  KEY Index_2 (userid)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS tuxiazi.lasted_photo;
CREATE TABLE  tuxiazi.lasted_photo (
  photoid bigint(20) unsigned NOT NULL,
  PRIMARY KEY  (photoid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS tuxiazi.photo;
CREATE TABLE  tuxiazi.photo (
  photoid bigint(20) unsigned NOT NULL,
  name varchar(50) default NULL,
  intro varchar(500) default NULL,
  userid bigint(20) unsigned NOT NULL,
  path varchar(200) NOT NULL,
  create_time datetime NOT NULL,
  privacy_flg tinyint(1) unsigned NOT NULL,
  cmt_num int(10) unsigned NOT NULL,
  recentcmtdata text,
  like_num int(10) unsigned NOT NULL,
  like_user varchar(800) default NULL,
  PRIMARY KEY  (photoid),
  KEY Index_2 (like_num)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS tuxiazi.photo_likeuser;
CREATE TABLE  tuxiazi.photo_likeuser (
  oid bigint(20) unsigned NOT NULL auto_increment,
  photoid bigint(20) unsigned NOT NULL,
  userid bigint(20) unsigned NOT NULL,
  PRIMARY KEY  (oid),
  UNIQUE KEY Index_2 (photoid,userid)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS tuxiazi.photo_userlike;
CREATE TABLE  tuxiazi.photo_userlike (
  photoid bigint(20) unsigned NOT NULL,
  userid bigint(20) unsigned NOT NULL,
  oid bigint(20) unsigned NOT NULL auto_increment,
  PRIMARY KEY  (oid),
  KEY Index_2 (userid,photoid)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS tuxiazi.photocmt;
CREATE TABLE  tuxiazi.photocmt (
  cmtid bigint(20) unsigned NOT NULL,
  photoid bigint(20) unsigned NOT NULL,
  userid bigint(20) unsigned NOT NULL,
  content varchar(300) NOT NULL,
  create_time datetime NOT NULL,
  replyuserid bigint(20) unsigned NOT NULL,
  PRIMARY KEY  (cmtid),
  KEY Index_2 (photoid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS tuxiazi.photocmtid;
CREATE TABLE  tuxiazi.photocmtid (
  cmtid bigint(20) unsigned NOT NULL auto_increment,
  PRIMARY KEY  (cmtid)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS tuxiazi.photoid;
CREATE TABLE  tuxiazi.photoid (
  photoid bigint(20) unsigned NOT NULL auto_increment,
  PRIMARY KEY  (photoid)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS tuxiazi.user;
CREATE TABLE  tuxiazi.user (
  userid bigint(20) unsigned NOT NULL,
  nick varchar(50) NOT NULL,
  head_path varchar(200) default NULL,
  pic_num int(10) unsigned NOT NULL,
  create_time datetime NOT NULL,
  fans_num int(10) unsigned NOT NULL,
  friend_num int(10) unsigned NOT NULL,
  PRIMARY KEY  (userid),
  KEY Index_2 (nick)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS tuxiazi.user_photo;
CREATE TABLE  tuxiazi.user_photo (
  oid bigint(20) unsigned NOT NULL auto_increment,
  userid bigint(20) unsigned NOT NULL,
  photoid bigint(20) unsigned NOT NULL,
  privacy_flg tinyint(1) unsigned NOT NULL,
  PRIMARY KEY  USING BTREE (oid),
  KEY Index_2 (userid)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS tuxiazi.userid;
CREATE TABLE  tuxiazi.userid (
  userid bigint(20) unsigned NOT NULL auto_increment,
  PRIMARY KEY  (userid)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS tuxiazi_feed.friend_photo_feed;
CREATE TABLE  tuxiazi_feed.friend_photo_feed (
  feedid bigint(20) unsigned NOT NULL auto_increment,
  photoid bigint(20) unsigned NOT NULL,
  userid bigint(20) unsigned NOT NULL,
  create_time datetime NOT NULL,
  photo_userid bigint(20) unsigned NOT NULL,
  PRIMARY KEY  (feedid),
  KEY Index_2 (userid)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS tuxiazi_feed.notice;
CREATE TABLE  tuxiazi_feed.notice (
  noticeid bigint(20) unsigned NOT NULL auto_increment,
  userid bigint(20) unsigned NOT NULL,
  notice_flg tinyint(1) unsigned NOT NULL,
  readflg tinyint(1) unsigned NOT NULL,
  data varchar(300) default NULL,
  createtime datetime NOT NULL,
  PRIMARY KEY  (noticeid),
  KEY Index_2 (userid)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;
