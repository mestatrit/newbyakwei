package iwant.dao.impl;

import iwant.bean.MainPpt;
import iwant.bean.enumtype.ActiveType;
import iwant.dao.MainPptDao;

import java.util.List;

import com.hk.frame.dao.query2.BaseDao;

public class MainPptDaoImpl extends BaseDao<MainPpt> implements MainPptDao {

	@Override
	public Class<MainPpt> getClazz() {
		return MainPpt.class;
	}

	@Override
	public List<MainPpt> getListOrderedByCatid(int catid,
			ActiveType activeType, int begin, int size) {
		if (activeType.getValue() == ActiveType.ALL.getValue()) {
			return this.getList(null, null, null, "order_flag desc", begin,
					size);
		}
		return this.getList(null, "active_flag=?", new Object[] { activeType
				.getValue() }, "order_flag desc", begin, size);
	}
}