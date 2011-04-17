package iwant.dao.impl;

import iwant.bean.MainPpt;
import iwant.dao.MainPptDao;
import iwant.dao.MainPptSearchCdn;

import java.util.ArrayList;
import java.util.List;

import com.hk.frame.dao.query2.BaseDao;
import com.hk.frame.util.DataUtil;

public class MainPptDaoImpl extends BaseDao<MainPpt> implements MainPptDao {

	@Override
	public Class<MainPpt> getClazz() {
		return MainPpt.class;
	}

	@Override
	public List<MainPpt> getListOrderedByCatid(int catid, int begin, int size) {
		return this.getList(null, "catid=?", new Object[] { catid },
				"order_flag desc", begin, size);
	}

	@Override
	public List<MainPpt> getListOrderedByCdn(MainPptSearchCdn mainPptSearchCdn,
			int begin, int size) {
		StringBuilder sb = new StringBuilder("1=1");
		List<Object> objlist = new ArrayList<Object>();
		if (mainPptSearchCdn.getCatid() > 0) {
			sb.append(" and catid=?");
			objlist.add(mainPptSearchCdn.getCatid());
		}
		if (DataUtil.isNotEmpty(mainPptSearchCdn.getName())) {
			sb.append(" and name like ?");
			objlist.add("%" + mainPptSearchCdn.getName() + "%");
		}
		if (mainPptSearchCdn.getActiveType() != null) {
			sb.append(" and active_flag=?");
			objlist.add(mainPptSearchCdn.getActiveType().getValue());
		}
		return this.getList(null, sb.toString(), objlist
				.toArray(new Object[objlist.size()]), mainPptSearchCdn
				.getOrder(), begin, size);
	}

	@Override
	public int countByCdn(MainPptSearchCdn mainPptSearchCdn) {
		StringBuilder sb = new StringBuilder("1=1");
		List<Object> objlist = new ArrayList<Object>();
		if (mainPptSearchCdn.getCatid() > 0) {
			sb.append(" and catid=?");
			objlist.add(mainPptSearchCdn.getCatid());
		}
		if (DataUtil.isNotEmpty(mainPptSearchCdn.getName())) {
			sb.append(" and name like ?");
			objlist.add("%" + mainPptSearchCdn.getName() + "%");
		}
		if (mainPptSearchCdn.getActiveType() != null) {
			sb.append(" and active_flag=?");
			objlist.add(mainPptSearchCdn.getActiveType().getValue());
		}
		return this.count(null, sb.toString(), objlist
				.toArray(new Object[objlist.size()]));
	}
}