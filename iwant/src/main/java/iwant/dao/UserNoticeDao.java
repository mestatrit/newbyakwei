package iwant.dao;

import halo.dao.query.IDao;
import iwant.bean.UserNotice;

import java.util.List;

public interface UserNoticeDao extends IDao<UserNotice> {

	void deleteByNoticeid(long noticeid);

	boolean isExistByUseridAndNoticeid(long userid, long noticeid);

	List<UserNotice> getList(int begin, int size);

	UserNotice getByUseridAndNoticeid(long userid, long noticeid);
}
