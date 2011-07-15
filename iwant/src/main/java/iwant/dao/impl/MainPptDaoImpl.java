package iwant.dao.impl;

import halo.dao.query.BaseDao;
import halo.util.DataUtil;
import iwant.bean.MainPpt;
import iwant.bean.enumtype.ActiveType;
import iwant.dao.MainPptDao;
import iwant.dao.MainPptSearchCdn;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component("mainPptDao")
public class MainPptDaoImpl extends BaseDao<MainPpt> implements MainPptDao {

	@Override
	public Class<MainPpt> getClazz() {
		return MainPpt.class;
	}

	@Override
	public List<MainPpt> getListOrderedByCatidAndDid(int catid, int did,
			int begin, int size) {
		return this.getList(null, "catid=? and did=?", new Object[] { catid,
				did }, "order_flag desc", begin, size);
	}

	@Override
	public void deleteByProjectid(long projectid) {
		this.delete(null, "projectid=?", new Object[] { projectid });
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
			if (mainPptSearchCdn.getActiveType() != ActiveType.ALL) {
				sb.append(" and active_flag=?");
				objlist.add(mainPptSearchCdn.getActiveType().getValue());
			}
		}
		sb.append(" and did=?");
		objlist.add(mainPptSearchCdn.getDid());
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
			if (mainPptSearchCdn.getActiveType() != ActiveType.ALL) {
				sb.append(" and active_flag=?");
				objlist.add(mainPptSearchCdn.getActiveType().getValue());
			}
		}
		sb.append(" and did=?");
		objlist.add(mainPptSearchCdn.getDid());
		return this.count(null, sb.toString(), objlist
				.toArray(new Object[objlist.size()]));
	}

	@Override
	public MainPpt getByProjectid(long projectid) {
		return this.getObject(null, "projectid=?", new Object[] { projectid });
	}
}