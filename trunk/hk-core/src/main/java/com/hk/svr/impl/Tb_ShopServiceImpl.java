package com.hk.svr.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.taobao.Tb_Shop;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.Tb_ShopService;

public class Tb_ShopServiceImpl implements Tb_ShopService {

	@Autowired
	private QueryManager manager;

	@Override
	public void createTb_Shop(Tb_Shop tbShop) {
		Query query = this.manager.createQuery();
		tbShop.setSid(query.insertObject(tbShop).longValue());
	}

	@Override
	public void deleteTb_Shop(long sid) {
		Query query = this.manager.createQuery();
		query.deleteById(Tb_Shop.class, sid);
	}

	@Override
	public Tb_Shop getTb_Shop(long sid) {
		Query query = this.manager.createQuery();
		return query.getObjectById(Tb_Shop.class, sid);
	}

	@Override
	public Tb_Shop getTb_ShopByTb_sid(long sid) {
		Query query = this.manager.createQuery();
		return query.getObjectEx(Tb_Shop.class, "tb_sid=?",
				new Object[] { sid });
	}

	@Override
	public void updateTb_Shop(Tb_Shop tbShop) {
		Query query = this.manager.createQuery();
		query.insertObject(tbShop);
	}

	@Override
	public void addCmt_num(long sid, int add) {
		Query query = this.manager.createQuery();
		query.addField("cmt_num", "add", add);
		query.updateById(Tb_Shop.class, sid);
	}

	@Override
	public List<Tb_Shop> getTb_ShopListForHuo(int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(Tb_Shop.class, "cmt_num desc", begin, size);
	}
}