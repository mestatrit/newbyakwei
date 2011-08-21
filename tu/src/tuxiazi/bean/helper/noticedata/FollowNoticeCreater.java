package tuxiazi.bean.helper.noticedata;

import java.util.Date;
import java.util.Map;

import tuxiazi.bean.Notice;
import tuxiazi.bean.benum.NoticeEnum;
import tuxiazi.bean.benum.NoticeReadEnum;

public class FollowNoticeCreater implements NoticeCreater {

	private long userid;

	private long senderid;

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

	@Override
	public Notice buildNotice() {
		Notice notice = new Notice();
		notice.setUserid(userid);
		notice.setCreatetime(new Date());
		notice.setNotice_flg(NoticeEnum.ADD_FOLLOW.getValue());
		notice.setReadflg(NoticeReadEnum.UNREAD.getValue());
		notice.setRefoid(this.userid);
		notice.setSenderid(this.senderid);
		notice.setData("");
		return notice;
	}

	@Override
	public String getIntro(Notice notice) {
		Map<String, String> map = notice.getDataMap();
		this.senderid = Long.valueOf(map.get("senderid"));
		return "正在关注您";
	}
}
