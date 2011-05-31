package iwant.dao.impl;

import iwant.bean.Slide;
import iwant.dao.SlideDao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.dev3g.cactus.dao.query.BaseDao;

@Component("slideDao")
public class SlideDaoImpl extends BaseDao<Slide> implements SlideDao {

	@Override
	public Class<Slide> getClazz() {
		return Slide.class;
	}

	@Override
	public List<Slide> getListByPptidOrdered(long pptid) {
		return this.getList(null, "pptid=?", new Object[] { pptid },
				"order_flag asc", 0, -1);
	}

	@Override
	public List<Slide> getListByProjectid(long projectid, int begin, int size) {
		return this.getList(null, "projectid=?", new Object[] { projectid },
				null, 0, -1);
	}

	@Override
	public int countByPptid(long pptid) {
		return this.count(null, "pptid=?", new Object[] { pptid });
	}

	@Override
	public Slide getBySlideid(long slideid) {
		return this.getById(null, slideid);
	}
}