package com.hk.svr.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.PvtChat;
import com.hk.bean.PvtChatMain;
import com.hk.bean.SendInfo;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.MsgService;

public class MsgServiceImpl implements MsgService {

	@Autowired
	private QueryManager queryManager;

	public SendInfo sendMsg(long userId, long senderId, String msg, byte smsflg) {
		Date date = new Date();
		SendInfo info = new SendInfo();
		PvtChat senderPvtChat = new PvtChat();
		senderPvtChat.setUserId(senderId);
		senderPvtChat.setSenderId(senderId);
		senderPvtChat.setCreateTime(date);
		senderPvtChat.setMsg(msg);
		senderPvtChat.setSmsflg(smsflg);
		long mainId = this.updatePvtChatMain(senderPvtChat, userId,
				PvtChatMain.READ_Y);
		senderPvtChat.setMainId(mainId);
		this.createPvtChat(senderPvtChat);
		info.setSenderPvtChat(senderPvtChat);
		PvtChat receiverPvtChat = new PvtChat();
		receiverPvtChat.setUserId(userId);
		receiverPvtChat.setSenderId(senderId);
		receiverPvtChat.setCreateTime(date);
		receiverPvtChat.setMsg(msg);
		receiverPvtChat.setSmsflg(smsflg);
		long main2Id = this.updatePvtChatMain(receiverPvtChat, senderId,
				PvtChatMain.READ_N);
		receiverPvtChat.setMainId(main2Id);
		this.createPvtChat(receiverPvtChat);
		info.setReceiverPvtChat(receiverPvtChat);
		return info;
	}

	public SendInfo sendMsg(long userId, long senderId, String msg) {
		return this.sendMsg(userId, senderId, msg, PvtChat.SmsFLG_N);
	}

	private void createPvtChat(PvtChat pvtChat) {
		Query query = this.queryManager.createQuery();
		query.addField("userid", pvtChat.getUserId());
		query.addField("senderid", pvtChat.getSenderId());
		query.addField("msg", pvtChat.getMsg());
		query.addField("createtime", pvtChat.getCreateTime());
		query.addField("mainid", pvtChat.getMainId());
		query.addField("smsflg", pvtChat.getSmsflg());
		query.addField("smsmsgid", pvtChat.getSmsmsgId());
		long id = query.insert(PvtChat.class).longValue();
		pvtChat.setChatId(id);
	}

	private long updatePvtChatMain(PvtChat pvtChat, long user2Id, byte readflg) {
		int noReadAdd = 0;
		String msg = pvtChat.getSenderId() + ">>" + pvtChat.getMsg() + ">>"
				+ pvtChat.getCreateTime().getTime();
		Query query = this.queryManager.createQuery();
		query.setTable(PvtChatMain.class);
		query.where("userid=? and user2id=?").setParam(pvtChat.getUserId())
				.setParam(user2Id);
		PvtChatMain chatMain = query.getObject(PvtChatMain.class);
		long mainId = 0;
		if (chatMain == null) {
			query.addField("userid", pvtChat.getUserId());
			query.addField("user2id", user2Id);
			query.addField("last3msg", msg);
			query.addField("readflg", readflg);
			query.addField("createtime", pvtChat.getCreateTime());
			if (readflg == PvtChatMain.READ_N) {// 未读 设置未读数量=1
				noReadAdd = 1;
			}
			query.addField("noreadcount", noReadAdd);
			mainId = query.insert(PvtChatMain.class).longValue();
		}
		else {
			mainId = chatMain.getMainId();
			if (chatMain.getLast3msg() != null) {
				String[] msgs = chatMain.getLast3msg().split("<>");
				List<String> slist = new ArrayList<String>();
				for (int i = 0; i < msgs.length; i++) {
					slist.add(msgs[i]);
				}
				if (slist.size() >= 1) {
					slist.remove(0);
				}
				slist.add(msg);
				StringBuilder sb = new StringBuilder();
				for (String s : slist) {
					sb.append(s).append("<>");
				}
				sb.deleteCharAt(sb.length() - 1);
				sb.deleteCharAt(sb.length() - 1);
				query.setTable(PvtChatMain.class);
				query.addField("last3msg", sb.toString());
				query.addField("readflg", readflg);
				query.addField("createtime", pvtChat.getCreateTime());
				if (readflg == PvtChatMain.READ_N) {// 未读+1
					noReadAdd = 1;
					query.addField("noreadcount", "add", noReadAdd);
				}
				else {
					query.addField("noreadcount", 0);// 已读，设置未读数量为0
				}
				query.where("userid=? and user2id=?").setParam(
						pvtChat.getUserId()).setParam(user2Id);
				query.update();
			}
		}
		return mainId;
	}

	public void deletePvtChatMain(long userId, long mainId) {
		PvtChatMain main = this.getPvtChatMain(userId, mainId);
		if (main == null) {
			return;
		}
		Query query = this.queryManager.createQuery();
		query.setTable(PvtChatMain.class);
		query.where("userid=? and mainid=?").setParam(userId).setParam(mainId)
				.delete();
		query.setTable(PvtChat.class);
		query.where("userid=? and mainid=?").setParam(userId).setParam(mainId)
				.delete();
	}

