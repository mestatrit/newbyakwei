package tuxiazi.dao.impl;

import halo.dao.query.BaseDao;
import tuxiazi.bean.Notice;
import tuxiazi.bean.benum.NoticeReadEnum;
import tuxiazi.dao.NoticeDao;

public class NoticeDaoImpl extends BaseDao<Notice> implements NoticeDao {

	@Override
	public Class<Notice> getClazz() {
		return Notice.class;
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
}
