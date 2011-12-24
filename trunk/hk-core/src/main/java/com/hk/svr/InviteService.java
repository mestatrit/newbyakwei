package com.hk.svr;

import java.util.List;

import com.hk.bean.Invite;
import com.hk.bean.InviteCode;
import com.hk.bean.UserInviteConfig;
import com.hk.svr.invite.exception.OutOfInviteLimitException;

public interface InviteService {

	long createInvite(long userId, String email, byte inviteType)
			throws OutOfInviteLimitException;

	void acceptInvite(long inviteId, long friendId, byte addhkbflg);

	void acceptNewInvite(long userId, long friendId, byte inviteType,
			byte addhkbflg);

	List<Invite> getInviteList(long userId, int begin, int size);

	List<Invite> getSuccessList(long userId, int begin, int size);

	Invite getInvite(long inviteId);

	List<Invite> getSuccessInviteListByFriendId(long friendId);

	void updateInvite(Invite invite);

	InviteCode getInviteCodeByData(String data);

	InviteCode createInviteCode(long userId);

	int countInviteCodeByUserIdAndUseflg(long userId, byte useflg);

	UserInviteConfig getUserInviteConfig(long userId);

	/**
	 * 创建或更新对象
	 * 
	 * @param userInviteConfig
	 *            2010-4-28
	 */
	void saveUserInviteConfig(UserInviteConfig userInviteConfig);

	List<InviteCode> getInviteCodeListByUserIdAndUseflg(long userId,
			byte useflg, int begin, int size);

	void updateInviteCode(InviteCode inviteCode);
}