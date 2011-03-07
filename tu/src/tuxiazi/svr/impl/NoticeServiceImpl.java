package tuxiazi.svr.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import tuxiazi.bean.Notice;
import tuxiazi.bean.benum.NoticeEnum;
import tuxiazi.bean.benum.NoticeReadEnum;
import tuxiazi.svr.iface.NoticeService;

import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;

public class NoticeServiceImpl implements NoticeService {

	@Autowired
	private QueryManager manager;

	@Override
	public void createNotice(Notice notice) {
		if (notice.getCreatetime() == null) {
			notice.setCreatetime(new Date());
		}
		Query query = this.manager.createQuery();
		Notice notice2 = query.getObject(Notice.class,
				"userid=? and notice_flg=?", new Object[] { notice.getUserid(),
						notice.getNotice_flg() }, "noticeid desc");
		if (notice2 != null) {
			notice.setReadflg(notice2.getReadflg());
			query.deleteObject(notice2);
		}
		query.insertObject(notice);
	}

	@Override
	public void deleteNotice(Notice notice) {
		this.manager.createQuery().deleteObject(notice);
	}

	@Override
	public Notice getNotice(long noticeid) {
		return this.manager.createQuery().getObjectById(Notice.class, noticeid);
	}

	@Override
	public List<Notice> getNoticeListByUserid(long userid, int begin, int size) {
		return this.manager.createQuery().listEx(Notice.class, "userid=?",
				new Object[] { userid }, "noticeid desc", begin, size);
	}

	@Override
	public int countNoticeByUseridAndNotice_flgForUnread(long userid,
			NoticeEnum noticeEnum) {
		return this.manager.createQuery().count(
				Notice.class,
				"userid=? and notice_flg=? and readflg=?",
				new Object[] { userid, noticeEnum.getValue(),
						NoticeReadEnum.UNREAD.getValue() });
	}

	@Override
	public int countNoticeByUseridForUnread(long userid) {
		return this.manager.createQuery().count(Notice.class,
				"userid=? and readflg=?",
				new Object[] { userid, NoticeReadEnum.UNREAD.getValue() });
	}

	@Override
	public List<Notice> getNoticeListByUseridAndReadflg(long userid,
			NoticeReadEnum noticeReadEnum, int begin, int size) {
		return this.manager.createQuery().listEx(Notice.class,
				"userid=? and readflg=?",
				new Object[] { userid, noticeReadEnum.getValue() },
				"noticeid desc", begin, size);
	}

	@Override
	public void setNoticeReaded(long noticeid) {
		Query query = this.manager.createQuery();
		query.addField("readflg", NoticeReadEnum.READED.getValue());
		query.updateById(Notice.class, noticeid);
	}
}