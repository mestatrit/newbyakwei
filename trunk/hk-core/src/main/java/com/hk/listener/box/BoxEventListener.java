package com.hk.listener.box;

import java.util.EventListener;

import com.hk.bean.Box;
import com.hk.svr.BoxOpenResult;

public interface BoxEventListener extends EventListener {

	void boxCreated(Box box);

	void boxOpened(BoxOpenResult boxOpenResult, long userId, long boxId,
			String ip);
}