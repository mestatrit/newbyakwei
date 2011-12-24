package com.hk.svr.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.hk.bean.BlockUser;
import com.hk.bean.Follow;
import com.hk.bean.Followed;
import com.hk.bean.User;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.FollowService;
import com.hk.svr.friend.exception.AlreadyBlockException;

public class FollowServiceImpl implements FollowService {

	@Autowired
	private QueryManager queryManager;

	/**
	 * 其他相关操作查看app-aop.xml
	 */
	public boolean addFollow(long userId, long friendId, String ip,
			boolean noticeAndFeed) throws AlreadyBlockException {
		if (userId == friendId) {
			return false;
		}
		BlockUser blockUser = this.getBlockUser(friendId, userId);
		if (blockUser != null) {
			throw new AlreadyBlockException(friendId, userId);
		}
		Query query = this.queryManager.createQuery();
		/** ************** 如果黑名单中有此人，就从黑名单中删除 ****************** */
		query.setTable(BlockUser.class);
		query.where("userid=? and blockuserid=?").setParam(userId).setParam(
				friendId).delete();
		Follow friend_me = this.getFollow(userId, friendId);
		boolean ok = false;
		if (friend_me == null) {// 加对方为好友
			ok = true;
			query.addField("userid", userId);
			query.addField("friendid", friendId);
			query.addField("bothfollow", Follow.FOLLOW_SINGLE);
			query.insert(Follow.class);
			/** ************** 添加follow ****************** */
			query.addField("userid", friendId).addField("followinguserid",
					userId).addField("bothfollow", Follow.FOLLOW_SINGLE)
					.insert(Followed.class);
		}
		Follow friend_other = this.getFollow(friendId, userId);
		if (friend_other != null) {// 如果对方已经加自己为好友，那么更新2边关系为双向好友
			this.setBothFollow(userId, friendId, Follow.FOLLOW_BOOTH);
		}
		this.updateUserFriendAndFansCountInfo(userId);
		this.updateUserFriendAndFansCountInfo(friendId);
		return ok;
	}

	public void setBothFollow(long userId, long friendId, boolean both) {
		byte code = Follow.FOLLOW_SINGLE;
		if (both) {
			code = Follow.FOLLOW_BOOTH;
		}
		this.setBothFollow(userId, friendId, code);
	}

	public void setBothFollow(long userId, long friendId, byte both) {
		this.updateFriend(userId, friendId, both);
		this.updateFriend(friendId, userId, both);
		this.updateFollowed(userId, friendId, both);
		this.updateFollowed(friendId, userId, both);
	}

	private void updateFriend(long userId, long friendId, byte both) {
		Query query = this.queryManager.createQuery();
		query.setTable(Follow.class).addField("bothfollow", both).where(
				"userid=? and friendid=?").setParam(userId).setParam(friendId)
				.update();
	}

	private void updateFollowed(long userId, long followingUserId, byte both) {
		Query query = this.queryManager.createQuery();
		query.setTable(Followed.class).addField("bothfollow", both).where(
				"userid=? and followinguserid=?").setParam(userId).setParam(
				followingUserId).update();
	}

	public Follow getFollow(long userId, long friendId) {
		Query query = this.queryManager.createQuery();
		query.setTable(Follow.class);
		query.where("userid=? and friendid=?");
		query.setParam(userId).setParam(friendId);
		Follow friend = query.getObject(Follow.class);
		return friend;
	}

	public int countFollow(long userId) {
		Query query = this.queryManager.createQuery();
		query.setTable(Follow.class).where("userid=?").setParam(userId);
		return query.count();
	}

	public int countFollowed(long userId) {
		Query query = this.queryManager.createQuery();
		query.setTable(Followed.class).where("userid=?").setParam(userId);
		return query.count();
	}

	public List<Follow> getFollowList(long userId, int begin, int size) {
		Query query = this.queryManager.createQuery();
		query.setTable(Follow.class).where("userid=?").setParam(userId);
		query.orderByDesc("sysid");
		return query.list(begin, size, Follow.class);
	}

