ALTER TABLE iwant.project ADD COLUMN cityid INTEGER UNSIGNED NOT NULL AFTER createtime, DROP INDEX Index_2, ADD INDEX Index_2 USING BTREE(catid, cityid);
ALTER TABLE iwant.mainppt ADD COLUMN cityid INTEGER UNSIGNED NOT NULL AFTER catid, ADD INDEX Index_3(cityid);
CREATE TABLE  iwant.district (
  did int(10) NOT NULL auto_increment,
  cityid int(10) unsigned NOT NULL default '0',
  countryid int(10) unsigned NOT NULL default '0',
  provinceid int(10) unsigned NOT NULL default '0',
  name varchar(50) default NULL,
  PRIMARY KEY  USING BTREE (did)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
