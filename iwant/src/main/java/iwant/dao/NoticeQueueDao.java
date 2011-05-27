package iwant.dao;

import iwant.bean.NoticeQueue;

import java.util.List;

import com.dev3g.cactus.dao.query.IDao;

public interface NoticeQueueDao extends IDao<NoticeQueue> {

	List<NoticeQueue> getList(int begin, int size);
}