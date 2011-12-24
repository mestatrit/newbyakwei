package com.hk.web.notice.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hk.bean.Notice;
import com.hk.web.notice.util.ChangeMayorNoticeContentCreater;
import com.hk.web.notice.util.FollowNoticeContentCreater;
import com.hk.web.notice.util.InviteNoticeCreater;
import com.hk.web.notice.util.LabaReplyNoticeContentCreater;
import com.hk.web.notice.util.NoticeContentCreater;
import com.hk.web.notice.util.UserInLabaNoticeContentCreater;

public class NoticeVo {

	private static Map<Byte, NoticeContentCreater> map = new HashMap<Byte, NoticeContentCreater>();
	static {
		map.put(Notice.NOTICETYPE_CREATEMSG, null);
		map.put(Notice.NOTICETYPE_FOLLOW, new FollowNoticeContentCreater());
		map.put(Notice.NOTICETYPE_INVITE, new InviteNoticeCreater());
		map.put(Notice.NOTICETYPE_LABAREPLY,
				new LabaReplyNoticeContentCreater());
		map.put(Notice.NOTICETYPE_USER_IN_LABA,
				new UserInLabaNoticeContentCreater());
		map.put(Notice.NOTICETYPE_CHANGEMAYOR,
				new ChangeMayorNoticeContentCreater());
	}

	public NoticeVo(HttpServletRequest request, Notice notice) {
		this.notice = notice;
		NoticeContentCreater noticeContentCreater = map.get(notice
				.getNoticeType());
		if (noticeContentCreater != null) {
			this.content = noticeContentCreater.execute(request, notice);
		}
	}

	private Notice notice;

	private String content;

	public Notice getNotice() {
		return notice;
	}

	public void setNotice(Notice notice) {
		this.notice = notice;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public static List<NoticeVo> createList(List<Notice> list,
			HttpServletRequest request) {
		List<NoticeVo> volist = new ArrayList<NoticeVo>();
		for (Notice notice : list) {
			volist.add(new NoticeVo(request, notice));
		}
		return volist;
	}
}