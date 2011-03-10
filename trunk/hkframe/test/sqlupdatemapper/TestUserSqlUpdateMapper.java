package sqlupdatemapper;

import java.util.ArrayList;
import java.util.List;

import com.hk.frame.dao.query2.SqlUpdateMqpper;

public class TestUserSqlUpdateMapper implements SqlUpdateMqpper<TestUser> {

	@Override
	public Object getIdParam(TestUser t) {
		return t.getNick();
	}

	@Override
	public Object[] getParamsForInsert(TestUser t) {
		List<Object> list = new ArrayList<Object>();
		list.add(t.getUserid());
		list.add(t.getNick());
		list.add(t.getCreatetime());
		return list.toArray(new Object[list.size()]);
	}

	@Override
	public Object[] getParamsForUpdate(TestUser t) {
		List<Object> list = new ArrayList<Object>();
		list.add(t.getNick());
		list.add(t.getCreatetime());
		list.add(t.getUserid());
		return list.toArray(new Object[list.size()]);
	}
}
