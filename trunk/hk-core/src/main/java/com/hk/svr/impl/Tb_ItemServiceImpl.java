package com.hk.svr.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.taobao.Tb_CmdItem;
import com.hk.bean.taobao.Tb_Domain_Item;
import com.hk.bean.taobao.Tb_Item;
import com.hk.bean.taobao.Tb_Item_Cat_Ref;
import com.hk.bean.taobao.Tb_Item_Img;
import com.hk.bean.taobao.Tb_Item_Score;
import com.hk.bean.taobao.Tb_Itemid;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.frame.util.DataUtil;
import com.hk.svr.Tb_ItemService;

public class Tb_ItemServiceImpl implements Tb_ItemService {

	@Autowired
	private QueryManager manager;

	public void createTb_Item(Tb_Item tbItem) {
		Query query = this.manager.createQuery();
		Tb_Itemid tbItemid = new Tb_Itemid();
		tbItemid.setCreate_time(new Date());
		long itemid = query.insertObject(tbItemid).longValue();
		tbItemid.setItemid(itemid);
		tbItem.setItemid(itemid);
		query.insertObject(tbItem);
	}

	public void deleteTb_Item(long itemid) {
		Query query = this.manager.createQuery();
		query.deleteById(Tb_Item.class, itemid);
		query.delete(Tb_CmdItem.class, "itemid=?", new Object[] { itemid });
	}

	public Tb_Item getTb_Item(long itemid) {
		Query query = this.manager.createQuery();
		return query.getObjectById(Tb_Item.class, itemid);
	}

	public Tb_Item getTb_ItemByNum_iid(long numIid) {
		Query query = this.manager.createQuery();
		return query.getObjectEx(Tb_Item.class, "num_iid=?",
				new Object[] { numIid });
	}

	public List<Tb_Item> getTb_ItemListInId(List<Long> idList) {
		Query query = this.manager.createQuery();
		return query.listInField(Tb_Item.class, null, null, "itemid", idList,
				null);
	}

	public Map<Long, Tb_Item> getTb_ItemMapInId(List<Long> idList) {
		List<Tb_Item> list = this.getTb_ItemListInId(idList);
		Map<Long, Tb_Item> map = new HashMap<Long, Tb_Item>();
		for (Tb_Item o : list) {
			map.put(o.getItemid(), o);
		}
		return map;
	}

	public void updateTb_Item(Tb_Item tbItem) {
		Query query = this.manager.createQuery();
		query.updateObject(tbItem);
	}

	public void createTb_Item_Cat_Ref(Tb_Item_Cat_Ref tbItemCatRef) {
		Query query = this.manager.createQuery();
		if (query
				.count(Tb_Item_Cat_Ref.class, "cid=? and itemid=?",
						new Object[] { tbItemCatRef.getCid(),
								tbItemCatRef.getItemid() }) == 0) {
			long id = query.insertObject(tbItemCatRef).longValue();
			tbItemCatRef.setOid(id);
		}
	}

	public void deleteTb_Item_Cat_Ref(long oid) {
		Query query = this.manager.createQuery();
		query.deleteById(Tb_Item_Cat_Ref.class, oid);
	}

	public void createTb_Item_Img(Tb_Item_Img tbItemImg) {
		Query query = this.manager.createQuery();
		long id = query.insertObject(tbItemImg).longValue();
		tbItemImg.setImgid(id);
	}

	public void deleteTb_Item_Img(long imgid) {
		Query query = this.manager.createQuery();
		query.deleteById(Tb_Item_Img.class, imgid);
	}

	public List<Tb_Item_Img> getTb_Item_ImgListByItemid(long itemid) {
		Query query = this.manager.createQuery();
		return query.listEx(Tb_Item_Img.class, "itemid=?",
				new Object[] { itemid }, "position desc");
	}

	public void saveTb_Item_Score(Tb_Item_Score tbItemScore) {
		Query query = this.manager.createQuery();
		if (tbItemScore.getOid() > 0) {
			query.updateObject(tbItemScore);
		}
		else {
			long id = query.insertObject(tbItemScore).longValue();
			tbItemScore.setOid(id);
		}
	}

	public void deleteTb_Item_Score(long oid) {
		Query query = this.manager.createQuery();
		query.deleteById(Tb_Item_Score.class, oid);
	}

	public Tb_Item_Score getTb_Item_ScoreByItemidAndUserid(long itemid,
			long userid) {
		Query query = this.manager.createQuery();
		return query.getObjectEx(Tb_Item_Score.class, "itemid=? and userid=?",
				new Object[] { itemid, userid });
	}

	public int sumScoreFromTb_Item_ScoreByItemId(long itemid) {
		Query query = this.manager.createQuery();
		return query.sum("score", Tb_Item_Score.class, "itemid=?",
				new Object[] { itemid }).intValue();
	}

	public int countTb_Item_ScoreByItemid(long itemid) {
		Query query = this.manager.createQuery();
		return query.count(Tb_Item_Score.class, "itemid=?",
				new Object[] { itemid });
	}

	public List<Tb_Item> getTb_ItemListForNew(int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(Tb_Item.class, "itemid desc", begin, size);
	}

