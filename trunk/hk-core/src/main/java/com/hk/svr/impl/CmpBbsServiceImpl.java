package com.hk.svr.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmpBbs;
import com.hk.bean.CmpBbsContent;
import com.hk.bean.CmpBbsDel;
import com.hk.bean.CmpBbsIdData;
import com.hk.bean.CmpBbsKind;
import com.hk.bean.CmpBbsReply;
import com.hk.bean.CmpBbsReplyDel;
import com.hk.bean.CmpBbsReplyIdData;
import com.hk.bean.CmpMyBbs;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.CmpBbsService;

public class CmpBbsServiceImpl implements CmpBbsService {

	@Autowired
	private QueryManager manager;

	public void bombCmpBbs(long bbsId, long opuserId) {
		if (this.getCmpBbsDel(bbsId) != null) {
			return;
		}
		Query query = this.manager.createQuery();
		CmpBbs cmpBbs = this.getCmpBbs(bbsId);
		Date date = new Date();
		CmpBbsContent cmpBbsContent = this.getCmpBbsContent(bbsId);
		if (cmpBbs != null && cmpBbsContent != null) {
			CmpBbsDel cmpBbsDel = new CmpBbsDel(cmpBbs, cmpBbsContent);
			cmpBbsDel.setOpuserId(opuserId);
			cmpBbsDel.setOptime(date);
			query.insertObject(cmpBbsDel);
			query.deleteById(CmpBbs.class, bbsId);
			query.deleteById(CmpBbsContent.class, bbsId);
			query.delete(CmpMyBbs.class, "bbsid=?", new Object[] { bbsId });
		}
	}

	private void bombCmpBbsReply(CmpBbsReply cmpBbsReply, long opuserId,
			Date optime, boolean updateBbs) {
		if (this.getCmpBbsReplyDel(cmpBbsReply.getReplyId()) != null) {
			return;
		}
		CmpBbsReplyDel cmpBbsReplyDel = new CmpBbsReplyDel(cmpBbsReply);
		cmpBbsReplyDel.setOpuserId(opuserId);
		cmpBbsReplyDel.setOptime(optime);
		Query query = this.manager.createQuery();
		query.insertObject(cmpBbsReplyDel);
		this.deleteCmpBbsReply(cmpBbsReply.getBbsId(),
				cmpBbsReply.getReplyId(), updateBbs);
	}

	public void bombCmpBbsReply(long replyId, long opuserId) {
		CmpBbsReply cmpBbsReply = this.getCmpBbsReply(replyId);
		this.bombCmpBbsReply(cmpBbsReply, opuserId, new Date(), true);
	}

	public void createCmpBbs(CmpBbs cmpBbs, CmpBbsContent cmpBbsContent) {
		if (cmpBbs.getCreateTime() == null) {
			cmpBbs.setCreateTime(new Date());
		}
		cmpBbs.setCmppinkTime(cmpBbs.getCreateTime());
		cmpBbs.setLastReplyTime(cmpBbs.getCreateTime());
		Query query = this.manager.createQuery();
		CmpBbsIdData cmpBbsIdData = new CmpBbsIdData(cmpBbs.getCreateTime());
		// 生成id
		long bbsId = query.insertObject(cmpBbsIdData).longValue();
		cmpBbs.setBbsId(bbsId);
		cmpBbsContent.setBbsId(bbsId);
		// 保存
		query.insertObject(cmpBbs);
		query.insertObject(cmpBbsContent);
		CmpMyBbs cmpMyBbs = new CmpMyBbs();
		cmpMyBbs.setBbsId(bbsId);
		cmpMyBbs.setBbsflg(CmpMyBbs.BBSFLG_CREATE);
		cmpMyBbs.setCompanyId(cmpBbs.getCompanyId());
		cmpMyBbs.setUserId(cmpBbs.getUserId());
		cmpMyBbs.setUptime(cmpBbs.getCreateTime());
		query.insertObject(cmpMyBbs);
	}

