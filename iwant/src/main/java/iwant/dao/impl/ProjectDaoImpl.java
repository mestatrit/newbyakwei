package iwant.dao.impl;

import iwant.bean.Project;
import iwant.bean.enumtype.ActiveType;
import iwant.dao.ProjectDao;
import iwant.dao.ProjectSearchCdn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dev3g.cactus.dao.query.BaseDao;
import com.dev3g.cactus.util.DataUtil;

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
		return this.getList(null, "active_flag=?", new Object[] { activeType
				.getValue() }, "projectid desc", begin, size);
	}

	@Override
	public List<Project> getListByCdn(ProjectSearchCdn projectSearchCdn,
			int begin, int size) {
		StringBuilder sb = new StringBuilder("1=1");
		List<Object> objlist = new ArrayList<Object>();
		if (projectSearchCdn != null) {
			if (projectSearchCdn.getCatid() > 0) {
				sb.append(" and catid=?");
				objlist.add(projectSearchCdn.getCatid());
			}
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
		return this.getList(null, sb.toString(), objlist
				.toArray(new Object[objlist.size()]), "projectid desc", begin,
				size);
	}

	@Override
	public Map<Long, Project> getMapInId(List<Long> idList) {
		List<Project> list = this.getListInField(null, "projectid", idList);
		Map<Long, Project> map = new HashMap<Long, Project>();
		for (Project o : list) {
			map.put(o.getProjectid(), o);
		}
		return map;
	}
}
