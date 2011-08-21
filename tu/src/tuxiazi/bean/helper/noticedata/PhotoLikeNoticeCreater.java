package tuxiazi.bean.helper.noticedata;

import java.util.Date;
import java.util.Map;

import tuxiazi.bean.Notice;
import tuxiazi.bean.benum.NoticeEnum;
import tuxiazi.bean.benum.NoticeReadEnum;

public class PhotoLikeNoticeCreater implements NoticeCreater {

	private long userid;

	private long senderid;

	private long photoid;

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public void setSenderid(long senderid) {
		this.senderid = senderid;
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
		notice.setRefoid(this.photoid);
		notice.setSenderid(this.senderid);
		notice.setData("");
		return notice;
	}

	@Override
	public String getIntro(Notice notice) {
		Map<String, String> map = notice.getDataMap();
		this.senderid = Long.valueOf(map.get("senderid"));
		this.photoid = Long.valueOf(map.get("photoid"));
		return "喜欢您的图片";
	}
}