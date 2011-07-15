package iwant.dao;

import halo.dao.query.IDao;
import iwant.bean.Notice;

import java.util.List;
import java.util.Map;

public interface NoticeDao extends IDao<Notice> {

	List<Notice> getList(int begin, int size);

	Map<Long, Notice> getNoticeMapInId(List<Long> idList);
}
