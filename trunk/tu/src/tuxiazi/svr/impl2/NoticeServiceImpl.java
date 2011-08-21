package tuxiazi.svr.impl2;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import tuxiazi.bean.Notice;
import tuxiazi.dao.NoticeDao;
import tuxiazi.svr.iface.NoticeService;

public class NoticeServiceImpl implements NoticeService {

	@Autowired
	private NoticeDao noticeDao;

	@Override
	public void createNotice(Notice notice) {
		if (notice.getCreatetime() == null) {
			notice.setCreatetime(new Date());
		}
		this.noticeDao.save(notice);
	}

	@Override
	public void deleteNotice(Notice notice) {
		this.noticeDao.deleteById(notice.getNoticeid());
	}

	@Override
	public void setNoticeReaded(long noticeid) {
		this.noticeDao.updateBySQL("readflg=?", "noticeid=?",
				new Object[] { noticeid });
	}
}