package com.hk.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.HkUtil;
import com.hk.svr.UserService;
import com.hk.svr.pub.Err;

@Table(name = "pvtchat", id = "chatid")
public class PvtChat {
	public static final byte SmsFLG_N = 0;

	public static final byte SmsFLG_Y = 1;

	private long chatId;

	private long userId;

	private long senderId;

	private String msg;

	private Date createTime;

	private long mainId;

	private byte smsflg;

	private long smsmsgId;

	private User sender;

	public long getTime() {
		return this.createTime.getTime();
	}

	public long getSmsmsgId() {
		return smsmsgId;
	}

	public void setSmsmsgId(long smsmsgId) {
		this.smsmsgId = smsmsgId;
	}

	public void setSmsflg(byte smsflg) {
		this.smsflg = smsflg;
	}

	public byte getSmsflg() {
		return smsflg;
	}

	public void setMainId(long mainId) {
		this.mainId = mainId;
	}

	public long getMainId() {
		return mainId;
	}

	public long getChatId() {
		return chatId;
	}

	public void setChatId(long chatId) {
		this.chatId = chatId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getSenderId() {
		return senderId;
	}

	public void setSenderId(long senderId) {
		this.senderId = senderId;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public boolean isSmsSend() {
		if (this.smsflg == SmsFLG_Y) {
			return true;
		}
		return false;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public static int validate(String msg) {
		if (DataUtil.isEmpty(msg)) {
			return Err.MSG_MSG_ERROR;
		}
		if (msg.length() > 140) {
			return Err.MSG_MSG_ERROR;
		}
		return Err.SUCCESS;
	}

	public static void initUserInList(List<PvtChat> list) {
		List<Long> idList = new ArrayList<Long>();
		for (PvtChat o : list) {
			idList.add(o.getSenderId());
		}
		UserService userService = (UserService) HkUtil.getBean("userService");
		Map<Long, User> map = userService.getUserMapInId(idList);
		for (PvtChat o : list) {
			o.setSender(map.get(o.getSenderId()));
		}
	}

	/**
	 * 内容中带有url时，可以转换为点击的url
	 * 
	 * @return
	 */
	public String getSpHtml() {
		List<String> urlist = DataUtil.parseUrl(this.msg);
		StringBuilder v = new StringBuilder();
		String cnt = this.msg;
		for (String url : urlist) {
			String localurl = url;
			v.append("<a href=\"").append(localurl).append("\"");
			v.append(" target=\"_blank\"");
			v.append(">").append(url).append("</a>");
			String tmp_url = DataUtil.getFilterRegValue(url);
			cnt = cnt.replaceAll(tmp_url, v.toString());
			v.delete(0, v.length());
		}
		return cnt;
	}
}