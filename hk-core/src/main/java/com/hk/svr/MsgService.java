package com.hk.svr;

import java.util.List;
import com.hk.bean.PvtChat;
import com.hk.bean.PvtChatMain;
import com.hk.bean.SendInfo;

public interface MsgService {
	SendInfo sendMsg(long userId, long senderId, String msg);

	SendInfo sendMsg(long userId, long senderId, String msg, byte smsflg);

	List<PvtChatMain> getPvtChatMainList(long userId, int begin, int size);

	int countPvtChatMain(long userId);

	List<PvtChat> getPvtChatList(long userId, long mainId, int begin, int size);

	List<PvtChat> getPvtChatList(long userId, long mainId, byte smsflg,
			int begin, int size);

	List<PvtChat> getLastPvtChatList(long userId, long mainId, int size);

	int countPvtChat(long userId, long mainId);

	PvtChatMain getPvtChatMain(long userId, long mainId);

	PvtChatMain getLastNoReadPvtChatMain(long userId);

	void deletePvtChatMain(long userId, long mainId);

	void deleteChat(long userId, long chatId);

	int countNoRead(long userId);

	void setRead(long userId, long mainId);

	PvtChatMain getPvtChatMainByUserIdAndUser2Id(long userId, long user2Id);

	PvtChat getPvtChat(long chatId);

	void updatePvtChatSmsmsgId(long chatId, long smsmsgId);

	List<PvtChat> getPvtChatListByUserIdGt(long userId, long mainId,
			long chatId, int size);
}