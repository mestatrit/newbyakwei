package iwant.svr.impl;

import iwant.bean.Notice;
import iwant.bean.NoticeidCreator;
import iwant.bean.UserNotice;
import iwant.dao.NoticeDao;
import iwant.dao.NoticeidCreatorDao;
import iwant.dao.UserNoticeDao;
import iwant.svr.NoticeSvr;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.frame.util.NumberUtil;

public class NoticeSvrImpl implements NoticeSvr {

	@Autowired
	private NoticeidCreatorDao noticeidCreatorDao;

	@Autowired
	private NoticeDao noticeDao;

	@Autowired
	private UserNoticeDao userNoticeDao;

	private String p12FilePath;

	public void setP12FilePath(String p12FilePath) {
		this.p12FilePath = p12FilePath;
	}

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

	@Override
	public boolean sendApnsNotice(String content, long userid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<UserNotice> getUserNoticeList(boolean buildNotice, int begin,
			int size) {
		List<UserNotice> list = this.userNoticeDao.getList(begin, size);
		if (buildNotice) {
			List<Long> idList = new ArrayList<Long>();
			for (UserNotice o : list) {
				idList.add(o.getNoticeid());
			}
			Map<Long, Notice> map = this.noticeDao.getNoticeMapInId(idList);
			for (UserNotice o : list) {
				o.setNotice(map.get(o.getNoticeid()));
			}
		}
		return list;
	}

	@Override
	public void deleteUserNotice(UserNotice userNotice) {
		this.userNoticeDao.delete(userNotice);
	}

	@Override
	public boolean sendApnsNotice(String deviceToken, String content) {
		// TODO Auto-generated method stub
		return false;
	}
}