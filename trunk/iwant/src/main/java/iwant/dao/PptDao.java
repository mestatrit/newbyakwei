package iwant.dao;

import iwant.bean.Ppt;

import java.util.List;
import java.util.Map;

import com.hk.frame.dao.query2.IDao;

public interface PptDao extends IDao<Ppt> {

	Map<Long, Ppt> getPptMapInId(List<Long> idList);

	void deleteByProjectid(long projectid);

	List<Ppt> getListByProjectid(long projectid, int begin, int size);
}
