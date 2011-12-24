package com.hk.svr;

import java.util.List;

import com.hk.bean.taobao.Tb_Shop;

public interface Tb_ShopService {

	void createTb_Shop(Tb_Shop tbShop);

	void updateTb_Shop(Tb_Shop tbShop);

	Tb_Shop getTb_Shop(long sid);

	Tb_Shop getTb_ShopByTb_sid(long sid);

	void deleteTb_Shop(long sid);

	void addCmt_num(long sid, int add);

	/**
	 * 评论多到少
	 * 
	 * @param begin
	 * @param size
	 * @return
	 *         2010-9-6
	 */
	List<Tb_Shop> getTb_ShopListForHuo(int begin, int size);
}