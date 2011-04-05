package tuxiazi.svr.impl2;

import java.util.Date;
import java.util.List;

import tuxiazi.bean.Notice;
import tuxiazi.bean.benum.NoticeEnum;
import tuxiazi.bean.benum.NoticeReadEnum;
import tuxiazi.dao.NoticeDao;
import tuxiazi.svr.iface.NoticeService;

public class NoticeServiceImpl implements NoticeService {

	private NoticeDao noticeDao;

	public void setNoticeDao(NoticeDao noticeDao) {
		this.noticeDao = noticeDao;
	}

	@Override
	public void createNotice(Notice notice) {
		if (notice.getCreatetime() == null) {
			notice.setCreatetime(new Date());
		}
		if (notice.getNotice_flg() == NoticeEnum.ADD_PHOTOLIKE.getValue()) {
			Notice notice2 = this.noticeDao.getObject(null,
					"userid=? and notice_flg=? and refoid=?", new Object[] {
							notice.getUserid(), notice.getNotice_flg(),
							notice.getRefoid() }, "noticeid desc");
			if (notice2 != null) {
				notice.setReadflg(notice2.getReadflg());
				this.deleteNotice(notice2);
			}
		}
		this.noticeDao.save(null, notice);
	}

	@Override
	public void deleteNotice(Notice notice) {
		this.noticeDao.delete(null, notice);
	}

	@Override
	public Notice getNotice(long noticeid) {
		return this.noticeDao.getById(null, noticeid);
	}

	@Override
	public List<Notice> getNoticeListByUserid(long userid, int begin, int size) {
		return this.noticeDao.getList(null, "userid=?",
				new Object[] { userid }, "noticeid desc", begin, size);
	}

	@Override
	public int countNoticeByUseridAndNotice_flgForUnread(long userid,
			NoticeEnum noticeEnum) {
		return this.noticeDao.count(null,
				"userid=? and notice_flg=? and readflg=?", new Object[] {
						userid, noticeEnum.getValue(),
						NoticeReadEnum.UNREAD.getValue() });
	}

	@Override
	public int countNoticeByUseridForUnread(long userid) {
		return this.noticeDao.count(null, "userid=? and readflg=?",
				new Object[] { userid, NoticeReadEnum.UNREAD.getValue() });
	}

	@Override
	public List<Notice> getNoticeListByUseridAndReadflg(long userid,
			NoticeReadEnum noticeReadEnum, int begin, int size) {
		return this.noticeDao.getList(null, "userid=? and readflg=?",
				new Object[] { userid, noticeReadEnum.getValue() },
				"noticeid desc", begin, size);
	}

	@Override
	public void setNoticeReaded(long noticeid) {
		this.noticeDao.updateBySQL(null, "readflg=?", "noticeid=?",
				new Object[] { noticeid });
	}
}