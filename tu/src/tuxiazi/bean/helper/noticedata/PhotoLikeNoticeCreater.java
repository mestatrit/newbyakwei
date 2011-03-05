package tuxiazi.bean.helper.noticedata;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.hk.frame.util.JsonUtil;

import tuxiazi.bean.Notice;
import tuxiazi.bean.benum.NoticeEnum;
import tuxiazi.bean.benum.NoticeReadEnum;

public class PhotoLikeNoticeCreater implements NoticeCreater {

	private long userid;

	private long senderid;

	private String sender_nick;

	private String sender_head;

	private long photoid;

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public void setSenderid(long senderid) {
		this.senderid = senderid;
	}

	public void setSender_nick(String senderNick) {
		sender_nick = senderNick;
	}

	public void setSender_head(String senderHead) {
		sender_head = senderHead;
	}

	public void setPhotoid(long photoid) {
		this.photoid = photoid;
	}

	@Override
	public Notice buildNotice() {
		Notice notice = new Notice();
		notice.setUserid(this.userid);
		notice.setCreatetime(new Date());
		notice.setNotice_flg(NoticeEnum.ADD_PHOTOLIKE.getValue());
		notice.setReadflg(NoticeReadEnum.UNREAD.getValue());
		Map<String, String> map = new HashMap<String, String>();
		map.put("senderid", String.valueOf(this.senderid));
		map.put("nick", this.sender_nick);
		map.put("head", this.sender_head);
		map.put("photoid", String.valueOf(this.photoid));
		notice.setData(JsonUtil.toJson(map));
		return notice;
	}

	@Override
	public String getIntro(Notice notice) {
		Map<String, String> map = notice.getDataMap();
		this.senderid = Long.valueOf(map.get("senderid"));
		this.sender_nick = map.get("nick");
		this.sender_head = map.get("head");
		this.photoid = Long.valueOf(map.get("photoid"));
		return "喜欢您的图片";
	}
}