	public List<PvtChat> getPvtChatList(long userId, long mainId, int begin,
			int size) {
		Query query = this.queryManager.createQuery();
		query.setTable(PvtChat.class);
		query.where("userid=? and mainid=?").setParam(userId).setParam(mainId)
				.orderByDesc("chatid");
		return query.list(begin, size, PvtChat.class);
	}

	public List<PvtChat> getPvtChatListByUserIdGt(long userId, long mainId,
			long chatId, int size) {
		Query query = this.queryManager.createQuery();
		return query.listEx(PvtChat.class,
				"userid=? and mainid=? and chatid>?", new Object[] { userId,
						mainId, chatId }, "chatid desc", 0, size);
	}

	public List<PvtChat> getPvtChatList(long userId, long mainId, byte smsflg,
			int begin, int size) {
		Query query = this.queryManager.createQuery();
		query.setTable(PvtChat.class);
		query.where("userid=? and mainid=? and smsflg=?").setParam(userId)
				.setParam(mainId).setParam(smsflg).orderByDesc("chatid");
		return query.list(begin, size, PvtChat.class);
	}

	public List<PvtChat> getLastPvtChatList(long userId, long mainId, int size) {
		Query query = this.queryManager.createQuery();
		query.setTable(PvtChat.class);
		query.where("userid=? and mainid=?").setParam(userId).setParam(mainId)
				.orderByDesc("chatid");
		return query.list(0, size, PvtChat.class);
	}

	public int countPvtChat(long userId, long mainId) {
		Query query = this.queryManager.createQuery();
		query.setTable(PvtChat.class);
		query.where("userid=? and mainid=?").setParam(userId).setParam(mainId);
		return query.count();
	}

	public PvtChatMain getPvtChatMain(long userId, long mainId) {
		Query query = this.queryManager.createQuery();
		query.setTable(PvtChatMain.class).where("userid=? and mainid=?")
				.setParam(userId).setParam(mainId);
		return query.getObject(PvtChatMain.class);
	}

	public PvtChatMain getPvtChatMainByUserIdAndUser2Id(long userId,
			long user2Id) {
		Query query = this.queryManager.createQuery();
		query.setTable(PvtChatMain.class).where("userid=? and user2id=?")
				.setParam(userId).setParam(user2Id);
		return query.getObject(PvtChatMain.class);
	}

	public List<PvtChatMain> getPvtChatMainList(long userId, int begin, int size) {
		Query query = this.queryManager.createQuery();
		query.setTable(PvtChatMain.class).where("userid=?").setParam(userId)
				.orderByDesc("createtime");
		return query.list(begin, size, PvtChatMain.class);
	}

	public int countPvtChatMain(long userId) {
		Query query = this.queryManager.createQuery();
		return query.count(PvtChatMain.class, "userid=?",
				new Object[] { userId });
	}

	public int countNoRead(long userId) {
		Query query = this.queryManager.createQuery();
		query.setTable(PvtChatMain.class).where("userid=? and readflg=?")
				.setParam(userId).setParam(PvtChatMain.READ_N);
		return query.count();
	}

	public void setRead(long userId, long mainId) {
		Query query = this.queryManager.createQuery();
		query.setTable(PvtChatMain.class);
		query.addField("readflg", 1);
		query.addField("noreadcount", 0);
		query.where("userid=? and mainid=?").setParam(userId).setParam(mainId);
		query.update();
	}

	public PvtChatMain getLastNoReadPvtChatMain(long userId) {
		Query query = this.queryManager.createQuery();
		query.setTable(PvtChatMain.class);
		query.where("userid=? and readflg=?").setParam(userId).setParam(
				PvtChatMain.READ_N).orderByDesc("mainid");
		return query.getObject(PvtChatMain.class);
	}

	public void deleteChat(long userId, long chatId) {
		PvtChat pvtChat = this.getPvtChat(chatId);
		if (pvtChat == null) {
			return;
		}
		Query query = this.queryManager.createQuery();
		query.setTable(PvtChat.class);
		query.where("chatid=? and userid=?").setParam(chatId).setParam(userId);
		query.delete();
		List<PvtChat> list = this.getLastPvtChatList(userId, pvtChat
				.getMainId(), 1);
		if (list.size() == 0) {
			this.deletePvtChatMain(userId, pvtChat.getMainId());
		}
		else {
			PvtChat o = list.iterator().next();
			String last3msg = o.getSenderId() + ">>" + o.getMsg() + ">>"
					+ o.getCreateTime().getTime();
			query.setTable(PvtChatMain.class);
			query.addField("last3msg", last3msg);
			query.where("mainid=?").setParam(pvtChat.getMainId());
			query.update();
		}
	}

	public PvtChat getPvtChat(long chatId) {
		Query query = this.queryManager.createQuery();
		return query.getObjectById(PvtChat.class, chatId);
	}

	public void updatePvtChatSmsmsgId(long userId, long senderId, long smsmsgId) {
		Query query = this.queryManager.createQuery();
		query.addField("smsmsgid", smsmsgId);
		query.update(PvtChat.class, "userid=? and senderid=?", new Object[] {
				userId, senderId });
	}

	public void updatePvtChatSmsmsgId(long chatId, long smsmsgId) {
		Query query = this.queryManager.createQuery();
		query.addField("smsmsgid", smsmsgId);
		query.update(PvtChat.class, "chatid=?", new Object[] { chatId });
	}
}