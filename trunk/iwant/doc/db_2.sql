ALTER TABLE iwant.city ADD COLUMN hide_flg integer default 0 AFTER order_flg;
ALTER TABLE iwant.district ADD COLUMN hide_flg integer default 0 AFTER name;
ALTER TABLE iwant.province ADD COLUMN hide_flg integer default 0 AFTER order_flg;

update iwant.city set hide_flg=0;
update iwant.district set hide_flg=0;
update iwant.province set hide_flg=0;