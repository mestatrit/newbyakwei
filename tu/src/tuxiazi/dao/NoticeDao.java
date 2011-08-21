package tuxiazi.dao;

import halo.dao.query.IDao;
import tuxiazi.bean.Notice;
import tuxiazi.bean.benum.NoticeReadEnum;

public interface NoticeDao extends IDao<Notice> {

	public Notice getByUseridAndSenderidAndRefoidAndNotice_flg(long userid,
			long senderid, long refoid, NoticeReadEnum noticeReadEnum);

	public Notice getLastByUseridAndSenderidAndRefoid(long userid,
			long senderid, long refoid);
}
