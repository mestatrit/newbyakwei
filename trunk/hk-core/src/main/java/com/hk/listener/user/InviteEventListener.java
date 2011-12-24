package com.hk.listener.user;

import java.util.EventListener;

public interface InviteEventListener extends EventListener {
	void acceptInvite(long inviteId, long friendId);

	void acceptNewInvite(long userId, long friendId, boolean needNoticeAndFeed);
}