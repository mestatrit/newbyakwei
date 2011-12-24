package com.hk.svr.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.taobao.Tb_Item_Cmt;
import com.hk.bean.taobao.Tb_Item_Cmt_Reply;
import com.hk.bean.taobao.Tb_Item_Cmtid;
import com.hk.bean.taobao.Tb_Item_User_Ref;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.Tb_Item_CmtService;

public class Tb_Item_CmtServiceImpl implements Tb_Item_CmtService {

	@Autowired
	private QueryManager manager;

	@Override
	public void createTb_Item_Cmt(Tb_Item_Cmt tbItemCmt, boolean commend,
			boolean holdItem, boolean createToSinaWeibo, String serverName,
			String contextPath) {
		Query query = this.manager.createQuery();
		Tb_Item_Cmtid tbItemCmtid = new Tb_Item_Cmtid();
		tbItemCmtid.setCreate_time(new Date());
		long cmtid = query.insertObject(tbItemCmtid).longValue();
		tbItemCmtid.setCmtid(cmtid);
		tbItemCmt.setCmtid(cmtid);
		query.insertObject(tbItemCmt);
	}

	public void deleteTb_Item_Cmt(Tb_Item_Cmt tbItemCmt) {
		Query query = this.manager.createQuery();
		query.deleteById(Tb_Item_Cmt.class, tbItemCmt.getCmtid());
	}

	public Tb_Item_Cmt getTb_Item_Cmt(long cmtid) {
		Query query = this.manager.createQuery();
		return query.getObjectById(Tb_Item_Cmt.class, cmtid);
	}

	public List<Tb_Item_Cmt> getTb_Item_CmtListByItemid(long itemid,
			boolean buildUser, int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(Tb_Item_Cmt.class, "itemid=?",
				new Object[] { itemid }, "cmtid desc", begin, size);
	}

	public List<Tb_Item_Cmt> getTb_Item_CmtListByItemidAndUserid(long itemid,
			long userid, int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(Tb_Item_Cmt.class, "itemid=? and userid=?",
				new Object[] { itemid, userid }, "cmtid desc", begin, size);
	}

	public List<Tb_Item_User_Ref> getTb_Item_User_RefByUseridAndFlg(
			long userid, byte flg, int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(Tb_Item_User_Ref.class, "userid=? and flg=?",
				new Object[] { userid, flg }, "oid desc", begin, size);
	}

	public void createTb_Item_Cmt_Reply(Tb_Item_Cmt_Reply tbItemCmtReply) {
		Query query = this.manager.createQuery();
		long id = query.insertObject(tbItemCmtReply).longValue();
		tbItemCmtReply.setReplyid(id);
	}

	public void deleteTb_Item_Cmt_Reply(Tb_Item_Cmt_Reply tbItemCmtReply) {
		Query query = this.manager.createQuery();
		query.deleteById(Tb_Item_Cmt_Reply.class, tbItemCmtReply.getReplyid());
	}

	public Tb_Item_Cmt_Reply getTb_Item_Cmt_Reply(long replyid) {
		Query query = this.manager.createQuery();
		return query.getObjectById(Tb_Item_Cmt_Reply.class, replyid);
	}

	public List<Tb_Item_Cmt_Reply> getTb_Item_Cmt_ReplyListByCmtid(long cmtid,
			boolean buildUser, int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(Tb_Item_Cmt_Reply.class, "cmtid=?",
				new Object[] { cmtid }, "replyid desc", begin, size);
	}

	public void updateScoreByItemidAndUserid(long itemid, long userid, int score) {
		Query query = this.manager.createQuery();
		query.addField("score", score);
		query.update(Tb_Item_Cmt.class, "itemid=? and userid=?", new Object[] {
				itemid, userid });
	}

	public void updateTb_Item_Cmt(Tb_Item_Cmt tbItemCmt) {
		Query query = this.manager.createQuery();
		query.updateObject(tbItemCmt);
	}

	public int countTb_Item_CmtByUseridAndItemid(long userid, long itemid) {
		Query query = this.manager.createQuery();
		return query.count(Tb_Item_Cmt.class, "userid=? and itemid=?",
				new Object[] { userid, itemid });
	}

	public Map<Long, Tb_Item_Cmt> getTb_Item_CmtMapInId(List<Long> idList) {
		Query query = this.manager.createQuery();
		List<Tb_Item_Cmt> list = query.listInField(Tb_Item_Cmt.class, null,
				null, "cmtid", idList, null);
		Map<Long, Tb_Item_Cmt> map = new HashMap<Long, Tb_Item_Cmt>();
		for (Tb_Item_Cmt o : list) {
			map.put(o.getCmtid(), o);
		}
		return map;
	}

	public void deleteTb_Item_Cmt_ReplyByCmtid(long cmtid) {
		Query query = this.manager.createQuery();
		query
				.delete(Tb_Item_Cmt_Reply.class, "cmtid=?",
						new Object[] { cmtid });
	}

	public List<Tb_Item_Cmt> getTb_Item_CmtListForNew(boolean buildUser,
			boolean buildItem, int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(Tb_Item_Cmt.class, "itemid desc", begin, size);
	}

	@Override
	public List<Tb_Item_Cmt> getTb_Item_CmtListBySid(long sid,
			boolean buildUser, boolean buildItem, int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(Tb_Item_Cmt.class, "sid=?", new Object[] { sid },
				"cmtid desc", begin, size);
	}

	@Override
	public void deleteTb_Item_Cmt_ReplyByItemid(long itemid) {
		this.manager.createQuery().delete(Tb_Item_Cmt_Reply.class, "itemid=?",
				new Object[] { itemid });
	}

	@Override
	public void deleteTb_Item_CmtByItemid(long itemid) {
		this.manager.createQuery().delete(Tb_Item_Cmt.class, "itemid=?",
				new Object[] { itemid });
	}
}