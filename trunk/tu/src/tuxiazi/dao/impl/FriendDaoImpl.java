package tuxiazi.dao.impl;

import halo.dao.query.BaseDao;
import halo.dao.query.QueryParam;
import halo.util.NumberUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import tuxiazi.bean.Friend;
import tuxiazi.bean.User;
import tuxiazi.dao.FriendDao;
import tuxiazi.dao.UserDao;

@Component("friendDao")
public class FriendDaoImpl extends BaseDao<Friend> implements FriendDao {

	@Autowired
	private UserDao userDao;

	@Override
	public Class<Friend> getClazz() {
		return Friend.class;
	}

	@Override
	public Object save(Friend t) {
		long id = NumberUtil.getLong(super.save(t));
		t.setOid(id);
		return id;
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

	@Override
	public List<Friend> getListByUserid(long userid, boolean buildUser,
			long relationUserid, int begin, int size) {
		List<Friend> list = this.getListByUserid(relationUserid, begin, size);
		if (buildUser) {
			List<Long> idList = new ArrayList<Long>();
			for (Friend o : list) {
				idList.add(o.getFriendid());
			}
			Map<Long, User> usermap = this.userDao.getMapInId(idList);
			for (Friend o : list) {
				o.setFriendUser(usermap.get(o.getFriendid()));
			}
		}
		if (relationUserid == userid) {
			for (Friend o : list) {
				o.setFriendRef(true);
			}
		}
		else {
			if (relationUserid > 0) {
				List<Long> friendidList = this
						.getFriendidListByUserid(relationUserid);
				Set<Long> set = new HashSet<Long>();
				set.addAll(friendidList);
				for (Friend o : list) {
					if (set.contains(o.getFriendid())) {
						o.setFriendRef(true);
					}
				}
			}
		}
		return list;
	}
}
