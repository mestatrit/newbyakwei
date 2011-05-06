package demo.svr.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cactus.dao.query.HkObjQuery;
import cactus.dao.query.QueryParam;
import demo.bean.UserInfo;

public class UserInfoDao extends BaseDao<UserInfo> {

	@Autowired
	private HkObjQuery hkObjQuery;

	private final String PKEY = "userid";

	@Override
	public String getKey() {
		return PKEY;
	}

	@Override
	public Class<UserInfo> getClazz() {
		return UserInfo.class;
	}

	public void create(UserInfo userInfo) {
		this.hkObjQuery.insertObj(this.createBaseParam(userInfo.getUserid()),
				userInfo);
	}

	public void update(UserInfo userInfo) {
		this.hkObjQuery.updateObj(this.createBaseParam(userInfo.getUserid()),
				userInfo);
	}

	public void delete(UserInfo userInfo) {
		this.hkObjQuery.deleteObj(this.createBaseParam(userInfo.getUserid()),
				userInfo);
	}

	public UserInfo getById(long userid) {
		return this.hkObjQuery.getObjectById(this.createQueryParam(userid),
				UserInfo.class, userid);
	}

	public List<UserInfo> getList(Object keyValue, int begin, int size) {
		QueryParam queryParam = this.createQueryParam(keyValue);
		queryParam.setRange(begin, size);
		queryParam.setWhereAndParams("nick=?", new Object[] { "akwei" });
		queryParam.setOrder("gender desc");
		return this.hkObjQuery.getList(queryParam, UserInfo.class);
	}
}