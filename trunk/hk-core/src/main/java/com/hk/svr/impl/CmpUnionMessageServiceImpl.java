package com.hk.svr.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmpUnionFeed;
import com.hk.bean.CmpUnionNotice;
import com.hk.bean.CmpUnionReq;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.CmpUnionMessageService;

public class CmpUnionMessageServiceImpl implements CmpUnionMessageService {
	@Autowired
	private QueryManager manager;

	public int countCmpUnionReqByUid(long uid, byte dealflg) {
		Query query = manager.createQuery();
		if (dealflg < 0) {
			return query
					.count(CmpUnionReq.class, "uid=?", new Object[] { uid });
		}
		return query.count(CmpUnionReq.class, "uid=? and dealflg=?",
				new Object[] { uid, dealflg });
	}

	public void createCmpUnionReq(CmpUnionReq cmpUnionReq) {
		cmpUnionReq.setCreateTime(new Date());
		Query query = manager.createQuery();
		query.addField("uid", cmpUnionReq.getUid());
		query.addField("reqflg", cmpUnionReq.getReqflg());
		query.addField("dealflg", cmpUnionReq.getDealflg());
		query.addField("objid", cmpUnionReq.getObjId());
		query.addField("createtime", cmpUnionReq.getCreateTime());
		query.addField("data", cmpUnionReq.getData());
		long reqid = query.insert(CmpUnionReq.class).longValue();
		cmpUnionReq.setReqid(reqid);
	}

	public void deleteCmpUnionReq(long reqid) {
		Query query = manager.createQuery();
		query.deleteById(CmpUnionReq.class, reqid);
	}

	public List<CmpUnionReq> getCmpUnionReqListByUid(long uid, byte dealflg,
			int begin, int size) {
		Query query = manager.createQuery();
		if (dealflg < 0) {
			return query.listEx(CmpUnionReq.class, "uid=?",
					new Object[] { uid }, "reqid desc", begin, size);
		}
		return query.listEx(CmpUnionReq.class, "uid=? and dealflg=?",
				new Object[] { uid, dealflg }, "reqid desc", begin, size);
	}

	public int countCmpUnionNoticeByUid(long uid, byte readflg) {
		Query query = manager.createQuery();
		if (readflg > 0) {
			return query.count(CmpUnionNotice.class, "uid=? and readflg=?",
					new Object[] { uid, readflg });
		}
		return query.count(CmpUnionNotice.class, "uid=?", new Object[] { uid });
	}

	public void createCmpUnionFeed(CmpUnionFeed cmpUnionFeed) {
		cmpUnionFeed.setCreateTime(new Date());
		Query query = manager.createQuery();
		query.addField("uid", cmpUnionFeed.getUid());
		query.addField("objid", cmpUnionFeed.getObjId());
		query.addField("feedflg", cmpUnionFeed.getFeedflg());
		query.addField("data", cmpUnionFeed.getData());
		query.addField("createtime", cmpUnionFeed.getCreateTime());
		long feedId = query.insert(CmpUnionFeed.class).longValue();
		cmpUnionFeed.setFeedId(feedId);
	}

	public void createCmpUnionNotice(CmpUnionNotice cmpUnionNotice) {
		cmpUnionNotice.setCreateTime(new Date());
		Query query = manager.createQuery();
		query.addField("uid", cmpUnionNotice.getUid());
		query.addField("noticeflg", cmpUnionNotice.getNoticeflg());
		query.addField("objid", cmpUnionNotice.getObjId());
		query.addField("data", cmpUnionNotice.getData());
		query.addField("readflg", cmpUnionNotice.getReadflg());
		query.addField("createtime", cmpUnionNotice.getCreateTime());
		long noticeId = query.insert(CmpUnionNotice.class).longValue();
		cmpUnionNotice.setNoticeId(noticeId);
	}

	public List<CmpUnionFeed> getCmpUnionFeedListByUid(long uid, int begin,
			int size) {
		Query query = manager.createQuery();
		return query.listEx(CmpUnionFeed.class, "uid=?", new Object[] { uid },
				"feedid desc", begin, size);
	}

	public List<CmpUnionNotice> getCmpUnionNoticeList(long uid, byte readflg,
			int begin, int size) {
		Query query = manager.createQuery();
		if (readflg > 0) {
			return query
					.listEx(CmpUnionNotice.class, "uid=? and readflg=?",
							new Object[] { uid, readflg }, "noticeid desc",
							begin, size);
		}
		return query.listEx(CmpUnionNotice.class, "uid=?",
				new Object[] { uid }, "noticeid desc", begin, size);
	}

	public CmpUnionReq getCmpUnionReq(long reqid) {
		Query query = manager.createQuery();
		return query.getObjectById(CmpUnionReq.class, reqid);
	}

	public void updateCmpUnionReqDeaded(long reqid) {
		Query query = manager.createQuery();
		query.addField("dealflg", CmpUnionReq.DEALFLG_Y);
		query.updateById(CmpUnionReq.class, reqid);
	}

	public void updateCmpUnionNoticeReaded(long noticeId) {
		Query query = manager.createQuery();
		query.addField("readflg", CmpUnionNotice.READFLG_Y);
		query.updateById(CmpUnionNotice.class, noticeId);
	}
}