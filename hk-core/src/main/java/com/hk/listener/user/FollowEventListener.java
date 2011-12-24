package com.hk.listener.user;

import java.util.EventListener;

public interface FollowEventListener extends EventListener {
	void followCreated(FollowEvent event);

	void followDeleted(FollowEvent event);
}