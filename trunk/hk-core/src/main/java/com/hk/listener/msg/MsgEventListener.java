package com.hk.listener.msg;

import java.util.EventListener;

import com.hk.bean.SendInfo;

public interface MsgEventListener extends EventListener {
	void msgCreated(SendInfo sendInfo, long userId, long senderId, String msg);
}