	private ParameterizedRowMapper<Follow> wrapperMapper = new ParameterizedRowMapper<Follow>() {

		public Follow mapRow(ResultSet rs, int rowNum) throws SQLException {
			Follow follow = new Follow();
			User user = new User();
			user.setUserId(rs.getLong("userid"));
			user.setNickName(rs.getString("nickname"));
			user.setHeadflg(rs.getByte("headflg"));
			user.setHeadPath(rs.getString("headpath"));
			user.setDomain(rs.getString("domain"));
			follow.setFollowUser(user);
			follow.setBothFollow(rs.getByte("bothfollow"));
			follow.setFriendId(rs.getLong("friendid"));
			return follow;
		}
	};

	private ParameterizedRowMapper<Followed> edwrapperMapper = new ParameterizedRowMapper<Followed>() {

		public Followed mapRow(ResultSet rs, int rowNum) throws SQLException {
			Followed o = new Followed();
			User user = new User();
			user.setUserId(rs.getLong("userid"));
			user.setNickName(rs.getString("nickname"));
			user.setHeadflg(rs.getByte("headflg"));
			user.setHeadPath(rs.getString("headpath"));
			user.setDomain(rs.getString("domain"));
			o.setFollowingUser(user);
			o.setBothFollow(rs.getByte("bothfollow"));
			o.setFollowingUserId(rs.getLong("followinguserid"));
			return o;
		}
	};

	public List<Follow> getFollowListByNickName(long userId, String nickName,
			int begin, int size) {
		Query query = this.queryManager.createQuery();
		String sql = "select u.*,f.friendid,f.bothfollow from user u,follow f where f.userid=? and u.nickName like ? and f.friendid=u.userid order by f.sysid desc";
		return query.listBySqlWithMapper("ds1", sql, begin, size, wrapperMapper, userId,
				"%" + nickName + "%");
	}

	public List<Followed> getFollowedList(long userId, int begin, int size) {
		Query query = this.queryManager.createQuery();
		query.setTable(Followed.class).where("userid=?").setParam(userId);
		query.orderByDesc("sysid");
		return query.list(begin, size, Followed.class);
	}

	public List<Followed> getFollowedListByNickName(long userId,
			String nickName, int begin, int size) {
		Query query = this.queryManager.createQuery();
		String sql = "select u.*,f.followinguserid,f.bothfollow from user u,followed f where f.userid=? and u.nickName like ? and f.followinguserid=u.userid order by f.sysid desc";
		return query.listBySqlWithMapper("ds1", sql, begin, size, edwrapperMapper,
				userId, "%" + nickName + "%");
	}

	public void removeFollow(long userId, long friendId) {
		Query query = this.queryManager.createQuery();
		query.setTable(Follow.class).where("userid=? and friendid=?").setParam(
				userId).setParam(friendId).delete();
		query.setTable(Followed.class).where("userid=? and followinguserid=?")
				.setParam(friendId).setParam(userId).delete();
		this.setBothFollow(userId, friendId, Follow.FOLLOW_SINGLE);
		this.updateUserFriendAndFansCountInfo(userId);
		this.updateUserFriendAndFansCountInfo(friendId);
	}

	public void blockUser(long userId, long blockUserId)
			throws AlreadyBlockException {
		this.removeFollow(userId, blockUserId);
		this.removeFollow(blockUserId, userId);
		BlockUser blockUser = getBlockUser(blockUserId, userId);
		if (blockUser != null) {// 对方已经阻止你
			throw new AlreadyBlockException(blockUserId, userId);
		}
		BlockUser blockUser2 = getBlockUser(userId, blockUserId);
		if (blockUser2 == null) {
			Query query = this.queryManager.createQuery();
			query.addField("userid", userId).addField("blockuserid",
					blockUserId);
			query.insert(BlockUser.class);
		}
		this.updateUserFriendAndFansCountInfo(userId);
		this.updateUserFriendAndFansCountInfo(blockUserId);
	}

	public void removeBlockUser(long userId, long blockUserId) {
		Query query = this.queryManager.createQuery();
		query.setTable(BlockUser.class);
		query.where("userid=? and blockuserid=?").setParam(userId).setParam(
				blockUserId);
		query.delete();
	}

