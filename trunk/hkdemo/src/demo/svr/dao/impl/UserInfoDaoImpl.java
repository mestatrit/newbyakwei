package demo.svr.dao.impl;

import java.util.List;

import com.dev3g.cactus.dao.query.BaseDao;

import demo.bean.UserInfo;
import demo.svr.dao.UserInfoDao;

/**
 * insert update delete selectById 等单表操作的方法已经在IDao中定义，并在BaseDao中实现，所以可以直接用
 * 
 * @author akwei
 */
public class UserInfoDaoImpl extends BaseDao<UserInfo> implements UserInfoDao {

	@Override
	public Class<UserInfo> getClazz() {
		// 返回对应的class，用来指定当前dao操作的表
		return UserInfo.class;
	}

	@Override
	public List<UserInfo> getListByGender(int gender, int begin, int size) {
		return this.getList("gender=?", new Object[] { gender }, "nick asc",
				begin, size);
		// 如果是按照gender分表：
//		return this.getList(gender, "gender=?", new Object[] { gender },
//				"nick asc", begin, size);
	}
}