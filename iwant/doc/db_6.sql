ALTER TABLE iwant.project CHANGE COLUMN order_flag order_flag BIGINT(20) UNSIGNED NOT NULL DEFAULT 0  ;
ALTER TABLE iwant.project DROP COLUMN buildtime ;
ALTER TABLE iwant.project DROP COLUMN order_flag ;
ALTER TABLE iwant.project CHANGE COLUMN orderflg orderflg INT(10) UNSIGNED NOT NULL DEFAULT 0  ;
ALTER TABLE iwant.project DROP COLUMN orderflg , DROP INDEX Index_2 , ADD INDEX Index_2 USING BTREE (did ASC) ;
