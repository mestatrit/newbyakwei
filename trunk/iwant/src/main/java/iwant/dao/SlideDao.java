package iwant.dao;

import halo.dao.query.IDao;
import iwant.bean.Slide;

import java.util.List;

public interface SlideDao extends IDao<Slide> {

	List<Slide> getListByProjectid(long projectid, int begin, int size);

	int countByProjectid(long projectid);

	Slide getBySlideid(long slideid);
}