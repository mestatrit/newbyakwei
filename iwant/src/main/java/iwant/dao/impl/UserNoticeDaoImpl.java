package iwant.dao.impl;

import iwant.bean.UserNotice;
import iwant.dao.UserNoticeDao;

import com.hk.frame.dao.query2.BaseDao;

public class UserNoticeDaoImpl extends BaseDao<UserNotice> implements
		UserNoticeDao {

	@Override
	public Class<UserNotice> getClazz() {
		return UserNotice.class;
	}

	@Override
	public void deleteByNoticeid(long noticeid) {
		this.delete(null, "noticeid=?", new Object[] { noticeid });
	}

	@Override
	public boolean isExistByUseridAndNoticeid(long userid, long noticeid) {
		if (this.count(null, "userid=? and noticeid=?", new Object[] { userid,
				noticeid }) > 0) {
			return true;
		}
		return false;
	}
}