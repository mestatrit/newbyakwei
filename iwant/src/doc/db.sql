DROP TABLE IF EXISTS iwant.category;
CREATE TABLE  iwant.category (
  catid int(10) unsigned NOT NULL auto_increment,
  name varchar(45) NOT NULL,
  order_flag int(10) unsigned NOT NULL,
  PRIMARY KEY  (catid)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS iwant.city;
CREATE TABLE  iwant.city (
  cityid int(10) unsigned NOT NULL auto_increment,
  countryid int(10) unsigned NOT NULL default '0',
  provinceid int(10) unsigned NOT NULL default '0',
  name varchar(50) default NULL,
  PRIMARY KEY  USING BTREE (cityid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS iwant.country;
CREATE TABLE  iwant.country (
  countryid int(10) unsigned NOT NULL auto_increment,
  name varchar(50) default NULL,
  PRIMARY KEY  USING BTREE (countryid)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS iwant.followproject;
CREATE TABLE  iwant.followproject (
  sysid bigint(20) unsigned NOT NULL auto_increment,
  userid bigint(20) unsigned NOT NULL,
  projectid bigint(20) unsigned NOT NULL,
  PRIMARY KEY  (sysid),
  KEY Index_2 (userid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS iwant.mainppt;
CREATE TABLE  iwant.mainppt (
  pptid bigint(20) unsigned NOT NULL auto_increment,
  projectid bigint(20) unsigned NOT NULL,
  name varchar(100) NOT NULL,
  pic_path varchar(200) NOT NULL,
  createtime datetime NOT NULL,
  order_flag bigint(20) unsigned NOT NULL,
  active_flag tinyint(1) unsigned NOT NULL,
  catid int(10) unsigned NOT NULL,
  PRIMARY KEY  (pptid),
  KEY Index_2 (projectid)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS iwant.notice;
CREATE TABLE  iwant.notice (
  noticeid bigint(20) unsigned NOT NULL,
  content varchar(500) NOT NULL,
  projectid bigint(20) unsigned NOT NULL,
  createtime datetime NOT NULL,
  pptid bigint(20) unsigned NOT NULL,
  PRIMARY KEY  (noticeid),
  KEY Index_2 (projectid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS iwant.noticeidcreator;
CREATE TABLE  iwant.noticeidcreator (
  noticeid int(10) unsigned NOT NULL auto_increment,
  PRIMARY KEY  (noticeid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS iwant.noticequeue;
CREATE TABLE  iwant.noticequeue (
  noticeid bigint(20) unsigned NOT NULL,
  PRIMARY KEY  (noticeid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS iwant.ppt;
CREATE TABLE  iwant.ppt (
  pptid bigint(20) unsigned NOT NULL,
  projectid bigint(20) unsigned NOT NULL,
  name varchar(100) NOT NULL,
  pic_path varchar(200) NOT NULL,
  createtime datetime NOT NULL,
  PRIMARY KEY  (pptid),
  KEY Index_2 (projectid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS iwant.pptidcreator;
CREATE TABLE  iwant.pptidcreator (
  pptid bigint(20) unsigned NOT NULL auto_increment,
  PRIMARY KEY  (pptid)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS iwant.pptqueue;
CREATE TABLE  iwant.pptqueue (
  pptid bigint(20) unsigned NOT NULL,
  projectid bigint(20) unsigned NOT NULL,
  PRIMARY KEY  (pptid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS iwant.ppttimeline;
CREATE TABLE  iwant.ppttimeline (
  sysid bigint(20) unsigned NOT NULL auto_increment,
  pptid bigint(20) unsigned NOT NULL,
  projectid bigint(20) unsigned NOT NULL,
  userid bigint(20) unsigned NOT NULL,
  read_flag tinyint(1) unsigned NOT NULL,
  createtime datetime NOT NULL,
  readtime datetime NOT NULL,
  catid int(10) unsigned NOT NULL,
  PRIMARY KEY  (sysid),
  KEY Index_2 (userid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS iwant.project;
CREATE TABLE  iwant.project (
  projectid bigint(20) unsigned NOT NULL,
  name varchar(100) NOT NULL,
  descr varchar(500) NOT NULL,
  catid int(10) unsigned NOT NULL,
  addr varchar(200) NOT NULL,
  order_flag bigint(20) unsigned NOT NULL,
  active_flag tinyint(1) unsigned NOT NULL,
  tel varchar(45) NOT NULL,
  markerx double NOT NULL,
  markery double NOT NULL,
  fans_num int(10) unsigned NOT NULL,
  createtime datetime NOT NULL,
  PRIMARY KEY  (projectid),
  KEY Index_2 (catid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS iwant.projectfans;
CREATE TABLE  iwant.projectfans (
  sysid bigint(20) unsigned NOT NULL auto_increment,
  projectid bigint(20) unsigned NOT NULL,
  userid bigint(20) unsigned NOT NULL,
  PRIMARY KEY  (sysid),
  KEY Index_2 (projectid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS iwant.projectidcreator;
CREATE TABLE  iwant.projectidcreator (
  projectid bigint(20) unsigned NOT NULL auto_increment,
  PRIMARY KEY  (projectid)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS iwant.projectrecycle;
CREATE TABLE  iwant.projectrecycle (
  projectid bigint(20) unsigned NOT NULL,
  PRIMARY KEY  (projectid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS iwant.province;
CREATE TABLE  iwant.province (
  provinceid int(10) unsigned NOT NULL auto_increment,
  countryid int(10) unsigned NOT NULL default '0',
  name varchar(50) default NULL,
  PRIMARY KEY  USING BTREE (provinceid)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS iwant.slide;
CREATE TABLE  iwant.slide (
  slideid bigint(20) unsigned NOT NULL auto_increment,
  pptid bigint(20) unsigned NOT NULL,
  title varchar(100) NOT NULL,
  subtitle varchar(100) NOT NULL,
  descr varchar(500) NOT NULL,
  pic_path varchar(200) NOT NULL,
  projectid bigint(20) unsigned NOT NULL,
  order_flag int(10) unsigned NOT NULL,
  PRIMARY KEY  (slideid),
  KEY Index_2 (pptid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS iwant.user;
CREATE TABLE  iwant.user (
  userid bigint(20) unsigned NOT NULL,
  device_token varchar(80) NOT NULL,
  email varchar(45) NOT NULL,
  gender tinyint(1) unsigned NOT NULL,
  name varchar(45) NOT NULL,
  mobile varchar(11) NOT NULL,
  createtime datetime NOT NULL,
  PRIMARY KEY  (userid),
  UNIQUE KEY Index_2 USING BTREE (device_token)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS iwant.useridcreator;
CREATE TABLE  iwant.useridcreator (
  userid bigint(20) unsigned NOT NULL auto_increment,
  PRIMARY KEY  (userid)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS iwant.usernotice;
CREATE TABLE  iwant.usernotice (
  sysid bigint(20) unsigned NOT NULL auto_increment,
  noticeid bigint(20) unsigned NOT NULL,
  userid bigint(20) unsigned NOT NULL,
  PRIMARY KEY  (sysid),
  KEY Index_2 (userid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;