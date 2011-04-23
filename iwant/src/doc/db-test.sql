DROP TABLE IF EXISTS iwant_test.category;
CREATE TABLE  iwant_test.category (
  catid int(10) unsigned NOT NULL auto_increment,
  name varchar(45) NOT NULL,
  order_flag int(10) unsigned NOT NULL,
  PRIMARY KEY  (catid)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS iwant_test.followproject;
CREATE TABLE  iwant_test.followproject (
  sysid bigint(20) unsigned NOT NULL auto_increment,
  userid bigint(20) unsigned NOT NULL,
  projectid bigint(20) unsigned NOT NULL,
  PRIMARY KEY  (sysid),
  KEY Index_2 (userid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS iwant_test.mainppt;
CREATE TABLE  iwant_test.mainppt (
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
) ENGINE=InnoDB AUTO_INCREMENT=179 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS iwant_test.notice;
CREATE TABLE  iwant_test.notice (
  noticeid bigint(20) unsigned NOT NULL,
  content varchar(500) NOT NULL,
  projectid bigint(20) unsigned NOT NULL,
  createtime datetime NOT NULL,
  pptid bigint(20) unsigned NOT NULL,
  PRIMARY KEY  (noticeid),
  KEY Index_2 (projectid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS iwant_test.noticeidcreator;
CREATE TABLE  iwant_test.noticeidcreator (
  noticeid int(10) unsigned NOT NULL auto_increment,
  PRIMARY KEY  (noticeid)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS iwant_test.noticequeue;
CREATE TABLE  iwant_test.noticequeue (
  noticeid bigint(20) unsigned NOT NULL,
  PRIMARY KEY  (noticeid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS iwant_test.ppt;
CREATE TABLE  iwant_test.ppt (
  pptid bigint(20) unsigned NOT NULL,
  projectid bigint(20) unsigned NOT NULL,
  name varchar(100) NOT NULL,
  pic_path varchar(200) NOT NULL,
  createtime datetime NOT NULL,
  PRIMARY KEY  (pptid),
  KEY Index_2 (projectid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS iwant_test.pptidcreator;
CREATE TABLE  iwant_test.pptidcreator (
  pptid bigint(20) unsigned NOT NULL auto_increment,
  PRIMARY KEY  (pptid)
) ENGINE=InnoDB AUTO_INCREMENT=179 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS iwant_test.pptqueue;
CREATE TABLE  iwant_test.pptqueue (
  pptid bigint(20) unsigned NOT NULL,
  projectid bigint(20) unsigned NOT NULL,
  PRIMARY KEY  (pptid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS iwant_test.ppttimeline;
CREATE TABLE  iwant_test.ppttimeline (
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

DROP TABLE IF EXISTS iwant_test.project;
CREATE TABLE  iwant_test.project (
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

DROP TABLE IF EXISTS iwant_test.projectfans;
CREATE TABLE  iwant_test.projectfans (
  sysid bigint(20) unsigned NOT NULL auto_increment,
  projectid bigint(20) unsigned NOT NULL,
  userid bigint(20) unsigned NOT NULL,
  PRIMARY KEY  (sysid),
  KEY Index_2 (projectid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS iwant_test.projectidcreator;
CREATE TABLE  iwant_test.projectidcreator (
  projectid bigint(20) unsigned NOT NULL auto_increment,
  PRIMARY KEY  (projectid)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS iwant_test.projectrecycle;
CREATE TABLE  iwant_test.projectrecycle (
  projectid bigint(20) unsigned NOT NULL,
  PRIMARY KEY  (projectid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS iwant_test.slide;
CREATE TABLE  iwant_test.slide (
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
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS iwant_test.user;
CREATE TABLE  iwant_test.user (
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

DROP TABLE IF EXISTS iwant_test.useridcreator;
CREATE TABLE  iwant_test.useridcreator (
  userid bigint(20) unsigned NOT NULL auto_increment,
  PRIMARY KEY  (userid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS iwant_test.usernotice;
CREATE TABLE  iwant_test.usernotice (
  sysid bigint(20) unsigned NOT NULL auto_increment,
  noticeid bigint(20) unsigned NOT NULL,
  userid bigint(20) unsigned NOT NULL,
  PRIMARY KEY  (sysid),
  KEY Index_2 (userid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;