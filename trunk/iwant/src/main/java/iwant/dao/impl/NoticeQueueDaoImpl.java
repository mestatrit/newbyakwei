package iwant.dao.impl;

import iwant.bean.NoticeQueue;
import iwant.dao.NoticeQueueDao;

import java.util.List;

import com.dev3g.cactus.dao.query.BaseDao;

public class NoticeQueueDaoImpl extends BaseDao<NoticeQueue> implements
		NoticeQueueDao {

	@Override
	public Class<NoticeQueue> getClazz() {
		return NoticeQueue.class;
	}

	@Override
	public List<NoticeQueue> getList(int begin, int size) {
		return this.getList(null, null, null, "noticeid asc", begin, size);
	}
}