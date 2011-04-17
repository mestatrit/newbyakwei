package iwant.dao;

import iwant.bean.NoticeQueue;

import java.util.List;

import com.hk.frame.dao.query2.IDao;

public interface NoticeQueueDao extends IDao<NoticeQueue> {

	List<NoticeQueue> getList(int begin, int size);
}