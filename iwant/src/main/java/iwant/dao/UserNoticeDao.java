package iwant.dao;

import iwant.bean.UserNotice;

import java.util.List;

import cactus.dao.query.IDao;

public interface UserNoticeDao extends IDao<UserNotice> {

	void deleteByNoticeid(long noticeid);

	boolean isExistByUseridAndNoticeid(long userid, long noticeid);

	List<UserNotice> getList(int begin, int size);

	UserNotice getByUseridAndNoticeid(long userid, long noticeid);
}
