package tuxiazi.bean.helper.noticedata;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import tuxiazi.bean.Notice;
import tuxiazi.bean.benum.NoticeEnum;
import tuxiazi.bean.benum.NoticeReadEnum;

import com.hk.frame.util.JsonUtil;

public class FollowNoticeCreater implements NoticeCreater {

	private long userid;

	private long senderid;

	private String sender_nick;

	private String sender_head;

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public long getSenderid() {
		return senderid;
	}

	public void setSenderid(long senderid) {
		this.senderid = senderid;
	}

	public String getSender_nick() {
		return sender_nick;
	}

	public void setSender_nick(String senderNick) {
		sender_nick = senderNick;
	}

	public String getSender_head() {
		return sender_head;
	}

	public void setSender_head(String senderHead) {
		sender_head = senderHead;
	}

	@Override
	public Notice buildNotice() {
		Notice notice = new Notice();
		notice.setUserid(userid);
		notice.setCreatetime(new Date());
		notice.setNotice_flg(NoticeEnum.ADD_FOLLOW.getValue());
		notice.setReadflg(NoticeReadEnum.UNREAD.getValue());
		notice.setRefoid(this.userid);
		Map<String, String> map = new HashMap<String, String>();
		map.put("nick", this.sender_nick);
		map.put("head", this.sender_head);
		map.put("senderid", String.valueOf(this.senderid));
		notice.setData(JsonUtil.toJson(map));
		return notice;
	}

	@Override
	public String getIntro(Notice notice) {
		Map<String, String> map = notice.getDataMap();
		this.senderid = Long.valueOf(map.get("senderid"));
		this.sender_nick = map.get("nick");
		this.sender_head = map.get("head");
		return "正在关注您";
	}
}
