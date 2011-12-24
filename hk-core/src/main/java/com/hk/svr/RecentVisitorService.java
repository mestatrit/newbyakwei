package com.hk.svr;

import java.util.List;

import com.hk.bean.RecentVisitor;

public interface RecentVisitorService {
	/**
	 * 存储最近访问者到数据库，不包括自己访问.如果访问者不存在就添加最近访问者，如果存在，就不添加，只更新访问时间
	 * 
	 * @param userId
	 * @param visitorId
	 * @param maxSize
	 *            最多存储数量
	 */
	void addVisitor(long userId, long visitorId, int maxSize);

	/**
	 * 获得某个用户的所有最近访问
	 * 
	 * @param userId
	 * @return
	 */
	List<RecentVisitor> getVisitorByUserId(long userId, int size);
}