package tuxiazi.dao.impl;

import halo.dao.query.BaseDao;
import halo.util.NumberUtil;

import java.util.List;

import org.springframework.stereotype.Component;

import tuxiazi.bean.Notice;
import tuxiazi.bean.benum.NoticeEnum;
import tuxiazi.bean.benum.NoticeReadEnum;
import tuxiazi.dao.NoticeDao;

@Component("noticeDao")
public class NoticeDaoImpl extends BaseDao<Notice> implements NoticeDao {

	@Override
	public Class<Notice> getClazz() {
		return Notice.class;
	}

	@Override
	public Object save(Notice t) {
		long id = NumberUtil.getLong(super.save(t));
		t.setNoticeid(id);
		return id;
	}

	public Notice getByUseridAndSenderidAndRefoidAndNotice_flg(long userid,
			long senderid, long refoid, NoticeReadEnum noticeReadEnum) {
		return this.getObject(
				"userid=? and senderid=? and refoid=? and notice_flg=?",
				new Object[] { userid, senderid, refoid,
						noticeReadEnum.getValue() });
	}

	public Notice getLastByUseridAndSenderidAndRefoid(long userid,
			long senderid, long refoid) {
		return this.getObject("userid=? and senderid=? and refoid=?",
				new Object[] { userid, senderid, refoid }, "noticeid desc");
	}

	public List<Notice> getListByUserid(long userid, int begin, int size) {
		return this.getList("userid=?", new Object[] { userid },
				"noticeid desc", begin, size);
	}

	@Override
	public int countByUseridAndNotice_flgForUnread(long userid,
			NoticeEnum noticeEnum) {
		return this.count("userid=? and notice_flg=? and readflg=?",
				new Object[] { userid, noticeEnum.getValue(),
						NoticeReadEnum.UNREAD.getValue() });
	}

	@Override
	public int countByUseridForUnread(long userid) {
		return this.count("userid=? and readflg=?", new Object[] { userid,
				NoticeReadEnum.UNREAD.getValue() });
	}

	@Override
	public List<Notice> getListByUseridAndReadflg(long userid,
			NoticeReadEnum noticeReadEnum, int begin, int size) {
		return this.getList("userid=? and readflg=?", new Object[] { userid,
				noticeReadEnum.getValue() }, "noticeid desc", begin, size);
	}
}
