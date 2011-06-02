ALTER TABLE iwant.city ADD COLUMN hide_flg integer  AFTER order_flg;
ALTER TABLE iwant.district ADD COLUMN hide_flg integer  AFTER name;
ALTER TABLE iwant.province ADD COLUMN hide_flg integer  AFTER order_flg;