	@Override
	public void addCmt_num(long itemid, int add) {
		Query query = this.manager.createQuery();
		if (add < 0) {
			query.addField("cmt_num", "add", add);
			query.update(Tb_Item.class, "cmt_num>?", new Object[] { 0 });
		}
		else {
			query.addField("cmt_num", "add", add);
			query.updateById(Tb_Item.class, itemid);
		}
	}

	@Override
	public List<Tb_Item> getTb_ItemListForKu(int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(Tb_Item.class, "hkscore desc", begin, size);
	}

	@Override
	public List<Tb_Item> getTb_ItemListForHuo(int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(Tb_Item.class, "huo_status>?", new Object[] { 0 },
				"huo_status desc", begin, size);
	}

	@Override
	public void createTb_Domain_Item(Tb_Domain_Item tbDomainItem) {
		Query query = this.manager.createQuery();
		if (query.count(Tb_Domain_Item.class, "domainid=? and itemid=?",
				new Object[] { tbDomainItem.getDomainid(),
						tbDomainItem.getItemid() }) == 0) {
			tbDomainItem.setOid(query.insertObject(tbDomainItem).longValue());
		}
	}

	@Override
	public List<Tb_Item> getTb_ItemList(int begin, int size) {
		return this.manager.createQuery().listEx(Tb_Item.class, begin, size);
	}

	@Override
	public List<Tb_Domain_Item> getTb_Domain_ItemListForHuo(int begin, int size) {
		return this.manager.createQuery().listEx(Tb_Domain_Item.class,
				"huo_status>?", new Object[] { 0 }, "huo_status desc", begin,
				size);
	}

	@Override
	public List<Tb_Domain_Item> getTb_Domain_ItemListForKu(int begin, int size) {
		return this.manager.createQuery().listEx(Tb_Domain_Item.class,
				"hkscore desc", begin, size);
	}

	@Override
	public List<Tb_Domain_Item> getTb_Domain_ItemListForNew(int begin, int size) {
		return this.manager.createQuery().listEx(Tb_Domain_Item.class,
				"oid desc", begin, size);
	}

	@Override
	public void updateTb_Domain_Item(Tb_Domain_Item tbDomainItem) {
		// TODO Auto-generated method stub
	}

	@Override
	public void updateTb_Domain_ItemByItemid(long itemid, int hkscore,
			int huoStatus, long volume) {
		// TODO Auto-generated method stub
	}

	@Override
	public void deleteTb_Item_ImgByNum_iid(long numIid) {
		this.manager.createQuery().delete(Tb_Item_Img.class, "num_iid=?",
				new Object[] { numIid });
	}

	@Override
	public void deleteTb_Item_ScoreByItemid(long itemid) {
		this.manager.createQuery().delete(Tb_Item_Score.class, "itemid=?",
				new Object[] { itemid });
	}

	@Override
	public List<Tb_Item> getTb_ItemListForUpdate(Date outerTime, int begin,
			int size) {
		return this.manager.createQuery().listEx(Tb_Item.class,
				"last_modified<?", new Object[] { outerTime }, "itemid asc",
				begin, size);
	}

	@Override
	public void commendItem(long itemid) {
		Query query = this.manager.createQuery();
		query.delete(Tb_CmdItem.class, "itemid=?", new Object[] { itemid });
		Tb_CmdItem o = new Tb_CmdItem();
		o.setItemid(itemid);
		o.setCreate_time(new Date());
		query.insertObject(o);
		query.addField("home_cmd_flg", Tb_Item.HOME_CMD_FLG_Y);
		query.updateById(Tb_Item.class, itemid);
	}

	@Override
	public void deleteTb_CmdItem(Tb_CmdItem tbCmdItem) {
		Query query = this.manager.createQuery();
		query.deleteObject(tbCmdItem);
		query.addField("home_cmd_flg", Tb_Item.HOME_CMD_FLG_N);
		query.updateById(Tb_Item.class, tbCmdItem.getItemid());
	}

	@Override
	public List<Tb_CmdItem> getTb_CmdItemList(boolean buildItem, int begin,
			int size) {
		List<Tb_CmdItem> list = this.manager.createQuery().listEx(
				Tb_CmdItem.class, "oid desc", begin, size);
		if (buildItem) {
			List<Long> idList = new ArrayList<Long>();
			for (Tb_CmdItem o : list) {
				idList.add(o.getItemid());
			}
			Map<Long, Tb_Item> map = this.getTb_ItemMapInId(idList);
			for (Tb_CmdItem o : list) {
				o.setTbItem(map.get(o.getItemid()));
			}
		}
		return list;
	}

	@Override
	public List<Tb_Item> getTb_ItemListCdn(String title, int begin, int size) {
		StringBuilder sb = new StringBuilder("");
		List<Object> olist = new ArrayList<Object>();
		if (DataUtil.isNotEmpty(title)) {
			sb.append("title like ?");
			olist.add("%" + title + "%");
		}
		return this.manager.createQuery().listExParamList(Tb_Item.class,
				sb.toString(), olist, "itemid desc", begin, size);
	}

	@Override
	public Tb_CmdItem getTb_CmdItem(long oid) {
		return this.manager.createQuery().getObjectById(Tb_CmdItem.class, oid);
	}
}