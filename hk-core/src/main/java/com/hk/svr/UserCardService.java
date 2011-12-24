package com.hk.svr;

import java.util.List;
import java.util.Map;
import com.hk.bean.MyUserCard;
import com.hk.bean.UserCard;

public interface UserCardService {
	/**
	 * 创建自己的名片
	 * 
	 * @param userCard
	 */
	void createUserCard(UserCard userCard);

	void updateUserCard(UserCard userCard);

	UserCard getUserCard(long userId);

	/**
	 * @param key 代表name or nickName
	 * @param userId
	 * @param begin
	 * @param size
	 * @return
	 */
	List<MyUserCard> getMyUserCardList(String key, long userId, int begin,
			int size);

	void createMyUserCard(long userId, long cardUserId);

	Map<Long, UserCard> getUserCardMapInUserId(List<Long> idList);

	List<UserCard> getUserCardListInUserId(List<Long> idList);

	MyUserCard getMyUserCard(long userId, long cardUserId);

	void deleteMyUserCard(long userId, long cardUserId);
}