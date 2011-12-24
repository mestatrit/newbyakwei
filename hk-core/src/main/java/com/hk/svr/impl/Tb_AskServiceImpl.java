package com.hk.svr.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.taobao.Tb_Answer;
import com.hk.bean.taobao.Tb_Answer_Status;
import com.hk.bean.taobao.Tb_Answerid;
import com.hk.bean.taobao.Tb_Ask;
import com.hk.bean.taobao.Tb_Ask_Index;
import com.hk.bean.taobao.Tb_Askid;
import com.hk.bean.taobao.Tb_User_Ask;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.Tb_AskService;

public class Tb_AskServiceImpl implements Tb_AskService {

	@Autowired
	private QueryManager manager;

	@Override
	public void createTb_Answer(Tb_Answer tbAnswer) {
		Query query = manager.createQuery();
		long ansid = query.insertObject(new Tb_Answerid(new Date()))
				.longValue();
		tbAnswer.setAnsid(ansid);
		query.insertObject(tbAnswer);
	}

	@Override
	public void createTb_Ask(Tb_Ask tbAsk) {
		Query query = manager.createQuery();
		long aid = query.insertObject(new Tb_Askid(new Date())).longValue();
		tbAsk.setAid(aid);
		query.insertObject(tbAsk);
	}

	@Override
	public void deleteTb_Answer(Tb_Answer tbAnswer) {
		manager.createQuery().deleteObject(tbAnswer);
	}

	@Override
	public void deleteTb_Ask(Tb_Ask tbAsk) {
		manager.createQuery().deleteObject(tbAsk);
	}

	@Override
	public void deleteTb_AnswerByAid(long aid) {
		manager.createQuery().delete(Tb_Answer.class, "aid=?",
				new Object[] { aid });
	}

	@Override
	public Tb_Answer getTb_Answer(long ansid) {
		return manager.createQuery().getObjectById(Tb_Answer.class, ansid);
	}

	@Override
	public List<Tb_Answer> getTb_AnswerListByAid(long aid, int begin, int size) {
		return manager.createQuery().listEx(Tb_Answer.class, "aid=?",
				new Object[] { aid }, "ansid desc", begin, size);
	}

	@Override
	public Tb_Ask getTb_Ask(long qid) {
		return manager.createQuery().getObjectById(Tb_Ask.class, qid);
	}

	@Override
	public List<Tb_Ask> getTb_AskListByUserid(long userid, int begin, int size) {
		return manager.createQuery().listEx(Tb_Ask.class, "userid=?",
				new Object[] { userid }, "aid desc", begin, size);
	}

	@Override
	public List<Tb_Ask> getTb_AskListForNew(int begin, int size) {
		return manager.createQuery().listEx(Tb_Ask.class, "aid desc", begin,
				size);
	}

	@Override
	public List<Tb_Ask> getTb_AskListForResolved(int begin, int size) {
		return manager.createQuery().listEx(Tb_Ask.class, "resolve_status=?",
				new Object[] { Tb_Ask.RESOLVE_STATUS_Y }, "aid desc", begin,
				size);
	}

	@Override
	public void updateTb_Answer(Tb_Answer tbAnswer) {
		manager.createQuery().updateObject(tbAnswer);
	}

	@Override
	public void updateTb_Ask(Tb_Ask tbAsk) {
		manager.createQuery().updateObject(tbAsk);
	}

	@Override
	public void addTb_AskAnswer_num(long aid, int add) {
		manager.createQuery().addField("answer_num", "add", add).update(
				Tb_Ask.class, "aid=?", new Object[] { aid });
	}

	@Override
	public void createTb_User_Ask(Tb_User_Ask tbUserAsk) {
		Query query = manager.createQuery();
		if (query.count(Tb_User_Ask.class, "userid=? and aid=? and askflg=?",
				new Object[] { tbUserAsk.getUserid(), tbUserAsk.getAid(),
						tbUserAsk.getAskflg() }) == 0) {
			query.insertObject(tbUserAsk);
		}
	}

