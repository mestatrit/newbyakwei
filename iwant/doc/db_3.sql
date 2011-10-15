ALTER TABLE iwant.project ADD COLUMN path VARCHAR(200) NOT NULL AFTER did;

ALTER TABLE iwant.project ADD COLUMN rongjilv VARCHAR(50) NOT NULL AFTER path, ADD COLUMN lvhualv VARCHAR(50) NOT NULL AFTER rongjilv, ADD COLUMN mrate VARCHAR(50) NOT NULL AFTER lvhualv, ADD COLUMN traffic VARCHAR(300) NOT NULL AFTER mrate, ADD COLUMN neardescr VARCHAR(500) NOT NULL AFTER traffic;

ALTER TABLE iwant.project ADD COLUMN buildtime VARCHAR(50) NOT NULL AFTER neardescr, ADD COLUMN carspace VARCHAR(50) NOT NULL AFTER buildtime;

ALTER TABLE iwant.project ADD COLUMN orderflg INTEGER UNSIGNED NOT NULL AFTER mtype, DROP INDEX Index_2, ADD INDEX Index_2 USING BTREE(did, orderflg);

ALTER TABLE iwant.slide DROP COLUMN pptid,DROP COLUMN descr, DROP INDEX Index_2, ADD INDEX Index_3(projectid);

ALTER TABLE iwant.project ADD COLUMN buildtype VARCHAR(50) NOT NULL AFTER carspace;

ALTER TABLE iwant.project ADD COLUMN mtype VARCHAR(50) NOT NULL AFTER buildtype;

ALTER TABLE iwant.project DROP COLUMN catid;

DROP TABLE IF EXISTS mainppt;
DROP TABLE IF EXISTS followproject;
DROP TABLE IF EXISTS ppt;
DROP TABLE IF EXISTS pptidcreator;
DROP TABLE IF EXISTS pptqueue;
DROP TABLE IF EXISTS ppttimeline;
DROP TABLE IF EXISTS projectfans;
DROP TABLE IF EXISTS usernotice;

