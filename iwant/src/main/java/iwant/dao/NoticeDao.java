package iwant.dao;

import iwant.bean.Notice;

import java.util.List;
import java.util.Map;

import com.dev3g.cactus.dao.query.IDao;

public interface NoticeDao extends IDao<Notice> {

	List<Notice> getList(int begin, int size);

	Map<Long, Notice> getNoticeMapInId(List<Long> idList);
}
