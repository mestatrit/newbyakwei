package com.hk.svr;

import java.util.List;

import com.hk.bean.taobao.Tb_Follow;
import com.hk.bean.taobao.Tb_Followed;

public interface Tb_FollowService {

	/**
	 * 如果已经存在关系数据，就不再创建
	 * 
	 * @param tbFollow
	 *            2010-8-31
	 */
	void createTb_Follow(Tb_Follow tbFollow, boolean follow_to_sina);

	/**
	 * 如果已经存在关系数据，就不再创建
	 * 
	 * @param tbFollowed
	 *            2010-8-31
	 */
	void createTb_Followed(Tb_Followed tbFollowed);

	void deleteTb_Follow(Tb_Follow tbFollow);

	void deleteTb_Followed(Tb_Followed tbFollowed);

	Tb_Follow getTb_FollowByUseridAndFriendid(long userid, long friendid);

	Tb_Followed getTb_FollowedByUseridAndFriendid(long userid, long fansid);

	/**
	 * @param userid
	 * @param begin
	 * @param size <0时，取所有数据
	 * @return
	 *         2010-8-31
	 */
	List<Tb_Follow> getTb_FollowListByUserid(long userid, boolean buildFriend,
			int begin, int size);

	/**
	 * @param userid
	 * @param begin
	 * @param size <0时，取所有数据
	 * @return
	 *         2010-8-31
	 */
	List<Tb_Followed> getTb_FollowedListByUserid(long userid,
			boolean buildFans, int begin, int size);

	int countTb_FollowByUserid(long userid);

	int countTb_FollowedByUserid(long userid);

	List<Long> getTb_FollowedFansidListByUserid(long userid);
}