package iwant.dao.impl;

import halo.dao.query.BaseDao;
import halo.util.DataUtil;
import iwant.bean.Project;
import iwant.bean.enumtype.ActiveType;
import iwant.dao.ProjectDao;
import iwant.dao.ProjectSearchCdn;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component("projectDao")
public class ProjectDaoImpl extends BaseDao<Project> implements ProjectDao {

	@Override
	public Class<Project> getClazz() {
		return Project.class;
	}

	@Override
	public List<Project> getListByCatid(int catid, ActiveType activeType,
			int begin, int size) {
		if (activeType.getValue() == ActiveType.ALL.getValue()) {
			return this
					.getList(null, null, null, "projectid desc", begin, size);
		}
		return this.getList(null, "active_flag=?",
				new Object[] { activeType.getValue() }, "projectid desc",
				begin, size);
	}

	@Override
	public List<Project> getListByDid(int did, int begin, int size) {
		return this.getList("did=?", new Object[] { did },
				"order_flg asc,projectid desc", begin, size);
	}

	@Override
	public List<Project> getListByCdn(ProjectSearchCdn projectSearchCdn,
			int begin, int size) {
		StringBuilder sb = new StringBuilder("1=1");
		List<Object> objlist = new ArrayList<Object>();
		if (projectSearchCdn != null) {
			if (DataUtil.isNotEmpty(projectSearchCdn.getName())) {
				sb.append(" and name like ?");
				objlist.add("%" + projectSearchCdn.getName() + "%");
			}
			if (projectSearchCdn.getActiveType() != null) {
				sb.append(" and active_flag=?");
				objlist.add(projectSearchCdn.getActiveType().getValue());
			}
			sb.append(" and did=?");
			objlist.add(projectSearchCdn.getDid());
		}
		return this.getList(null, sb.toString(),
				objlist.toArray(new Object[objlist.size()]),
				"order_flg asc,projectid desc", begin, size);
	}

	@Override
	public void updateOrder_flg(long projectid, int order_flg) {
		this.updateBySQL("order_flg=?", "projectid=?", new Object[] {
				order_flg, projectid });
	}
}
