package iwant.dao.impl;

import iwant.bean.Ppt;
import iwant.dao.PptDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hk.frame.dao.query2.BaseDao;

public class PptDaoImpl extends BaseDao<Ppt> implements PptDao {

	@Override
	public Class<Ppt> getClazz() {
		return Ppt.class;
	}

	@Override
	public Map<Long, Ppt> getPptMapInId(List<Long> idList) {
		List<Ppt> list = this.getListInField(null, "pptid", idList);
		Map<Long, Ppt> map = new HashMap<Long, Ppt>();
		for (Ppt o : list) {
			map.put(o.getPptid(), o);
		}
		return map;
	}

	@Override
	public void deleteByProjectid(long projectid) {
		this.delete(null, "projectid=?", new Object[] { projectid });
	}

	@Override
	public List<Ppt> getListByProjectid(long projectid, int begin, int size) {
		return this.getList(null, "projectid=?", new Object[] { projectid },
				"pptid desc", begin, size);
	}
}