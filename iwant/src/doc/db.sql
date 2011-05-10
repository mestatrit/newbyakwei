-- MySQL dump 10.11
--
-- Host: localhost    Database: iwant
-- ------------------------------------------------------
-- Server version	5.0.85

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `category`
--
USE iwant;

DROP TABLE IF EXISTS category;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE category (
  catid int(10) unsigned NOT NULL auto_increment,
  name varchar(45) NOT NULL,
  order_flag int(10) unsigned NOT NULL,
  PRIMARY KEY  (catid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table city
--

DROP TABLE IF EXISTS city;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE city (
  cityid int(10) unsigned NOT NULL auto_increment,
  countryid int(10) unsigned NOT NULL default '0',
  provinceid int(10) unsigned NOT NULL default '0',
  name varchar(50) default NULL,
  order_flg int(10) unsigned NOT NULL,
  PRIMARY KEY  USING BTREE (cityid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table country
--

DROP TABLE IF EXISTS country;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE country (
  countryid int(10) unsigned NOT NULL auto_increment,
  name varchar(50) default NULL,
  PRIMARY KEY  USING BTREE (countryid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table district
--

DROP TABLE IF EXISTS district;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE district (
  did int(11) NOT NULL auto_increment,
  cityid int(10) unsigned NOT NULL default '0',
  countryid int(10) unsigned NOT NULL default '0',
  provinceid int(10) unsigned NOT NULL default '0',
  name varchar(50) default NULL,
  PRIMARY KEY  USING BTREE (did)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table followproject
--

DROP TABLE IF EXISTS followproject;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE followproject (
  sysid bigint(20) unsigned NOT NULL auto_increment,
  userid bigint(20) unsigned NOT NULL,
  projectid bigint(20) unsigned NOT NULL,
  PRIMARY KEY  (sysid),
  KEY Index_2 (userid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table mainppt
--

DROP TABLE IF EXISTS mainppt;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE mainppt (
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table notice
--

DROP TABLE IF EXISTS notice;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE notice (
  noticeid bigint(20) unsigned NOT NULL,
  content varchar(500) NOT NULL,
  projectid bigint(20) unsigned NOT NULL,
  createtime datetime NOT NULL,
  pptid bigint(20) unsigned NOT NULL,
  PRIMARY KEY  (noticeid),
  KEY Index_2 (projectid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table noticeidcreator
--

DROP TABLE IF EXISTS noticeidcreator;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE noticeidcreator (
  noticeid int(10) unsigned NOT NULL auto_increment,
  PRIMARY KEY  (noticeid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table noticequeue
--

DROP TABLE IF EXISTS noticequeue;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE noticequeue (
  noticeid bigint(20) unsigned NOT NULL,
  PRIMARY KEY  (noticeid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table ppt
--

DROP TABLE IF EXISTS ppt;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE ppt (
  pptid bigint(20) unsigned NOT NULL,
  projectid bigint(20) unsigned NOT NULL,
  name varchar(100) NOT NULL,
  pic_path varchar(200) NOT NULL,
  createtime datetime NOT NULL,
  PRIMARY KEY  (pptid),
  KEY Index_2 (projectid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table pptidcreator
--

DROP TABLE IF EXISTS pptidcreator;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE pptidcreator (
  pptid bigint(20) unsigned NOT NULL auto_increment,
  PRIMARY KEY  (pptid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table pptqueue
--

DROP TABLE IF EXISTS pptqueue;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE pptqueue (
  pptid bigint(20) unsigned NOT NULL,
  projectid bigint(20) unsigned NOT NULL,
  PRIMARY KEY  (pptid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table ppttimeline
--

DROP TABLE IF EXISTS ppttimeline;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE ppttimeline (
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table project
--

DROP TABLE IF EXISTS project;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE project (
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
  cityid int(10) unsigned NOT NULL,
  PRIMARY KEY  (projectid),
  KEY Index_2 USING BTREE (catid,cityid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table projectfans
--

DROP TABLE IF EXISTS projectfans;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE projectfans (
  sysid bigint(20) unsigned NOT NULL auto_increment,
  projectid bigint(20) unsigned NOT NULL,
  userid bigint(20) unsigned NOT NULL,
  PRIMARY KEY  (sysid),
  KEY Index_2 (projectid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table projectidcreator
--

DROP TABLE IF EXISTS projectidcreator;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE projectidcreator (
  projectid bigint(20) unsigned NOT NULL auto_increment,
  PRIMARY KEY  (projectid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table projectrecycle
--

DROP TABLE IF EXISTS projectrecycle;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE projectrecycle (
  projectid bigint(20) unsigned NOT NULL,
  PRIMARY KEY  (projectid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table province
--

DROP TABLE IF EXISTS province;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE province (
  provinceid int(10) unsigned NOT NULL auto_increment,
  countryid int(10) unsigned NOT NULL default '0',
  name varchar(50) default NULL,
  order_flg int(10) unsigned NOT NULL,
  PRIMARY KEY  USING BTREE (provinceid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table slide
--

DROP TABLE IF EXISTS slide;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE slide (
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table user
--

DROP TABLE IF EXISTS user;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE user (
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table useridcreator
--

DROP TABLE IF EXISTS useridcreator;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE useridcreator (
  userid bigint(20) unsigned NOT NULL auto_increment,
  PRIMARY KEY  (userid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table usernotice
--

DROP TABLE IF EXISTS usernotice;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE usernotice (
  sysid bigint(20) unsigned NOT NULL auto_increment,
  noticeid bigint(20) unsigned NOT NULL,
  userid bigint(20) unsigned NOT NULL,
  PRIMARY KEY  (sysid),
  KEY Index_2 (userid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-05-10 18:16:03