	@Override
	public void deleteTb_User_Ask(long userid, long aid, byte askflg) {
		manager.createQuery().delete(Tb_User_Ask.class,
				"userid=? and aid=? and askflg=?",
				new Object[] { userid, aid, askflg });
	}

	@Override
	public Tb_User_Ask getTb_User_Ask(long userid, long aid, byte askflg) {
		return manager.createQuery().getObjectEx(Tb_User_Ask.class,
				"userid=? and aid=? and askflg=?",
				new Object[] { userid, aid, askflg });
	}

	@Override
	public void createTb_Answer_Status(Tb_Answer_Status tbAnswerStatus) {
		Query query = manager.createQuery();
		if (query.count(Tb_Answer_Status.class,
				"userid=? and ansid=? and ans_status=?", new Object[] {
						tbAnswerStatus.getUserid(), tbAnswerStatus.getAnsid(),
						tbAnswerStatus.getAns_status() }) == 0) {
			query.insertObject(tbAnswerStatus);
		}
	}

	@Override
	public void updateTb_Answer_Status(Tb_Answer_Status tbAnswerStatus) {
		manager.createQuery().updateObject(tbAnswerStatus);
	}

	@Override
	public Tb_Answer_Status getTb_Answer_Status(long userid, long ansid) {
		return manager.createQuery().getObjectEx(Tb_Answer_Status.class,
				"userid=? and ansid=?", new Object[] { userid, ansid });
	}

	@Override
	public void updateTb_AnswerSupportAndDiscmdInfo(long ansid, int supportNum,
			int discmdNum) {
		manager.createQuery().addField("support_num", supportNum).addField(
				"discmd_num", discmdNum).updateById(Tb_Answer.class, ansid);
	}

	@Override
	public void createTb_Ask_Index(long aid, byte flg) {
		Tb_Ask_Index tbAskIndex = new Tb_Ask_Index();
		tbAskIndex.setAid(aid);
		tbAskIndex.setCreate_time(new Date());
		tbAskIndex.setFlg(flg);
		manager.createQuery().insertObject(tbAskIndex);
	}

	@Override
	public void deleteTb_Ask_Index(long oid) {
		manager.createQuery().deleteById(Tb_Ask_Index.class, oid);
	}

	@Override
	public List<Tb_Ask_Index> getTb_Ask_IndexListByFlg(byte flg, int begin,
			int size) {
		return manager.createQuery().listEx(Tb_Ask_Index.class, "flg=?",
				new Object[] { flg }, "aid asc", begin, size);
	}

	@Override
	public Map<Long, Tb_Ask> getTb_AskMapInId(List<Long> idList) {
		Query query = manager.createQuery();
		List<Tb_Ask> list = query.listInField(Tb_Ask.class, null, null, "aid",
				idList, null);
		Map<Long, Tb_Ask> map = new HashMap<Long, Tb_Ask>();
		for (Tb_Ask o : list) {
			map.put(o.getAid(), o);
		}
		return map;
	}

	@Override
	public Map<Long, Tb_Answer> getTb_AnswerMapInId(List<Long> idList) {
		Query query = manager.createQuery();
		List<Tb_Answer> list = query.listInField(Tb_Answer.class, null, null,
				"ansid", idList, null);
		Map<Long, Tb_Answer> map = new HashMap<Long, Tb_Answer>();
		for (Tb_Answer o : list) {
			map.put(o.getAnsid(), o);
		}
		return map;
	}

	@Override
	public List<Tb_User_Ask> getTb_User_AskList(long userid, byte askflg,
			int begin, int size) {
		Query query = manager.createQuery();
		return query.listEx(Tb_User_Ask.class, "userid=? and askflg=?",
				new Object[] { userid, askflg }, "oid desc", begin, size);
	}

	@Override
	public List<Tb_Ask> getTb_AskListForNotResolved(int begin, int size) {
		return manager.createQuery().listEx(Tb_Ask.class, "ansid=?",
				new Object[] { 0 }, "aid desc", begin, size);
	}
}