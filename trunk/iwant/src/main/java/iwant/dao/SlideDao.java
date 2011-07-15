package iwant.dao;

import halo.dao.query.IDao;
import iwant.bean.Slide;

import java.util.List;

public interface SlideDao extends IDao<Slide> {

	List<Slide> getListByPptidOrdered(long pptid);

	List<Slide> getListByProjectid(long projectid, int begin, int size);

	int countByPptid(long pptid);

	Slide getBySlideid(long slideid);
}