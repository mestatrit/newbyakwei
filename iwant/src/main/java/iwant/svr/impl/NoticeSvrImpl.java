package iwant.svr.impl;

import iwant.bean.Notice;
import iwant.bean.NoticeidCreator;
import iwant.bean.UserNotice;
import iwant.dao.NoticeDao;
import iwant.dao.NoticeidCreatorDao;
import iwant.dao.UserNoticeDao;
import iwant.svr.NoticeSvr;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.frame.util.NumberUtil;

public class NoticeSvrImpl implements NoticeSvr {

	@Autowired
	private NoticeidCreatorDao noticeidCreatorDao;

	@Autowired
	private NoticeDao noticeDao;

	@Autowired
	private UserNoticeDao userNoticeDao;

	@Override
	public void createNotice(Notice notice) {
		long noticeid = NumberUtil.getLong(this.noticeidCreatorDao
				.save(new NoticeidCreator()));
		notice.setNoticeid(noticeid);
		this.noticeDao.save(notice);
	}

	@Override
	public void deleteNotice(long noticeid) {
		this.noticeDao.deleteById(null, noticeid);
		this.userNoticeDao.deleteByNoticeid(noticeid);
	}

	@Override
	public Notice getNotice(long noticeid) {
		return this.noticeDao.getById(null, noticeid);
	}

	@Override
	public List<Notice> getNoticeList(int begin, int size) {
		return this.noticeDao.getList(begin, size);
	}

	@Override
	public boolean sendAPNSNotice(String content, long userid) {
		// TODO 发送apple通知
		return false;
	}

	@Override
	public UserNotice createUserNotice(long noticeid, long userid) {
		if (this.userNoticeDao.isExistByUseridAndNoticeid(userid, noticeid)) {
			return null;
		}
		UserNotice userNotice = new UserNotice();
		userNotice.setUserid(userid);
		userNotice.setNoticeid(noticeid);
		long sysid = NumberUtil.getLong(this.userNoticeDao.save(userNotice));
		userNotice.setSysid(sysid);
		return userNotice;
	}
}