	public BlockUser getBlockUser(long userId, long blockUserId) {
		Query query = this.queryManager.createQuery();
		query.setTable(BlockUser.class);
		query.where("userid=? and blockuserid=?").setParam(userId).setParam(
				blockUserId);
		return query.getObject(BlockUser.class);
	}

	public List<Follow> getFollowList(long userId) {
		Query query = this.queryManager.createQuery();
		return query.listEx(Follow.class, "userid=?", new Object[] { userId });
	}

	public List<User> getUserListForSend(long userId, int begin, int size) {
		Query query = this.queryManager.createQuery();
		String sql = "select u.* from user u,follow f left join usercontactdegree ud on f.userid=ud.userid and f.friendid=ud.contactuserid where f.userid=? and f.bothfollow=? and f.friendid=u.userid order by ud.degree desc";
		return query.listBySql("ds1", sql, begin, size, User.class, userId,
				Follow.FOLLOW_BOOTH);
	}

	public List<User> getUserListByNickNameForSend(long userId,
			String nickName, int begin, int size) {
		Query query = this.queryManager.createQuery();
		String sql = "select u.* from user u,follow f left join usercontactdegree ud on f.userid=ud.userid and f.friendid=ud.contactuserid where f.userid=? and f.bothfollow=? and u.nickName like ? and f.friendid=u.userid order by ud.degree desc";
		return query.listBySql("ds1", sql, begin, size, User.class, userId,
				Follow.FOLLOW_BOOTH, "%" + nickName + "%");
	}

	public List<Follow> getBothFollowList(long userId) {
		Query query = this.queryManager.createQuery();
		return query.listEx(Follow.class, "userid=? and bothfollow=?",
				new Object[] { userId, Follow.FOLLOW_BOOTH }, "sysid desc");
	}

	public List<Long> getUserIdList(long userId, List<Long> idList) {
		if (idList == null || idList.size() == 0) {
			return new ArrayList<Long>();
		}
		StringBuilder sb = new StringBuilder();
		for (Long l : idList) {
			sb.append(l).append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		String sql = "select friendid from follow where userid=? and friendid in ("
				+ sb.toString() + ")";
		Query query = this.queryManager.createQuery();
		return query.listBySqlEx("ds1", sql, Long.class, userId);
	}

	private void updateUserFriendAndFansCountInfo(long userId) {
		Query query = this.queryManager.createQuery();
		int fcount = query.count(Follow.class, "userid=?",
				new Object[] { userId });
		int fedcount = query.count(Followed.class, "userid=?",
				new Object[] { userId });
		query.addField("friendcount", fcount);
		query.addField("fanscount", fedcount);
		query.updateById(User.class, userId);
	}

	public List<Followed> getFollowedListByfollowingUserIdInUserId(long userId,
			List<Long> idList) {
		if (idList.size() == 0) {
			return new ArrayList<Followed>();
		}
		StringBuilder sql = new StringBuilder(
				"select * from followed where followinguserid=? and userid in (");
		for (Long l : idList) {
			sql.append(l).append(",");
		}
		sql.deleteCharAt(sql.length() - 1).append(")");
		Query query = this.queryManager.createQuery();
		return query.listBySqlEx("ds1", sql.toString(), Followed.class, userId);
	}

	public Map<Long, Followed> getFollowedMapByfollowingUserIdInUserId(
			long userId, List<Long> idList) {
		List<Followed> list = this.getFollowedListByfollowingUserIdInUserId(
				userId, idList);
		Map<Long, Followed> map = new HashMap<Long, Followed>();
		for (Followed f : list) {
			map.put(f.getUserId(), f);
		}
		return map;
	}

	public List<Long> getFollowFriendIdListByUserId(long userId) {
		Query query = this.queryManager.createQuery();
		String sql = "select friendid from follow where userid=?";
		return query.listBySqlEx("ds1", sql, Long.class, userId);
	}

	public List<Long> getFansIdListByUserId(long userId, int begin, int size) {
		String sql = "select f.followinguserid from followed f,userupdate up where f.userid=? and f.followinguserid=up.userid order by up.uptime desc";
		Query query = this.queryManager.createQuery();
		return query.listBySql("ds1", sql, begin, size, Long.class, userId);
	}
}