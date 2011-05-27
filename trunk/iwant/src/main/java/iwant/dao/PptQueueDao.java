package iwant.dao;

import iwant.bean.PptQueue;

import java.util.List;

import com.dev3g.cactus.dao.query.IDao;

public interface PptQueueDao extends IDao<PptQueue> {

	List<PptQueue> getList(int begin, int size);
}