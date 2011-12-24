package com.hk.svr.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.taobao.Tb_Ask_Cat;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.frame.util.DataUtil;
import com.hk.svr.Tb_Ask_CatService;

public class Tb_Ask_CatServiceImpl implements Tb_Ask_CatService {

	@Autowired
	private QueryManager manager;

	@Override
	public boolean createTb_Ask_Cat(Tb_Ask_Cat tbAskCat) {
		Query query = this.manager.createQuery();
		if (query.count(Tb_Ask_Cat.class, "name=? and parent_cid=?",
				new Object[] { tbAskCat.getName(), tbAskCat.getParent_cid() }) == 0) {
			tbAskCat.setCid(query.insertObject(tbAskCat).longValue());
			if (tbAskCat.getParent_cid() > 0) {
				Tb_Ask_Cat parent = query.getObjectById(Tb_Ask_Cat.class,
						tbAskCat.getParent_cid());
				if (!parent.isParent()) {
					parent.setParentflg(Tb_Ask_Cat.PARENTFLG_Y);
					query.updateObject(parent);
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public void deleteTb_Ask_Cat(Tb_Ask_Cat tbAskCat) {
		Query query = this.manager.createQuery();
		query.deleteById(Tb_Ask_Cat.class, tbAskCat.getCid());
		if (tbAskCat.getParent_cid() > 0) {
			Tb_Ask_Cat parent = query.getObjectById(Tb_Ask_Cat.class, tbAskCat
					.getParent_cid());
			if (query.count(Tb_Ask_Cat.class, "parent_cid=?",
					new Object[] { parent.getCid() }) == 0) {
				parent.setParentflg(Tb_Ask_Cat.PARENTFLG_N);
				query.updateObject(parent);
			}
		}
	}

	@Override
	public Tb_Ask_Cat getTb_Ask_Cat(long cid) {
		return this.manager.createQuery().getObjectById(Tb_Ask_Cat.class, cid);
	}

	public List<Tb_Ask_Cat> getTb_Ask_CatList(long parentCid) {
		return this.manager.createQuery().listEx(Tb_Ask_Cat.class,
				"parent_cid=?", new Object[] { parentCid }, "order_num desc");
	}

	@Override
	public List<Tb_Ask_Cat> getTb_Ask_CatList(long parentCid, String name) {
		if (DataUtil.isEmpty(name)) {
			return this.manager.createQuery().listEx(Tb_Ask_Cat.class,
					"parent_cid=?", new Object[] { parentCid },
					"order_num desc,cid desc");
		}
		return this.manager.createQuery().listEx(Tb_Ask_Cat.class,
				"parent_cid=? and name like ?",
				new Object[] { parentCid, "%" + name + "%" },
				"order_num desc,cid desc");
	}

	@Override
	public boolean updateTb_Ask_Cat(Tb_Ask_Cat tbAskCat) {
		Query query = this.manager.createQuery();
		Tb_Ask_Cat dbobj = query.getObjectById(Tb_Ask_Cat.class, tbAskCat
				.getCid());
		if (dbobj.getName().equals(tbAskCat.getName())) {
			query.updateObject(tbAskCat);
			return true;
		}
		if (query.count(Tb_Ask_Cat.class, "name=? and parent_cid=?",
				new Object[] { tbAskCat.getName(), tbAskCat.getParent_cid() }) == 0) {
			query.updateObject(tbAskCat);
			return true;
		}
		return false;
	}

	@Override
	public List<Tb_Ask_Cat> getTb_Ask_CatList() {
		return this.manager.createQuery().listEx(Tb_Ask_Cat.class,
				"order_num desc,cid desc");
	}
}