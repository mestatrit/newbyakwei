package iwant.dao;

import halo.dao.query.IDao;
import iwant.bean.NoticeQueue;

import java.util.List;

public interface NoticeQueueDao extends IDao<NoticeQueue> {

	List<NoticeQueue> getList(int begin, int size);
}