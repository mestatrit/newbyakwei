package com.hk.svr;

import java.util.List;

import com.hk.bean.taobao.Tb_Item_Cat;

/**
 * @author akwei
 */
public interface Tb_Item_CatService {

	void createTb_Item_Cat(Tb_Item_Cat tbItemCat);

	void updateTb_Item_Cat(Tb_Item_Cat tbItemCat);

	void deleteTb_Item_Cat(long cid);

	Tb_Item_Cat getTb_Item_Cat(long cid);

	/**
	 * @param parent_cid 如果为0 则获取根分类
	 * @return
	 *         2010-8-27
	 */
	List<Tb_Item_Cat> getTb_Item_CatListByParent_cid(long parent_cid);

	List<Tb_Item_Cat> getTb_Item_CatListInId(List<Long> idList);

	List<Tb_Item_Cat> getTb_Item_CatListForNoDeal(int begin, int size);
}