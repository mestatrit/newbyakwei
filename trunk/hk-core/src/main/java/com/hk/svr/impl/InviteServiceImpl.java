package com.hk.svr.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.Invite;
import com.hk.bean.InviteCode;
import com.hk.bean.InviteCodeData;
import com.hk.bean.ShortUrl;
import com.hk.bean.UserInviteConfig;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.InviteService;
import com.hk.svr.invite.exception.OutOfInviteLimitException;

public class InviteServiceImpl implements InviteService {

	@Autowired
	private QueryManager manager;

	public void acceptInvite(long inviteId, long friendId, byte addhkbflg) {
		Query query = this.manager.createQuery();
		Invite o = query.getObjectById(Invite.class, inviteId);
		if (o == null || o.getFriendId() > 0) {
			return;
		}
		query.setTable(Invite.class);
		query.addField("friendid", friendId);
		query.addField("regtime", new Date());
		query.addField("addhkbflg", addhkbflg);
		query.where("inviteid=?").setParam(inviteId);
		query.update();
	}

	public void acceptNewInvite(long userId, long friendId, byte inviteType,
			byte addhkbflg) {
		Query query = this.manager.createQuery();
		query.setTable(Invite.class);
		query.where("userid=? and friendid=?").setParam(userId).setParam(
				friendId);
		if (query.count() == 0) {
			Date date = new Date();
			Invite invite = new Invite();
			invite.setUserId(userId);
			invite.setFriendId(friendId);
			invite.setEmail(null);
			invite.setCreateTime(date);
			invite.setRegTime(date);
			invite.setInviteCount(1);
			invite.setUptime(date);
			invite.setInviteType(inviteType);
			invite.setAddhkbflg(addhkbflg);
			this.insertInvite(invite);
		}
	}

	private long insertInvite(Invite invite) {
		Query query = this.manager.createQuery();
		query.addField("userid", invite.getUserId());
		query.addField("friendid", invite.getFriendId());
		query.addField("email", invite.getEmail());
		query.addField("createtime", invite.getCreateTime());
		query.addField("regtime", invite.getRegTime());
		query.addField("invitecount", invite.getInviteCount());
		query.addField("uptime", invite.getUptime());
		query.addField("invitetype", invite.getInviteType());
		query.addField("addhkbflg", invite.getAddhkbflg());
		return query.insert(Invite.class).longValue();
	}

	public void updateInvite(Invite invite) {
		Query query = this.manager.createQuery();
		query.setTable(Invite.class);
		query.addField("userid", invite.getUserId());
		query.addField("friendid", invite.getFriendId());
		query.addField("email", invite.getEmail());
		query.addField("createtime", invite.getCreateTime());
		query.addField("regtime", invite.getRegTime());
		query.addField("invitecount", invite.getInviteCount());
		query.addField("uptime", invite.getUptime());
		query.addField("invitetype", invite.getInviteType());
		query.addField("addhkbflg", invite.getAddhkbflg());
		query.where("inviteid=?").setParam(invite.getInviteId());
		query.update();
	}

	public long createInvite(long userId, String email, byte inviteType)
			throws OutOfInviteLimitException {
		Date date = new Date();
		Query query = this.manager.createQuery();
		query.setTable(Invite.class);
		query.where("userid=? and email=?").setParam(userId).setParam(email);
		Invite o = query.getObject(Invite.class);
		if (o == null) {
			Invite invite = new Invite();
			invite.setUserId(userId);
			invite.setFriendId(0);
			invite.setEmail(email);
			invite.setCreateTime(date);
			invite.setRegTime(date);
			invite.setInviteCount(1);
			invite.setUptime(date);
			invite.setInviteType(Invite.INVITETYPE_EMAIL);
			return this.insertInvite(invite);
		}
		Calendar c = Calendar.getInstance();
		Calendar now = Calendar.getInstance();
		c.setTime(o.getUptime());
		int count = o.getInviteCount();
		if (c.get(Calendar.DATE) == now.get(Calendar.DATE)) {// 当天发送
			if (o.getInviteCount() >= 3) {// 超过3次禁止再次发送
				throw new OutOfInviteLimitException("userid [ " + userId
						+ " ] out of invite 3 count");
			}
		}
		else {// 不是当天,清0操作
			count = 0;
		}
		query.setTable(Invite.class);
		query.addField("invitecount", count + 1);
		query.addField("uptime", date);
		query.where("inviteid=?").setParam(o.getInviteId());
		query.update();
		return o.getInviteId();
	}

