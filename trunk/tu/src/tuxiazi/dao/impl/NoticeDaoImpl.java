package tuxiazi.dao.impl;

import halo.dao.query.BaseDao;
import halo.util.NumberUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tuxiazi.bean.Notice;
import tuxiazi.bean.User;
import tuxiazi.bean.benum.NoticeEnum;
import tuxiazi.bean.benum.NoticeReadEnum;
import tuxiazi.dao.NoticeDao;
import tuxiazi.dao.UserDao;

@Component("noticeDao")
public class NoticeDaoImpl extends BaseDao<Notice> implements NoticeDao {

	@Autowired
	private UserDao userDao;

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

	public List<Notice> getListByUserid(long userid, boolean buildSender,
			int begin, int size) {
		List<Notice> list = this.getList("userid=?", new Object[] { userid },
				"noticeid desc", begin, size);
		if (buildSender) {
			this.buildSender(list);
		}
		return list;
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
			NoticeReadEnum noticeReadEnum, boolean buildSender, int begin,
			int size) {
		List<Notice> list = this.getList("userid=? and readflg=?",
				new Object[] { userid, noticeReadEnum.getValue() },
				"noticeid desc", begin, size);
		if (buildSender) {
			this.buildSender(list);
		}
		return list;
	}

	@Override
	public int updateReaded(long noticeid, int read_flg) {
		return this.updateBySQL("readflg=?", "noticeid=?", new Object[] {
				read_flg, noticeid });
	}

	private void buildSender(List<Notice> list) {
		List<Long> idList = new ArrayList<Long>();
		for (Notice o : list) {
			idList.add(o.getSenderid());
		}
		Map<Long, User> map = this.userDao.getMapInId(idList);
		for (Notice o : list) {
			o.setSender(map.get(o.getSenderid()));
		}
	}
}
