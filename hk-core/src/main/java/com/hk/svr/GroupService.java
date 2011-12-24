package com.hk.svr;

import java.util.List;
import com.hk.bean.HkGroup;
import com.hk.bean.HkGroupUser;

public interface GroupService {
	void createGroup(HkGroup group);

	List<HkGroup> getGroupList(int begin, int size);

	List<HkGroup> getHkGroupListSortByUcount(int size);

	List<HkGroup> getGroupListByUserId(long userId, int begin, int size);

	/**
	 * 排序为最新注册
	 * 
	 * @param groupId
	 * @param begin
	 * @param size
	 * @return
	 */
	List<HkGroupUser> getGroupUserListByGroupId(int groupId, int begin, int size);

	List<HkGroupUser> getGroupUserListByGroupId(int groupId);

	HkGroupUser getGroupUser(int groupId, long userId);

	void addGroupUser(int groupId, long userId);

	void deleteGroupUser(int groupId, long userId);

	HkGroup getGroup(int groupId);

	HkGroup getGroupByName(String name);
}