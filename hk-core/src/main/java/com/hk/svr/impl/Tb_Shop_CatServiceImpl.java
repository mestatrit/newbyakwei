package com.hk.svr.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.taobao.Tb_Shop_Cat;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.Tb_Shop_CatService;

public class Tb_Shop_CatServiceImpl implements Tb_Shop_CatService {

	@Autowired
	private QueryManager manager;

	@Override
	public void createTb_Shop_Cat(Tb_Shop_Cat tbShopCat) {
		Query query = manager.createQuery();
		query.insertObject(tbShopCat);
	}

	@Override
	public void deleteTb_Shop_Cat(long cid) {
		Query query = manager.createQuery();
		query.deleteById(Tb_Shop_Cat.class, cid);
	}

	@Override
	public Tb_Shop_Cat getTb_Shop_Cat(long cid) {
		Query query = manager.createQuery();
		return query.getObjectById(Tb_Shop_Cat.class, cid);
	}

	@Override
	public List<Tb_Shop_Cat> getTb_Shop_CatList(long parentCid) {
		Query query = manager.createQuery();
		return query.listEx(Tb_Shop_Cat.class, "parent_cid=?",
				new Object[] { parentCid }, "cid desc");
	}

	@Override
	public void updateTb_Shop_Cat(Tb_Shop_Cat tbShopCat) {
		Query query = manager.createQuery();
		query.updateObject(tbShopCat);
	}
}
