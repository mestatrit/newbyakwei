package com.hk.svr.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.hk.bean.HkGroup;
import com.hk.bean.HkGroupUser;
import com.hk.bean.HkUserGroup;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.GroupService;

public class GroupServiceImpl implements GroupService {
	@Autowired
	private QueryManager manager;

	public void addGroupUser(int groupId, long userId) {
		HkGroupUser o = this.getGroupUser(groupId, userId);
		if (o == null) {
			Query query = this.manager.createQuery();
			query.addField("groupid", groupId);
			query.addField("userid", userId);
			query.insert(HkGroupUser.class);
			query.addField("groupid", groupId);
			query.addField("userid", userId);
			query.insert(HkUserGroup.class);
			this.updateGroupUserCount(groupId);
		}
	}

	private void updateGroupUserCount(int groupId) {
		Query query = this.manager.createQuery();
		query.setTable(HkGroupUser.class);
		query.where("groupid=?").setParam(groupId);
		int count = query.count();
		query.setTable(HkGroup.class);
		query.addField("ucount", count);
		query.where("groupid=?").setParam(groupId);
		query.update();
	}

	public HkGroup getGroup(int groupId) {
		Query query = this.manager.createQuery();
		return query.getObjectById(HkGroup.class, groupId);
	}

	public HkGroup getGroupByName(String name) {
		Query query = this.manager.createQuery();
		query.setTable(HkGroup.class);
		query.where("name=?").setParam(name);
		return query.getObject(HkGroup.class);
	}

	public void createGroup(HkGroup group) {
		if (this.getGroupByName(group.getName()) == null) {
			Query query = this.manager.createQuery();
			query.addField("name", group.getName());
			query.addField("ucount", group.getUcount());
			query.insert(HkGroup.class);
		}
	}

	public void deleteGroupUser(int groupId, long userId) {
		Query query = this.manager.createQuery();
		query.setTable(HkGroupUser.class);
		query.where("groupid=? and userid=?").setParam(groupId)
				.setParam(userId);
		query.delete();
		query.setTable(HkUserGroup.class);
		query.where("groupid=? and userid=?").setParam(groupId)
				.setParam(userId);
		query.delete();
		this.updateGroupUserCount(groupId);
	}

	public List<HkGroup> getGroupList(int begin, int size) {
		Query query = this.manager.createQuery();
		query.setTable(HkGroup.class);
		return query.list(begin, size, HkGroup.class);
	}

	public List<HkGroup> getGroupListByUserId(long userId, int begin, int size) {
		Query query = this.manager.createQuery();
		String sql = "select g.* from hkgroup g,hkusergroup ug where ug.userid=? and ug.groupid=g.groupid order by ug.sysid desc";
		return query.listBySql("ds1", sql, begin, size, HkGroup.class, userId);
	}

	public HkGroupUser getGroupUser(int groupId, long userId) {
		Query query = this.manager.createQuery();
		query.setTable(HkGroupUser.class);
		query.where("groupid=? and userid=?").setParam(groupId)
				.setParam(userId);
		return query.getObject(HkGroupUser.class);
	}

	public List<HkGroupUser> getGroupUserListByGroupId(int groupId, int begin,
			int size) {
		Query query = this.manager.createQuery();
		query.setTable(HkGroupUser.class);
		query.where("groupid=?").setParam(groupId);
		return query.list(begin, size, HkGroupUser.class);
	}

	public List<HkGroupUser> getGroupUserListByGroupId(int groupId) {
		Query query = this.manager.createQuery();
		return query.listEx(HkGroupUser.class, "groupid=?",
				new Object[] { groupId });
	}

	public List<HkGroup> getHkGroupListSortByUcount(int size) {
		Query query = this.manager.createQuery();
		query.setTable(HkGroup.class);
		query.orderByDesc("ucount");
		return query.list(0, size, HkGroup.class);
	}
}