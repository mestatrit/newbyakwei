package com.hk.svr;

import java.util.List;

import com.hk.bean.taobao.Tb_Shop_Cat;

public interface Tb_Shop_CatService {

	void createTb_Shop_Cat(Tb_Shop_Cat tbShopCat);

	void updateTb_Shop_Cat(Tb_Shop_Cat tbShopCat);

	void deleteTb_Shop_Cat(long cid);

	Tb_Shop_Cat getTb_Shop_Cat(long cid);

	/**
	 * 获取店铺分类
	 * 
	 * @param parent_cid 为0时，取根目录分类
	 * @return
	 *         2010-9-6
	 */
	List<Tb_Shop_Cat> getTb_Shop_CatList(long parent_cid);
}