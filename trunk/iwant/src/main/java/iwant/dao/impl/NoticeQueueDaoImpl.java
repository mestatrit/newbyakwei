package iwant.dao.impl;

import halo.dao.query.BaseDao;
import iwant.bean.NoticeQueue;
import iwant.dao.NoticeQueueDao;

import java.util.List;

import org.springframework.stereotype.Component;

@Component("noticeQueueDao")
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