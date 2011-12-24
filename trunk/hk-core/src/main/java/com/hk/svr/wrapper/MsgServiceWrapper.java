package com.hk.svr.wrapper;

import java.util.ArrayList;
import java.util.List;

import com.hk.bean.PvtChat;
import com.hk.bean.PvtChatMain;
import com.hk.bean.SendInfo;
import com.hk.listener.msg.MsgEventListener;
import com.hk.svr.MsgService;

public class MsgServiceWrapper implements MsgService {
	private MsgService msgService;

	private List<MsgEventListener> msgEventListenerList;

	public void setMsgEventListenerList(
			List<MsgEventListener> msgEventListenerList) {
		this.msgEventListenerList = msgEventListenerList;
	}

	public void addMsgEventListener(MsgEventListener listener) {
		if (msgEventListenerList == null) {
			msgEventListenerList = new ArrayList<MsgEventListener>();
		}
		msgEventListenerList.add(listener);
	}

	public void setMsgService(MsgService msgService) {
		this.msgService = msgService;
	}

	public SendInfo sendMsg(long userId, long senderId, String msg) {
		SendInfo sendInfo = msgService.sendMsg(userId, senderId, msg);
		for (MsgEventListener listener : msgEventListenerList) {
			listener.msgCreated(sendInfo, userId, senderId, msg);
		}
		return sendInfo;
	}

	public SendInfo sendMsg(long userId, long senderId, String msg, byte smsflg) {
		SendInfo sendInfo = msgService.sendMsg(userId, senderId, msg, smsflg);
		for (MsgEventListener listener : msgEventListenerList) {
			listener.msgCreated(sendInfo, userId, senderId, msg);
		}
		return sendInfo;
	}

	public int countNoRead(long userId) {
		return msgService.countNoRead(userId);
	}

	public int countPvtChat(long userId, long mainId) {
		return msgService.countPvtChat(userId, mainId);
	}

	public int countPvtChatMain(long userId) {
		return msgService.countPvtChatMain(userId);
	}

	public void deleteChat(long userId, long chatId) {
		msgService.deleteChat(userId, chatId);
	}

	public void deletePvtChatMain(long userId, long mainId) {
		msgService.deletePvtChatMain(userId, mainId);
	}

	public PvtChatMain getLastNoReadPvtChatMain(long userId) {
		return msgService.getLastNoReadPvtChatMain(userId);
	}

	public List<PvtChat> getLastPvtChatList(long userId, long mainId, int size) {
		return msgService.getLastPvtChatList(userId, mainId, size);
	}

	public PvtChat getPvtChat(long chatId) {
		return msgService.getPvtChat(chatId);
	}

	public List<PvtChat> getPvtChatList(long userId, long mainId, byte smsflg,
			int begin, int size) {
		return msgService.getPvtChatList(userId, mainId, smsflg, begin, size);
	}

	public List<PvtChat> getPvtChatList(long userId, long mainId, int begin,
			int size) {
		return msgService.getPvtChatList(userId, mainId, begin, size);
	}

	public List<PvtChat> getPvtChatListByUserIdGt(long userId, long mainId,
			long chatId, int size) {
		return msgService
				.getPvtChatListByUserIdGt(userId, mainId, chatId, size);
	}

	public PvtChatMain getPvtChatMain(long userId, long mainId) {
		return msgService.getPvtChatMain(userId, mainId);
	}

	public PvtChatMain getPvtChatMainByUserIdAndUser2Id(long userId,
			long user2Id) {
		return msgService.getPvtChatMainByUserIdAndUser2Id(userId, user2Id);
	}

	public List<PvtChatMain> getPvtChatMainList(long userId, int begin, int size) {
		return msgService.getPvtChatMainList(userId, begin, size);
	}

	public void setRead(long userId, long mainId) {
		msgService.setRead(userId, mainId);
	}

	public void updatePvtChatSmsmsgId(long chatId, long smsmsgId) {
		msgService.updatePvtChatSmsmsgId(chatId, smsmsgId);
	}
}