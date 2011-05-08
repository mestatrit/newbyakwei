ALTER TABLE iwant.project ADD COLUMN cityid INTEGER UNSIGNED NOT NULL AFTER createtime, DROP INDEX Index_2, ADD INDEX Index_2 USING BTREE(catid, cityid);
ALTER TABLE iwant.mainppt ADD COLUMN cityid INTEGER UNSIGNED NOT NULL AFTER catid, ADD INDEX Index_3(cityid);
