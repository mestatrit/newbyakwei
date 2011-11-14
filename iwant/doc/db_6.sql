ALTER TABLE iwant.project CHANGE COLUMN order_flag order_flag BIGINT(20) UNSIGNED NOT NULL DEFAULT 0  ;
ALTER TABLE iwant.project DROP COLUMN buildtime ;
ALTER TABLE iwant.project DROP COLUMN order_flag ;
