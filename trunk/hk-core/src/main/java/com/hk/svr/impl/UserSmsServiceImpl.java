package com.hk.svr.impl;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import com.hk.bean.MsgIdData;
import com.hk.bean.UserBatchSms;
import com.hk.bean.UserSms;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.UserSmsService;

public class UserSmsServiceImpl implements UserSmsService {
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

	private DecimalFormat df = new DecimalFormat("00000");

	@Autowired
	private QueryManager manager;

	public void createUserSms(UserSms userSms) {
		if (userSms.getReceiverIdList() != null
				&& userSms.getReceiverIdList().size() == 1) {
			userSms
					.setReceiverId(userSms.getReceiverIdList().iterator()
							.next());
		}
		userSms.setSmsstate((byte) -1);
		userSms.setMsgId(this.getMsgId());
		userSms.setCreateTime(new Date());
		if (userSms.getReceiverIdList() != null
				&& userSms.getReceiverIdList().size() > 1) {
			userSms.setSmsflg(UserSms.SMSFLG_GROUP);
		}
		else {
			userSms.setSmsflg(UserSms.SMSFLG_SINGLE);
		}
		Query query = manager.createQuery();
		query.addField("msgid", userSms.getMsgId());
		query.addField("senderid", userSms.getSenderId());
		query.addField("content", userSms.getContent());
		query.addField("port", userSms.getPort());
		query.addField("smsstate", userSms.getSmsstate());
		query.addField("createtime", userSms.getCreateTime());
		query.addField("receiverid", userSms.getReceiverId());
		query.addField("statemsg", userSms.getStatemsg());
		query.addField("smsflg", userSms.getSmsflg());
		query.insert(UserSms.class).longValue();
		if (userSms.getReceiverIdList() != null
				&& userSms.getReceiverIdList().size() > 1) {
			for (Long id : userSms.getReceiverIdList()) {
				UserBatchSms userBatchSms = new UserBatchSms();
				userBatchSms.setMsgId(userSms.getMsgId());
				userBatchSms.setReceiverId(id);
				this.createUserBatchSms(userBatchSms);
			}
		}
	}

	private void createUserBatchSms(UserBatchSms userBatchSms) {
		userBatchSms.setSmsstate((byte) -1);
		Query query = manager.createQuery();
		query.addField("msgid", userBatchSms.getMsgId());
		query.addField("receiverid", userBatchSms.getReceiverId());
		query.addField("smsstate", userBatchSms.getSmsstate());
		query.addField("statemsg", userBatchSms.getStatemsg());
		query.insert(UserBatchSms.class);
	}

	private List<UserBatchSms> getUserBatchSmsListByMsgIdAndReceiverId(
			long msgId, long receiverId) {
		Query query = manager.createQuery();
		return query.listEx(UserBatchSms.class, "msgid=? and receiverid=?",
				new Object[] { msgId, receiverId });
	}

	private void updateUserBatchSms(UserBatchSms userBatchSms) {
		Query query = manager.createQuery();
		query.setTable(UserBatchSms.class);
		query.addField("msgid", userBatchSms.getMsgId());
		query.addField("receiverid", userBatchSms.getReceiverId());
		query.addField("smsstate", userBatchSms.getSmsstate());
		query.addField("statemsg", userBatchSms.getStatemsg());
		query.where("sysid=?").setParam(userBatchSms.getSysId());
		query.update();
	}

	public List<UserSms> getUserSmsList(int begin, int size) {
		Query query = manager.createQuery();
		query.setTable(UserSms.class);
		query.orderByDesc("msgid");
		return query.list(begin, size, UserSms.class);
	}

	public List<UserSms> getUserSmsListByUserId(long userId, int begin, int size) {
		Query query = manager.createQuery();
		query.setTable(UserSms.class);
		query.where("userid=?").setParam(userId);
		query.orderByDesc("msgid");
		return query.list(begin, size, UserSms.class);
	}

	public void updateUserSms(UserSms userSms) {
		Query query = manager.createQuery();
		query.setTable(UserSms.class);
		query.addField("senderid", userSms.getSenderId());
		query.addField("content", userSms.getContent());
		query.addField("port", userSms.getPort());
		query.addField("smsstate", userSms.getSmsstate());
		query.addField("createtime", userSms.getCreateTime());
		query.addField("receiverId", userSms.getReceiverId());
		query.addField("statemsg", userSms.getStatemsg());
		query.addField("smsflg", userSms.getSmsflg());
		query.where("msgId=?").setParam(userSms.getMsgId());
		query.update();
	}

	private synchronized long getMsgId() {
		Query query = this.manager.createQuery();
		query.setTable(MsgIdData.class);
		query.orderByDesc("msgid");
		MsgIdData o = query.getObject(MsgIdData.class);
		if (o == null) {
			o = new MsgIdData();
			o.setMsgId(1);
			this.createMsgIdData(o);
			return Long.parseLong(sdf.format(new Date())
					+ df.format(o.getMsgId()));
		}
		int v = o.getMsgId() + 1;
		if (o.getMsgId() >= 99999) {
			v = 1;
		}
		MsgIdData msgIdData = new MsgIdData();
		msgIdData.setMsgId(v);
		this.createMsgIdData(msgIdData);
		return Long.parseLong(sdf.format(new Date()) + df.format(o.getMsgId()));
	}

	private void createMsgIdData(MsgIdData msgIdData) {
		Query query = this.manager.createQuery();
		query.addField("msgid", msgIdData.getMsgId());
		query.insert(MsgIdData.class);
	}

	public UserSms getUserSms(long msgId) {
		Query query = this.manager.createQuery();
		return query.getObjectById(UserSms.class, msgId);
	}

	public void processUpdateUserSms(long msgId, long receiverId, byte state,
			String statemsg) {
		// 更新记录中的发送状态
		UserSms userSms = this.getUserSms(msgId);
		if (userSms == null) {
			return;
		}
		userSms.setSmsstate(state);
		userSms.setStatemsg(statemsg);
		this.updateUserSms(userSms);
		// 如果是批量发送，则要处理批量表
		if (userSms.getSmsflg() == UserSms.SMSFLG_GROUP) {
			List<UserBatchSms> list = this
					.getUserBatchSmsListByMsgIdAndReceiverId(msgId, receiverId);
			for (UserBatchSms o : list) {
				o.setSmsstate(state);
				o.setStatemsg(statemsg);
				this.updateUserBatchSms(o);
			}
		}
	}

	public List<UserSms> getUserSmsListInId(List<Long> idList) {
		if (idList.isEmpty()) {
			return new ArrayList<UserSms>();
		}
		StringBuilder sb = new StringBuilder();
		for (Long l : idList) {
			sb.append(l).append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		String sql = "select * from usersms where msgid in (" + sb.toString()
				+ ")";
		Query query = this.manager.createQuery();
		return query.listBySqlEx("ds1", sql, UserSms.class);
	}

	public Map<Long, UserSms> getUserSmsMapInId(List<Long> idList) {
		List<UserSms> list = this.getUserSmsListInId(idList);
		Map<Long, UserSms> map = new HashMap<Long, UserSms>();
		for (UserSms o : list) {
			map.put(o.getMsgId(), o);
		}
		return map;
	}
}