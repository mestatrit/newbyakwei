package com.hk.svr.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.taobao.Tb_Item_User_Ref;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.Tb_Item_User_RefService;

public class Tb_Item_User_RefServiceImpl implements Tb_Item_User_RefService {

	@Autowired
	private QueryManager manager;

	@Override
	public boolean createTb_Item_User_Ref(Tb_Item_User_Ref tbItemUserRef) {
		Query query = this.manager.createQuery();
		if (query.count(Tb_Item_User_Ref.class,
				"userid=? and itemid=? and flg=?", new Object[] {
						tbItemUserRef.getUserid(), tbItemUserRef.getItemid(),
						tbItemUserRef.getFlg() }) == 0) {
			long id = query.insertObject(tbItemUserRef).longValue();
			tbItemUserRef.setOid(id);
			return true;
		}
		return false;
	}

	@Override
	public void deleteTb_Item_User_Ref(long oid) {
		Query query = this.manager.createQuery();
		query.deleteById(Tb_Item_User_Ref.class, oid);
	}

	@Override
	public Tb_Item_User_Ref getTb_Item_User_Ref(long oid) {
		Query query = this.manager.createQuery();
		return query.getObjectById(Tb_Item_User_Ref.class, oid);
	}

	@Override
	public List<Tb_Item_User_Ref> getTb_Item_User_RefByUseridAndFlg(
			long userid, byte flg, boolean buildItem, boolean buildCmt,
			int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(Tb_Item_User_Ref.class, "userid=? and flg=?",
				new Object[] { userid, flg }, "oid desc", begin, size);
	}

	@Override
	public List<Tb_Item_User_Ref> getTb_Item_User_RefByItemidAndFlg(
			long itemid, byte flg, boolean buildUser, int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(Tb_Item_User_Ref.class, "itemid=? and flg=?",
				new Object[] { itemid, flg }, "oid desc", begin, size);
	}

	@Override
	public Tb_Item_User_Ref getTb_Item_User_RefByUseridAndItemidAndFlg(
			long userid, long itemid, byte flg) {
		Query query = this.manager.createQuery();
		return query.getObjectEx(Tb_Item_User_Ref.class,
				"userid=? and itemid=? and flg=?", new Object[] { userid,
						itemid, flg });
	}

	@Override
	public List<Tb_Item_User_Ref> getTb_Item_User_RefListByUseridAndItemid(
			long userid, long itemid) {
		Query query = this.manager.createQuery();
		return query.listEx(Tb_Item_User_Ref.class, "userid=? and itemid=?",
				new Object[] { userid, itemid }, "oid desc");
	}

	@Override
	public void updateCmtid(long oid, long cmtid) {
		Query query = this.manager.createQuery();
		query.addField("cmtid", cmtid);
		query.updateById(Tb_Item_User_Ref.class, oid);
	}

	@Override
	public List<Tb_Item_User_Ref> getTb_Item_User_RefListByUseridAndInItemid(
			long userid, List<Long> idList) {
		Query query = this.manager.createQuery();
		return query.listInField(Tb_Item_User_Ref.class, "userid=?",
				new Object[] { userid }, "itemid", idList, null);
	}

	@Override
	public List<Tb_Item_User_Ref> getTb_Item_User_RefListByUseridAndItemidAndCmtid(
			long userid, long itemid, long cmtid) {
		Query query = this.manager.createQuery();
		return query.listEx(Tb_Item_User_Ref.class,
				"userid=? and itemid=? and cmtid=?", new Object[] { userid,
						itemid, cmtid });
	}

	@Override
	public int countTb_Item_User_RefByuseridAndFlg(long userid, byte flg) {
		return this.manager.createQuery().count(Tb_Item_User_Ref.class,
				"userid=? and flg=?", new Object[] { userid, flg });
	}

	@Override
	public void deleteTb_Item_User_RefByItemid(long itemid) {
		this.manager.createQuery().delete(Tb_Item_User_Ref.class, "itemid=?",
				new Object[] { itemid });
	}
}