package iwant.dao;

import halo.dao.query.IDao;
import iwant.bean.PptQueue;

import java.util.List;

public interface PptQueueDao extends IDao<PptQueue> {

	List<PptQueue> getList(int begin, int size);
}