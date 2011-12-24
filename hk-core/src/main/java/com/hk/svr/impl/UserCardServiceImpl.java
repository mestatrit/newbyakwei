package com.hk.svr.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import com.hk.bean.MyUserCard;
import com.hk.bean.UserCard;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.UserCardService;

public class UserCardServiceImpl implements UserCardService {
	@Autowired
	private QueryManager manager;

	public void createUserCard(UserCard userCard) {
		userCard.checkComplete();
		Query query = this.manager.createQuery();
		query.addField("userid", userCard.getUserId());
		query.addField("homeaddr", userCard.getHomeAddr());
		query.addField("homepostcode", userCard.getHomePostcode());
		query.addField("workplace", userCard.getWorkplace());
		query.addField("workplaceweb", userCard.getWorkPlaceWeb());
		query.addField("workaddr", userCard.getWorkAddr());
		query.addField("workpostcode", userCard.getWorkPostcode());
		query.addField("anothermobile", userCard.getAnotherMobile());
		query.addField("hometelphone", userCard.getHomeTelphone());
		query.addField("qq", userCard.getQq());
		query.addField("msn", userCard.getMsn());
		query.addField("gtalk", userCard.getGtalk());
		query.addField("skype", userCard.getSkype());
		query.addField("intro", userCard.getIntro());
		query.addField("jobrank", userCard.getJobRank());
		query.addField("completeinfo", userCard.getCompleteinfo());
		query.addField("email", userCard.getEmail());
		query.addField("name", userCard.getName());
		query.addField("nickname", userCard.getNickName());
		query.addField("chgflg", userCard.getChgflg());
		query.insert(UserCard.class);
	}

	public UserCard getUserCard(long userId) {
		Query query = this.manager.createQuery();
		return query.getObjectById(UserCard.class, userId);
	}

	public void updateUserCard(UserCard userCard) {
		userCard.checkComplete();
		Query query = this.manager.createQuery();
		query.setTable(UserCard.class);
		query.addField("homeaddr", userCard.getHomeAddr());
		query.addField("homepostcode", userCard.getHomePostcode());
		query.addField("workplace", userCard.getWorkplace());
		query.addField("workplaceweb", userCard.getWorkPlaceWeb());
		query.addField("workaddr", userCard.getWorkAddr());
		query.addField("workpostcode", userCard.getWorkPostcode());
		query.addField("anothermobile", userCard.getAnotherMobile());
		query.addField("hometelphone", userCard.getHomeTelphone());
		query.addField("qq", userCard.getQq());
		query.addField("msn", userCard.getMsn());
		query.addField("gtalk", userCard.getGtalk());
		query.addField("skype", userCard.getSkype());
		query.addField("intro", userCard.getIntro());
		query.addField("jobrank", userCard.getJobRank());
		query.addField("completeinfo", userCard.getCompleteinfo());
		query.addField("email", userCard.getEmail());
		query.addField("name", userCard.getName());
		query.addField("nickname", userCard.getNickName());
		query.addField("chgflg", userCard.getChgflg());
		query.where("userid=?").setParam(userCard.getUserId());
		query.update();
	}

	public List<MyUserCard> getMyUserCardList(String key, long userId,
			int begin, int size) {
		Query query = this.manager.createQuery();
		if (key == null) {
			return this.getMyUserCardList(userId, begin, size);
		}
		String sql = "select mc.* from usercard c,myusercard mc left join usercontactdegree ud on mc.userid=ud.userid and mc.carduserid=ud.contactuserid where mc.userid=? and mc.carduserid=c.userid and (c.name like ? or c.nickname like ?) order by ud.degree desc";
		return query.listBySql("ds1", sql.toString(), begin, size,
				MyUserCard.class, userId, "%" + key + "%", "%" + key + "%");
	}

	public List<MyUserCard> getMyUserCardList(long userId, int begin, int size) {
		Query query = this.manager.createQuery();
		query.setTable(MyUserCard.class);
		query.where("userid=?").setParam(userId);
		query.orderByDesc("carduserid");
		return query.list(begin, size, MyUserCard.class);
	}

	public void createMyUserCard(long userId, long cardUserId) {
		if (userId == cardUserId) {
			return;
		}
		Query query = this.manager.createQuery();
		query.setTable(MyUserCard.class);
		query.where("userid=? and carduserid=?").setParam(userId).setParam(
				cardUserId);
		if (query.count() == 0) {
			query.addField("userid", userId);
			query.addField("carduserid", cardUserId);
			query.insert(MyUserCard.class);
		}
	}

	public Map<Long, UserCard> getUserCardMapInUserId(List<Long> idList) {
		Map<Long, UserCard> map = new HashMap<Long, UserCard>();
		if (idList == null || idList.size() == 0) {
			return map;
		}
		List<UserCard> list = this.getUserCardListInUserId(idList);
		for (UserCard o : list) {
			map.put(o.getUserId(), o);
		}
		return map;
	}

	public List<UserCard> getUserCardListInUserId(List<Long> idList) {
		if (idList.size() == 0) {
			return new ArrayList<UserCard>();
		}
		StringBuilder sb = new StringBuilder();
		for (Long l : idList) {
			sb.append(l).append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		String sql = "select * from usercard where userid in (" + sb.toString()
				+ ") order by userid desc";
		Query query = this.manager.createQuery();
		return query.listBySqlEx("ds1", sql, UserCard.class);
	}

	public MyUserCard getMyUserCard(long userId, long cardUserId) {
		Query query = this.manager.createQuery();
		query.setTable(MyUserCard.class);
		query.where("userid=? and carduserid=?").setParam(userId).setParam(
				cardUserId);
		return query.getObject(MyUserCard.class);
	}

	public void deleteMyUserCard(long userId, long cardUserId) {
		Query query = this.manager.createQuery();
		query.setTable(MyUserCard.class);
		query.where("userid=? and carduserid=?").setParam(userId).setParam(
				cardUserId);
		query.delete();
	}
}