	public List<Invite> getInviteList(long userId, int begin, int size) {
		Query query = this.manager.createQuery();
		query.setTable(Invite.class);
		query.where("userid=?").setParam(userId);
		query.orderByDesc("inviteid");
		return query.list(begin, size, Invite.class);
	}

	public List<Invite> getSuccessList(long userId, int begin, int size) {
		Query query = this.manager.createQuery();
		query.setTable(Invite.class);
		query.where("userid=? and friendid!=?").setParam(userId).setParam(0);
		query.orderByDesc("inviteid");
		return query.list(begin, size, Invite.class);
	}

	public Invite getInvite(long inviteId) {
		Query query = this.manager.createQuery();
		return query.getObjectById(Invite.class, inviteId);
	}

	public List<Invite> getSuccessInviteListByFriendId(long friendId) {
		Query query = this.manager.createQuery();
		return query.listEx(Invite.class, "friendid=?",
				new Object[] { friendId });
	}

	public InviteCode getInviteCodeByData(String data) {
		Query query = this.manager.createQuery();
		return query.getObjectEx(InviteCode.class, "data=?",
				new Object[] { data });
	}

	public int countInviteCodeByUserIdAndUseflg(long userId, byte useflg) {
		Query query = this.manager.createQuery();
		return query.count(InviteCode.class, "userid=? and useflg=?",
				new Object[] { userId, useflg });
	}

	public synchronized void createShortUrl(ShortUrl shortUrl) {
		InviteCodeData inviteCodeData = this.getInviteCodeData();
		String data = null;
		Query query = this.manager.createQuery();
		while (true) {
			data = inviteCodeData.getNextShortKey();
			if (query.count(InviteCode.class, "data=?", new Object[] { data }) == 0) {
				shortUrl.setShortKey(data);
				shortUrl.setCreateTime(new Date());
				query.updateObject(inviteCodeData);
				query.insertObject(shortUrl);
				break;
			}
		}
	}

	public InviteCode createInviteCode(long userId) {
		InviteCode inviteCode = new InviteCode();
		inviteCode.setUserId(userId);
		inviteCode.setUseflg(InviteCode.USEFLG_N);
		InviteCodeData inviteCodeData = this.getInviteCodeData();
		String data = null;
		Query query = this.manager.createQuery();
		while (true) {
			data = inviteCodeData.getNextShortKey();
			if (query.count(InviteCode.class, "data=?", new Object[] { data }) == 0) {
				inviteCode.setData(data);
				query.updateObject(inviteCodeData);
				query.insertObject(inviteCode);
				break;
			}
		}
		return inviteCode;
	}

	public UserInviteConfig getUserInviteConfig(long userId) {
		Query query = this.manager.createQuery();
		return query.getObjectById(UserInviteConfig.class, userId);
	}

	public void saveUserInviteConfig(UserInviteConfig userInviteConfig) {
		Query query = this.manager.createQuery();
		UserInviteConfig o = this.getUserInviteConfig(userInviteConfig
				.getUserId());
		if (o == null) {
			query.insertObject(userInviteConfig);
		}
		else {
			query.updateObject(userInviteConfig);
		}
	}

	private InviteCodeData getInviteCodeData() {
		Query query = this.manager.createQuery();
		return query.getObjectById(InviteCodeData.class, 1);
	}

	public List<InviteCode> getInviteCodeListByUserIdAndUseflg(long userId,
			byte useflg, int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(InviteCode.class, "userid=? and useflg=?",
				new Object[] { userId, useflg }, "oid asc", begin, size);
	}

	public void updateInviteCode(InviteCode inviteCode) {
		Query query = this.manager.createQuery();
		query.updateObject(inviteCode);
	}
}