package com.hk.svr.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.hk.bean.Notice;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.NoticeService;

public class NoticeServiceImpl implements NoticeService {
	@Autowired
	private QueryManager manager;

	public int countNoReadNotice(long userId) {
		Query query = manager.createQuery();
		query.setTable(Notice.class);
		query.where("userid=? and readflg=?").setParam(userId).setParam(
				Notice.READFLG_N);
		return query.count();
	}

	public int countNoReadNotice(long userId, byte noticeType) {
		Query query = manager.createQuery();
		query.setTable(Notice.class);
		query.where("userid=? and readflg=? and noticetype=?").setParam(userId)
				.setParam(Notice.READFLG_N).setParam(noticeType);
		return query.count();
	}

	public void createNotice(Notice notice) {
		if (notice.getCreateTime() == null) {
			notice.setCreateTime(new Date());
		}
		Query query = manager.createQuery();
		query.insertObject(notice);
	}

	public List<Notice> getNoReadNoticeList(long userId, byte noticeType,
			int begin, int size) {
		Query query = manager.createQuery();
		query.setTable(Notice.class);
		query.where("userid=? and readflg=? and noticetype=?").setParam(userId)
				.setParam(Notice.READFLG_N).setParam(noticeType);
		query.orderByDesc("noticeid");
		return query.list(begin, size, Notice.class);
	}

	public Notice getLastNoReadNotice(long userId, byte noticeType) {
		Query query = manager.createQuery();
		query.setTable(Notice.class);
		query.where("userid=? and readflg=? and noticetype=?").setParam(userId)
				.setParam(Notice.READFLG_N).setParam(noticeType);
		query.orderByDesc("noticeid");
		return query.getObject(Notice.class);
	}

	public Notice getLastNoReadNotice(long userId) {
		Query query = manager.createQuery();
		return query.getObject(Notice.class, "userid=? and readflg=?",
				new Object[] { userId, Notice.READFLG_N }, "noticeid");
	}

	public List<Notice> getNoticeList(long userId, byte noticeType, int begin,
			int size) {
		Query query = manager.createQuery();
		query.setTable(Notice.class);
		query.where("userid=? and noticetype=?").setParam(userId).setParam(
				noticeType);
		query.orderByDesc("noticeid");
		return query.list(begin, size, Notice.class);
	}

	public void setNoticeRead(long userId, long noticeId) {
		Query query = manager.createQuery();
		query.setTable(Notice.class);
		query.addField("readflg", Notice.READFLG_Y);
		query.where("userid=? and noticeid=?").setParam(userId).setParam(
				noticeId);
		query.update();
	}

	public void deleteNotice(long userId, long objId, long obj2Id,
			byte noticeType) {
		Query query = manager.createQuery();
		query.setTable(Notice.class);
		query.where("userid=? and objid=? and obj2id=? and noticetype=?")
				.setParam(userId).setParam(objId).setParam(obj2Id).setParam(
						noticeType);
		query.delete();
	}

	public void deleteNotice(long objId, byte noticeType) {
		Query query = manager.createQuery();
		query.setTable(Notice.class);
		query.where("objid=? and noticetype=?").setParam(objId).setParam(
				noticeType);
		query.delete();
	}
}