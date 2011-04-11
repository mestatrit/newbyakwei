package iwant.dao;

import iwant.bean.UserNotice;

import com.hk.frame.dao.query2.IDao;

public interface UserNoticeDao extends IDao<UserNotice> {

	void deleteByNoticeid(long noticeid);

	boolean isExistByUseridAndNoticeid(long userid, long noticeid);
}
