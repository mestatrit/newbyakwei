package com.hk.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.svr.pub.Err;

@Table(name = "usersms", id = "msgId")
public class UserSms {
	public static final byte SMSFLG_SINGLE = 0;

	public static final byte SMSFLG_GROUP = 1;

	private long msgId;

	private long senderId;

	private String content;

	private String port;

	private Date createTime;

	private long receiverId;

	private byte smsstate;

	private String statemsg;

	private byte smsflg;

	private List<Long> receiverIdList;

	public void addReceiverId(long receiverId) {
		if (receiverIdList == null) {
			this.receiverIdList = new ArrayList<Long>();
		}
		this.receiverIdList.add(receiverId);
	}

	public List<Long> getReceiverIdList() {
		return receiverIdList;
	}

	public void setReceiverIdList(List<Long> receiverIdList) {
		this.receiverIdList = receiverIdList;
	}

	public long getSenderId() {
		return senderId;
	}

	public void setSenderId(long senderId) {
		this.senderId = senderId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public long getMsgId() {
		return msgId;
	}

	public void setMsgId(long msgId) {
		this.msgId = msgId;
	}

	public byte getSmsstate() {
		return smsstate;
	}

	public void setSmsstate(byte smsstate) {
		this.smsstate = smsstate;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int validate() {
		if (this.senderId == 0) {
			return Err.USERID_ERROR;
		}
		if (this.receiverIdList == null || this.receiverIdList.size() == 0) {
			return Err.USERID_ERROR;
		}
		for (Long l : this.receiverIdList) {
			if (l.longValue() == 0) {
				return Err.USERID_ERROR;
			}
		}
		String s = DataUtil.toTextRow(this.content);
		if (DataUtil.isEmpty(s)) {
			return Err.USERSMS_CONTENT_ERROR;
		}
		if (s.length() > 70) {
			return Err.USERSMS_CONTENT_LENGTH_TOO_LONG;
		}
		return Err.SUCCESS;
	}

	public long getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(long receiverId) {
		this.receiverId = receiverId;
	}

	public String getStatemsg() {
		return statemsg;
	}

	public void setStatemsg(String statemsg) {
		this.statemsg = statemsg;
	}

	public byte getSmsflg() {
		return smsflg;
	}

	public void setSmsflg(byte smsflg) {
		this.smsflg = smsflg;
	}

	public int getSendState() {
		if (this.isSendSuccess()) {
			return 0;
		}
		if (this.isSending()) {
			return 1;
		}
		return -1;// 其余都是失败
	}

	public boolean isSendSuccess() {
		return DataUtil.isInElements(this.smsstate, new Object[] { new Byte(
				(byte) 2) });
	}

	public boolean isSending() {
		return DataUtil.isInElements(this.smsstate, new Object[] {
				new Byte((byte) -1), new Byte((byte) 3) });
	}

	public boolean isSendFail() {
		return DataUtil.isInElements(this.smsstate, new Object[] {
				new Byte((byte) 1), new Byte((byte) 4), new Byte((byte) 5),
				new Byte((byte) 6), new Byte((byte) 7), new Byte((byte) 8),
				new Byte((byte) 9) });
	}
}