	public boolean createCmpBbsKind(CmpBbsKind cmpBbsKind) {
		Query query = this.manager.createQuery();
		int countByName = query.count(CmpBbsKind.class,
				"companyid=? and name=?", new Object[] {
						cmpBbsKind.getCompanyId(), cmpBbsKind.getName() });
		if (countByName > 0) {
			return false;
		}
		cmpBbsKind.setKindId(query.insertObject(cmpBbsKind).longValue());
		return true;
	}

	public void createCmpBbsReply(CmpBbsReply cmpBbsReply, CmpBbs cmpBbs) {
		if (cmpBbsReply.getCreateTime() == null) {
			cmpBbsReply.setCreateTime(new Date());
		}
		CmpBbsReplyIdData cmpBbsReplyIdData = new CmpBbsReplyIdData();
		cmpBbsReplyIdData.setCreateTime(cmpBbsReply.getCreateTime());
		Query query = this.manager.createQuery();
		// 生成id
		long replyId = query.insertObject(cmpBbsReplyIdData).longValue();
		cmpBbsReply.setReplyId(replyId);
		query.insertObject(cmpBbsReply);
		// 更新bbs相关
		int replyCount = query.count(CmpBbsReply.class, "bbsid=?",
				new Object[] { cmpBbs.getBbsId() });
		cmpBbs.setReplyCount(replyCount);
		cmpBbs.setLastReplyUserId(cmpBbsReply.getUserId());
		cmpBbs.setLastReplyTime(cmpBbsReply.getCreateTime());
		this.updateCmpBbsReplyRef(cmpBbs);
		// 更新回复话题
		if (query.count(CmpMyBbs.class, "companyid=? and bbsid=? and userid=?",
				new Object[] { cmpBbsReply.getCompanyId(),
						cmpBbsReply.getBbsId(), cmpBbsReply.getUserId() }) == 0) {
			CmpMyBbs cmpMyBbs = new CmpMyBbs();
			cmpMyBbs.setBbsId(cmpBbsReply.getBbsId());
			cmpMyBbs.setCompanyId(cmpBbsReply.getCompanyId());
			cmpMyBbs.setBbsflg(CmpMyBbs.BBSFLG_REPLY);
			cmpMyBbs.setUserId(cmpBbsReply.getUserId());
			cmpMyBbs.setUptime(cmpBbsReply.getCreateTime());
			query.insertObject(cmpMyBbs);
		}
		// 更新所有相关话题的更新时间
		query.addField("uptime", cmpBbsReply.getCreateTime());
		query.update(CmpMyBbs.class, "companyid=? and bbsid=?", new Object[] {
				cmpBbsReply.getCompanyId(), cmpBbsReply.getBbsId() });
	}

	public void deleteCmpBbs(long bbsId) {
		Query query = this.manager.createQuery();
		query.deleteById(CmpBbs.class, bbsId);
		query.deleteById(CmpBbsContent.class, bbsId);
		query.deleteById(CmpBbsDel.class, bbsId);
		query.delete(CmpBbsReply.class, "bbsid=?", new Object[] { bbsId });
		query.delete(CmpMyBbs.class, "bbsid=?", new Object[] { bbsId });
		query.delete(CmpBbsReplyDel.class, "bbsid=?", new Object[] { bbsId });
	}

	public void deleteCmpBbsReply(long bbsId, long replyId) {
		this.deleteCmpBbsReply(bbsId, replyId, true);
	}

	private void deleteCmpBbsReply(long bbsId, long replyId, boolean updateBbs) {
		Query query = this.manager.createQuery();
		query.deleteById(CmpBbsReply.class, replyId);
		if (updateBbs) {
			int replyCount = query.count(CmpBbsReply.class, "bbsid=?",
					new Object[] { bbsId });
			query.addField("replycount", replyCount);
			query.updateById(CmpBbs.class, bbsId);
		}
	}

	public CmpBbs getCmpBbs(long bbsId) {
		Query query = this.manager.createQuery();
		return query.getObjectById(CmpBbs.class, bbsId);
	}

	public CmpBbsContent getCmpBbsContent(long bbsId) {
		Query query = this.manager.createQuery();
		return query.getObjectById(CmpBbsContent.class, bbsId);
	}

