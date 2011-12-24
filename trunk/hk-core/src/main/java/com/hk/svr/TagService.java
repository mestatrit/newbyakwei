package com.hk.svr;

import java.util.Date;
import java.util.List;

import com.hk.bean.Tag;
import com.hk.bean.UserTag;

public interface TagService {

	Tag createTag(String tagName);

	Tag getTag(long tagId);

	Tag getTagByName(String name);

	/**
	 * @param tagId
	 * @param userId
	 *            从某人的喇叭中点击
	 * @param add
	 */
	void addTagClick(long tagId, long userId, int add);

	List<UserTag> getUserTagList(long userId, int begin, int size);

	/**
	 * user使用的标签集合，按照最新排序
	 * 
	 * @param userId
	 * @param begin
	 * @param size
	 * @return
	 */
	List<Tag> getTagListByUserId(long userId, int begin, int size);

	/**
	 * 根据喇叭数量排序取自己加入的频道
	 * 
	 * @param userId
	 * @param begin
	 * @param size
	 * @return
	 */
	List<Tag> getTagListByUserIdOrderByLabaCount(long userId, int begin,
			int size);

	List<Tag> getTagListByUserIdOrderByHot(long userId, int begin, int size);

	/**
	 * 根据喇叭数量排序取频道
	 * 
	 * @param begin
	 * @param size
	 * @return
	 */
	List<Tag> getTagListOrderByLabaCount(int begin, int size);

	List<Tag> getTagListOrderByHot(int begin, int size);

	void addUserTag(long userId, long tagId);

	void updateTagHot(Tag tag);

	void updateUpdateTime(long tagId, Date updateTime);

	List<Tag> getTagListByUpdateTimeRange(Date beginTime, Date endTime);

	void updateHot(long tagId, int hot);

	int countLabaTagByLabaIdAndAccessional(long labaId, byte accessional);
}