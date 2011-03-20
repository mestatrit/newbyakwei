package tuxiazi.bean.helper.noticedata;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import tuxiazi.bean.Notice;
import tuxiazi.bean.benum.NoticeEnum;
import tuxiazi.bean.benum.NoticeReadEnum;

import com.hk.frame.util.JsonUtil;

public class PhotoCmtNoticeCreater implements NoticeCreater {

	private long userid;

	private long senderid;

	private String sender_nick;

	private String sender_head;

	private long cmtid;

	private long photoid;

	private String content;

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

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

	public void setCmtid(long cmtid) {
		this.cmtid = cmtid;
	}

	public void setPhotoid(long photoid) {
		this.photoid = photoid;
	}

	@Override
	public Notice buildNotice() {
		Notice notice = new Notice();
		notice.setUserid(this.userid);
		notice.setCreatetime(new Date());
		notice.setNotice_flg(NoticeEnum.ADD_PHOTOCMT.getValue());
		notice.setReadflg(NoticeReadEnum.UNREAD.getValue());
		Map<String, String> map = new HashMap<String, String>();
		map.put("senderid", String.valueOf(this.senderid));
		map.put("nick", this.sender_nick);
		map.put("head", this.sender_head);
		map.put("cmtid", String.valueOf(this.cmtid));
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
		this.cmtid = Long.valueOf(map.get("cmtid"));
		return "评论了您的图片";
	}
}