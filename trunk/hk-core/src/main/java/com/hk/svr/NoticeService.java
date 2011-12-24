package com.hk.svr;

import java.util.List;

import com.hk.bean.Notice;

public interface NoticeService {
	void createNotice(Notice notice);

	int countNoReadNotice(long userId);

	int countNoReadNotice(long userId, byte noticeType);

	List<Notice> getNoReadNoticeList(long userId, byte noticeType, int begin,
			int size);

	List<Notice> getNoticeList(long userId, byte noticeType, int begin, int size);

	void setNoticeRead(long userId, long noticeId);

	// void deleteNotice(long userId, long objId, long obj2Id, byte noticeType);
	void deleteNotice(long objId, byte noticeType);

	Notice getLastNoReadNotice(long userId, byte noticeType);

	Notice getLastNoReadNotice(long userId);
}