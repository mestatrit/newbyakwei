package iwant.dao.impl;

import iwant.bean.Notice;
import iwant.dao.NoticeDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.dev3g.cactus.dao.query.BaseDao;

@Component("noticeDao")
public class NoticeDaoImpl extends BaseDao<Notice> implements NoticeDao {

	@Override
	public Class<Notice> getClazz() {
		return Notice.class;
	}

	@Override
	public List<Notice> getList(int begin, int size) {
		return this.getList(null, null, null, null, begin, size);
	}

	@Override
	public Map<Long, Notice> getNoticeMapInId(List<Long> idList) {
		List<Notice> list = getListInId(idList);
		Map<Long, Notice> map = new HashMap<Long, Notice>();
		for (Notice o : list) {
			map.put(o.getNoticeid(), o);
		}
		return map;
	}

	public List<Notice> getListInId(List<Long> idList) {
		return this.getListInField(null, "noticeid", idList);
	}
}