package tuxiazi.dao.impl;

import halo.dao.query.BaseDao;
import halo.dao.query.QueryParam;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import tuxiazi.bean.Friend;
import tuxiazi.dao.FriendDao;

@Component("friendDao")
public class FriendDaoImpl extends BaseDao<Friend> implements FriendDao {

	@Override
	public Class<Friend> getClazz() {
		return Friend.class;
	}

	public Friend getByUseridAndFriendid(long userid, long friendid) {
		return this.getObject("userid=? and friendid=?", new Object[] { userid,
				friendid });
	}

	public int deleteByUseridAndFriendid(long userid, long friendid) {
		return this.delete("userid=? and friendid=?", new Object[] { userid,
				friendid });
	}

	public int countByUserid(long userid) {
		return this.count("userid=?", new Object[] { userid });
	}

	public List<Friend> getListByUserid(long userid, int begin, int size) {
		return this.getList("userid=?", new Object[] { userid }, "oid desc",
				begin, size);
	}

	public List<Long> getFriendidListByUserid(long userid) {
		QueryParam queryParam = new QueryParam(this.getKey(), null);
		queryParam.addClass(Friend.class);
		queryParam.setOrder("oid desc");
		queryParam.setColumns(new String[][] { new String[] { "friendid" } });
		queryParam.setWhereAndParams("userid=?", new Object[] { userid });
		return this.hkObjQuery.getList(queryParam, new RowMapper<Long>() {

			@Override
			public Long mapRow(ResultSet rs, int arg1) throws SQLException {
				return rs.getLong("fansid");
			}
		});
	}
}
