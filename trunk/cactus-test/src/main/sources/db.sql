create database if not exists test0;
create database if not exists test1;

DROP TABLE IF EXISTS test0.member;
CREATE TABLE  test0.member (
  userid bigint(20) unsigned NOT NULL auto_increment,
  nick varchar(45) NOT NULL,
  groupid bigint(20) unsigned NOT NULL,
  PRIMARY KEY  (userid)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS test0.member0;
CREATE TABLE  test0.member0 (
  memberuserid bigint(20) unsigned NOT NULL auto_increment,
  nick varchar(45) NOT NULL,
  groupid bigint(20) unsigned NOT NULL,
  PRIMARY KEY  USING BTREE (memberuserid)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS test0.testuser0;
CREATE TABLE  test0.testuser0 (
  userid bigint(20) unsigned NOT NULL auto_increment,
  nick varchar(45) NOT NULL,
  createtime datetime NOT NULL,
  money double NOT NULL,
  purchase double NOT NULL,
  gender tinyint(1) unsigned NOT NULL,
  PRIMARY KEY  (userid)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS test1.member1;
CREATE TABLE  test1.member1 (
  memberuserid bigint(20) unsigned NOT NULL auto_increment,
  nick varchar(45) NOT NULL,
  groupid bigint(20) unsigned NOT NULL,
  PRIMARY KEY  USING BTREE (memberuserid)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS test1.testuser1;
CREATE TABLE  test1.testuser1 (
  userid bigint(20) unsigned NOT NULL auto_increment,
  nick varchar(45) NOT NULL,
  createtime datetime NOT NULL,
  money double NOT NULL,
  purchase double NOT NULL,
  gender tinyint(1) unsigned NOT NULL,
  PRIMARY KEY  (userid)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;