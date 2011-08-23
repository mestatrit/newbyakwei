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

import tuxiazi.bean.Fans;
import tuxiazi.bean.User;
import tuxiazi.dao.FansDao;
import tuxiazi.dao.FriendDao;
import tuxiazi.dao.UserDao;

@Component("fansDao")
public class FansDaoImpl extends BaseDao<Fans> implements FansDao {

	@Autowired
	private UserDao userDao;

	@Autowired
	private FriendDao friendDao;

	@Override
	public Class<Fans> getClazz() {
		return Fans.class;
	}

	@Override
	public Object save(Fans t) {
		long id = NumberUtil.getLong(super.save(t));
		t.setOid(id);
		return id;
	}

	public Fans getByUseridAndFansid(long userid, long fansid) {
		return this.getObject("userid=? and fansid=?", new Object[] { userid,
				fansid });
	}

	public int deleteByUseridAndFansid(long userid, long fansid) {
		return this.delete("userid=? and fansid=?", new Object[] { userid,
				fansid });
	}

	public int countByUserid(long userid) {
		return this.count("userid=?", new Object[] { userid });
	}

	public List<Long> getFansidListByUserid(long userid) {
		QueryParam queryParam = new QueryParam(this.getKey(), null);
		queryParam.addClass(Fans.class);
		queryParam.setOrder("oid desc");
		queryParam.setColumns(new String[][] { new String[] { "fansid" } });
		queryParam.setWhereAndParams("userid=?", new Object[] { userid });
		return this.hkObjQuery.getList(queryParam, new RowMapper<Long>() {

			@Override
			public Long mapRow(ResultSet rs, int arg1) throws SQLException {
				return rs.getLong("fansid");
			}
		});
	}

	public List<Fans> getListByUserid(long userid, int begin, int size) {
		return this.getList(null, "userid=?", new Object[] { userid },
				"oid desc", begin, size);
	}

	@Override
	public List<Fans> getListByUserid(long userid, boolean buildUser,
			long relationUserid, int begin, int size) {
		List<Fans> list = this.getListByUserid(userid, begin, size);
		if (buildUser) {
			List<Long> idList = new ArrayList<Long>();
			for (Fans o : list) {
				idList.add(o.getFansid());
			}
			Map<Long, User> usermap = this.userDao.getMapInId(idList);
			for (Fans o : list) {
				o.setFansUser(usermap.get(o.getFansid()));
			}
		}
		if (relationUserid > 0) {
			List<Long> friendidList = this.friendDao
					.getFriendidListByUserid(relationUserid);
			Set<Long> set = new HashSet<Long>();
			set.addAll(friendidList);
			for (Fans o : list) {
				if (set.contains(o.getFansid())) {
					o.setFriendRef(true);
				}
			}
		}
		return list;
	}
}
