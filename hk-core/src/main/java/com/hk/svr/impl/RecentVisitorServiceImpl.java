package com.hk.svr.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.RecentVisitor;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.RecentVisitorService;

public class RecentVisitorServiceImpl implements RecentVisitorService {
	@Autowired
	private QueryManager manager;

	public void addVisitor(long userId, long visitorId, int maxSize) {
		if (userId == visitorId) {
			return;
		}
		Query query = this.manager.createQuery();
		if (query.count(RecentVisitor.class, "userid=? and visitorid=?",
				new Object[] { userId, visitorId }) == 0) {
			query.addField("userid", userId);
			query.addField("visitorid", visitorId);
			query.addField("uptime", new Date());
			query.insert(RecentVisitor.class);
		}
		else {
			query.addField("uptime", new Date());
			query.update(RecentVisitor.class, "userid=? and visitorid=?",
					new Object[] { userId, visitorId });
		}
		// 如果数量超过 maxSize就删除超过的数据
		int size = maxSize + 1;
		List<RecentVisitor> list = this.getVisitorByUserId(userId, size);
		if (list.size() == size) {
			query
					.delete(RecentVisitor.class, "userid=? and oid<=?",
							new Object[] { userId,
									list.get(list.size() - 1).getOid() });
		}
	}

	public List<RecentVisitor> getVisitorByUserId(long userId, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(RecentVisitor.class, "userid=?",
				new Object[] { userId }, "uptime desc", 0, size);
	}
}