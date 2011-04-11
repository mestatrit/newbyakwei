package iwant.dao;

import iwant.bean.Slide;

import java.util.List;

import com.hk.frame.dao.query2.IDao;

public interface SlideDao extends IDao<Slide> {

	List<Slide> getListByPptidOrdered(long pptid);

	List<Slide> getListByProjectid(long projectid, int begin, int size);

	int countByPptid(long pptid);

	Slide getByPptidAndSlideid(long pptid, long slideid);
}