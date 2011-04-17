package iwant.dao;

import iwant.bean.PptQueue;

import java.util.List;

import com.hk.frame.dao.query2.IDao;

public interface PptQueueDao extends IDao<PptQueue> {

	List<PptQueue> getList(int begin, int size);
}