	public CmpBbsDel getCmpBbsDel(long bbsId) {
		Query query = this.manager.createQuery();
		return query.getObjectById(CmpBbsDel.class, bbsId);
	}

	public List<CmpBbs> getCmpBbsListByKindId(long kindId, int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpBbs.class, "kindid=?", new Object[] { kindId },
				"lastreplytime desc", begin, size);
	}

	public List<CmpBbs> getCmpBbsListByCompanyId(long companyId, int begin,
			int size) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpBbs.class, "companyid=?",
				new Object[] { companyId }, "lastreplytime desc", begin, size);
	}

	public List<CmpMyBbs> getCmpMyBbsListByCompanyIdAndUserIdAndBbsflg(
			long companyId, long userId, byte bbsflg, int begin, int size) {
		Query query = this.manager.createQuery();
		return query
				.listEx(CmpMyBbs.class,
						"companyid=? and userid=? and bbsflg=?", new Object[] {
								companyId, userId, bbsflg }, "uptime desc",
						begin, size);
	}

	public List<CmpBbs> getCmpBbsListInId(List<Long> idList) {
		Query query = this.manager.createQuery();
		// return query.listInId(CmpBbs.class, idList);
		return query.listInField(CmpBbs.class, null, null, "bbsid", idList,
				null);
	}

	public Map<Long, CmpBbs> getCmpBbsMapInId(List<Long> idList) {
		List<CmpBbs> list = this.getCmpBbsListInId(idList);
		Map<Long, CmpBbs> map = new HashMap<Long, CmpBbs>();
		for (CmpBbs o : list) {
			map.put(o.getBbsId(), o);
		}
		return map;
	}

	public List<CmpBbsReply> getCmpBbsReplieListByBbsId(long bbsId, int begin,
			int size) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpBbsReply.class, "bbsid=?",
				new Object[] { bbsId }, "replyid asc", begin, size);
	}

	public CmpBbsReply getCmpBbsReply(long replyId) {
		Query query = this.manager.createQuery();
		return query.getObjectById(CmpBbsReply.class, replyId);
	}

	public CmpBbsReplyDel getCmpBbsReplyDel(long replyId) {
		Query query = this.manager.createQuery();
		return query.getObjectById(CmpBbsReplyDel.class, replyId);
	}

	public void recoverBombCmpBbs(long bbsId) {
		CmpBbsDel cmpBbsDel = this.getCmpBbsDel(bbsId);
		if (cmpBbsDel == null) {
			return;
		}
		CmpBbs cmpBbs = new CmpBbs(cmpBbsDel);
		Query query = this.manager.createQuery();
		query.insertObject(cmpBbs);
		CmpBbsContent cmpBbsContent = new CmpBbsContent(cmpBbsDel);
		query.insertObject(cmpBbsContent);
		CmpMyBbs cmpMyBbs = new CmpMyBbs();
		cmpMyBbs.setBbsId(bbsId);
		cmpMyBbs.setBbsflg(CmpMyBbs.BBSFLG_CREATE);
		cmpMyBbs.setCompanyId(cmpBbs.getCompanyId());
		cmpMyBbs.setUserId(cmpBbs.getUserId());
		cmpMyBbs.setUptime(cmpBbs.getCreateTime());
		query.insertObject(cmpMyBbs);
		query.deleteById(CmpBbsDel.class, bbsId);
	}

	public void recoverBombCmpBbsReply(long replyId) {
		CmpBbsReplyDel cmpBbsReplyDel = this.getCmpBbsReplyDel(replyId);
		if (cmpBbsReplyDel == null) {
			return;
		}
		CmpBbsReply cmpBbsReply = new CmpBbsReply(cmpBbsReplyDel);
		Query query = this.manager.createQuery();
		query.insertObject(cmpBbsReply);
		query.deleteById(CmpBbsReplyDel.class, cmpBbsReplyDel.getReplyId());
		int replyCount = query.count(CmpBbsReply.class, "bbsid=?",
				new Object[] { cmpBbsReply.getBbsId() });
		query.addField("replycount", replyCount);
		query.updateById(CmpBbs.class, cmpBbsReply.getBbsId());
	}

	public void updateCmpBbs(CmpBbs cmpBbs, CmpBbsContent cmpBbsContent) {
		Query query = this.manager.createQuery();
		query.updateObjectExcept(cmpBbs, new String[] { "lastreplyuserid",
				"lastreplytime", "replycount", "viewcount" });
		if (cmpBbsContent != null) {
			query.updateObject(cmpBbsContent);
		}
	}

	public boolean updateCmpBbsKind(CmpBbsKind cmpBbsKind) {
		Query query = this.manager.createQuery();
		CmpBbsKind o = query.getObjectEx(CmpBbsKind.class,
				"companyid=? and name=?", new Object[] {
						cmpBbsKind.getCompanyId(), cmpBbsKind.getName() });
		if (o != null && o.getKindId() != cmpBbsKind.getKindId()) {
			return false;
		}
		query.updateObject(cmpBbsKind);
		return true;
	}

	public void updateCmpBbsReply(CmpBbsReply cmpBbsReply) {
		Query query = this.manager.createQuery();
		query.updateObject(cmpBbsReply);
	}

	private void updateCmpBbsReplyRef(CmpBbs cmpBbs) {
		Query query = this.manager.createQuery();
		query.addField("lastreplyuserid", cmpBbs.getLastReplyUserId());
		query.addField("lastreplytime", cmpBbs.getLastReplyTime());
		query.addField("replycount", cmpBbs.getReplyCount());
		query.updateById(CmpBbs.class, cmpBbs.getBbsId());
	}

	public List<CmpBbsKind> getCmpBbsKindListByCompanyId(long companyId) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpBbsKind.class, "companyid=?",
				new Object[] { companyId }, "kindid asc");
	}

	public CmpBbsKind getCmpBbsKind(long kindId) {
		Query query = this.manager.createQuery();
		return query.getObjectById(CmpBbsKind.class, kindId);
	}

	public void updateCmpBbsPicpath(long bbsId, long photoId, String path) {
		Query query = this.manager.createQuery();
		query.addField("photoid", photoId);
		query.addField("picpath", path);
		query.updateById(CmpBbs.class, bbsId);
	}

	public void deleteCmpBbsKind(long kindId) {
		Query query = this.manager.createQuery();
		query.deleteById(CmpBbsKind.class, kindId);
		query.addField("kindid", 0);
		query.updateById(CmpBbs.class, kindId);
	}

	public void setCmpBbsCmppink(long bbsId, byte cmppink) {
		Query query = this.manager.createQuery();
		query.addField("cmppink", cmppink);
		query.addField("cmppinktime", new Date());
		query.updateById(CmpBbs.class, bbsId);
	}

	public void clearCmpBbsPic(long bbsId) {
		Query query = this.manager.createQuery();
		query.addField("photoid", 0);
		query.addField("picpath", null);
		query.updateById(CmpBbs.class, bbsId);
	}

	public void clearCmpBbsReplyPic(long replyId) {
		Query query = this.manager.createQuery();
		query.addField("photoid", 0);
		query.addField("picpath", null);
		query.updateById(CmpBbsReply.class, replyId);
	}

	public List<CmpBbsDel> getCmpBbsDelListByCompanyId(long companyId,
			int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpBbsDel.class, "companyid=?",
				new Object[] { companyId }, "optime desc", begin, size);
	}

	public List<CmpBbsReplyDel> getCmpBbsReplyDelListByCompanyId(
			long companyId, int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpBbsReplyDel.class, "companyid=?",
				new Object[] { companyId }, "optime desc", begin, size);
	}

	public void deleteCmpBbsDel(long bbsId) {
		Query query = this.manager.createQuery();
		query.deleteById(CmpBbsDel.class, bbsId);
		this.deleteCmpBbs(bbsId);
	}

	public void deleteCmpBbsReplyDel(long bbsId, long replyId) {
		Query query = this.manager.createQuery();
		query.deleteById(CmpBbsReplyDel.class, replyId);
		this.deleteCmpBbsReply(bbsId, replyId, true);
	}
}