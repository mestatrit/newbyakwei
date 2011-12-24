package com.hk.svr.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.taobao.Tb_Item_Cat;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.Tb_Item_CatService;

public class Tb_Item_CatServiceImpl implements Tb_Item_CatService {

	@Autowired
	private QueryManager manager;

	public void createTb_Item_Cat(Tb_Item_Cat tbItemCat) {
		Query query = this.manager.createQuery();
		query.insertObject(tbItemCat);
	}

	public void deleteTb_Item_Cat(long cid) {
		Query query = this.manager.createQuery();
		query.deleteById(Tb_Item_Cat.class, cid);
	}

	public Tb_Item_Cat getTb_Item_Cat(long cid) {
		Query query = this.manager.createQuery();
		return query.getObjectById(Tb_Item_Cat.class, cid);
	}

	public List<Tb_Item_Cat> getTb_Item_CatListByParent_cid(long parentCid) {
		Query query = this.manager.createQuery();
		return query.listEx(Tb_Item_Cat.class, "parent_cid=?",
				new Object[] { parentCid }, "cid asc");
	}

	public List<Tb_Item_Cat> getTb_Item_CatListInId(List<Long> idList) {
		Query query = this.manager.createQuery();
		return query.listInField(Tb_Item_Cat.class, null, null, "cid", idList,
				null);
	}

	public void updateTb_Item_Cat(Tb_Item_Cat tbItemCat) {
		Query query = this.manager.createQuery();
		query.updateObject(tbItemCat);
	}

	@Override
	public List<Tb_Item_Cat> getTb_Item_CatListForNoDeal(int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(Tb_Item_Cat.class,
				"child_update=? and parentflg=?", new Object[] { 0,
						Tb_Item_Cat.PARENTFLG_Y }, "cid asc");
	}
}