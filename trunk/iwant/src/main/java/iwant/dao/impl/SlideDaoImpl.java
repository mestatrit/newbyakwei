package iwant.dao.impl;

import halo.dao.query.BaseDao;
import iwant.bean.Slide;
import iwant.dao.SlideDao;

import java.util.List;

import org.springframework.stereotype.Component;

@Component("slideDao")
public class SlideDaoImpl extends BaseDao<Slide> implements SlideDao {

	@Override
	public Class<Slide> getClazz() {
		return Slide.class;
	}

	@Override
	public List<Slide> getListByProjectid(long projectid, int begin, int size) {
		return this.getList("projectid=?", new Object[] { projectid },
				"order_flag asc", begin, size);
	}

	@Override
	public Slide getBySlideid(long slideid) {
		return this.getById(null, slideid);
	}

	@Override
	public int countByProjectid(long projectid) {
		return this.count(null, "projectid=?", new Object[] { projectid });
	}
}